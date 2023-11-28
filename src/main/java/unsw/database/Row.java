package unsw.database;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import unsw.database.Column.ColumnType;

public class Row {
    // String is a the column(field) name
    // Object is the value
    private Map<String, Object> row;
    private List<Column> observers = new ArrayList<>();

    public Row(String[] header, String[] values, Map<String, Column> fields) {
        row = new LinkedHashMap<>();
        initRow(header, values, fields);
        // TODO: Update derived fields HERE! and add them to the row!
        updateDerivedFieldHelper(fields);
    }

    public void initRow(String[] header, String[] values, Map<String, Column> fields) {
        // Add values to the row
        for (int j = 0; j < header.length; j++) {
            // cast them to the correct type
            // Check if the values are empty
            setValueRowField(header, values, fields, j);
        }
    }

    private void setValueRowField(String[] header, String[] values, Map<String, Column> fields, int j) {
        ColumnType t = fields.get(header[j]).getType();
        // Check the data type when adding the field
        if (t.ordinal() == 0) {
            if (values[j].equals("")) {
                row.put(header[j], 0);
            } else {
                row.put(header[j], Integer.parseInt(values[j]));
            }
        } else {
            row.put(header[j], values[j]);
        }
    }

    public Map<String, Object> getRow() {
        return row;
    }

    public void addDerviedField(String colName, List<String> depends, Function<Map<String, Object>, Object> compute,
            Column col) {
        Map<String, Object> oneSubField = getFields(depends);
        Object res = compute.apply(oneSubField);
        updateValue(colName, res);

        // The the column to the observer
        observers.add(col);
    }

    // Update the derived field in the column when the column is added
    public void updateDerivedFieldHelper(Map<String, Column> fields) {
        List<Column> update = new ArrayList<>(fields.values());
        update.forEach(e -> {
            if (e.isDerived()) {
                observers.add(e);
                row.put(e.getName(), e.computeDerived(this));
            }
        });
    }

    // Update the derived field in the column
    public void updateDerivedFieldHelper(String colName) {
        Column col = observers.stream().filter(e -> e.depedencyMatch(colName)).findFirst().orElse(null);
        if (col != null) {
            Object res = col.computeDerived(this);
            row.put(col.getName(), res);
        }
    }

    /**
     * 
     * @pre columName is a valid value
     * @post Given a column name, get the value in the correct type
     * @return
     */
    //
    public Object getValue(String columnName) {
        return row.get(columnName);
    }

    /**
     * 
     * @pre columName is a valid value and columnValue is also valid
     * @post update the value
     * @return
     */
    //
    public void updateValue(String columnName, Object columnValue) {
        row.put(columnName, columnValue);
        updateDerivedFieldHelper(columnName);
    }

    /**
     * 
     * @pre List of dependent columns are valid
     * @post Return a new subfield
     * @return
     */
    //
    public Map<String, Object> getFields(List<String> dependencies) {
        Map<String, Object> subField = new LinkedHashMap<>();
        dependencies.forEach(e -> subField.put(e, row.get(e)));
        return subField;
    }

}

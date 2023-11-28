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

    public void addDerviedField(String columnName, List<String> dependencies,
            Function<Map<String, Object>, Object> compute) {

        Map<String, Object> oneSubField = getFields(dependencies);

        Object res = compute.apply(oneSubField);
        // res = castValue(res, t);
        updateValue(columnName, res);
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

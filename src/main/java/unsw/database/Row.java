package unsw.database;

import java.util.LinkedHashMap;
import java.util.Map;

import unsw.database.Column.ColumnType;

public class Row {
    // String is a the column(field) name
    // Object is the value
    private Map<String, Object> row;

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
     * @pre columName is a valid value
     * @post Given a column name, get the value in the correct type
     * @return
     */
    //
    public void updateValue(String columnName, Object columnValue) {
        row.put(columnName, columnValue);
    }

}

package unsw.database;

import java.util.LinkedHashMap;
import java.util.Map;

public class Fields {
    private Map<String, Column> fields = new LinkedHashMap<>();

    public Map<String, Column> getFields() {
        return fields;
    }

    public void setFields(Map<String, Column> fields) {
        this.fields = fields;
    }

}

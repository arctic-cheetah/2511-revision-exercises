package unsw.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class Query {
    // you are to implement this function.
    public static final List<String> BOOLEAN_OPERATOR = new ArrayList<>(Arrays.asList(new String[] { "AND", "OR" }));
    public static final List<String> COMPARISON_OPERATOR = new ArrayList<>(Arrays.asList(new String[] { ">", "=" }));

    private String operatorName = "";

    public Query(Database db) {

    }

    public Query() {

    }

    public abstract List<Map<String, Object>> evaluate();

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

}

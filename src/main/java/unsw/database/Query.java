package unsw.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Query {
    // you are to implement this function.
    public static final List<String> BOOLEAN_OPERATOR = new ArrayList<>(Arrays.asList(new String[] { "AND", "OR" }));
    public static final List<String> COMPARISON_OPERATOR = new ArrayList<>(Arrays.asList(new String[] { ">", "=" }));

    private String operatorName = "";

    public Query(Database db) {

    }

    public Query() {

    }

    // Evaluate the row to the Map<String, Object> in the database afterwards
    public abstract List<Row> evaluate();

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

}

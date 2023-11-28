package unsw.database.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import unsw.database.Query;

public abstract class ComparisonOperator extends Query {
    private static final int COLUMN_NAME = 0;
    private static final int VALUE = 1;

    List<String> param = new ArrayList<>();
    // Query function
    BiFunction<String, Object, List<Map<String, Object>>> func = null;

    public List<Map<String, Object>> evaluate() {
        return func.apply(param.get(COLUMN_NAME), param.get(VALUE));
    };

    public void setFunc(BiFunction<String, Object, List<Map<String, Object>>> func) {
        // func = (x, y) -> db.querySimple(x, y);
        this.func = func;
    }

    public List<String> getParam() {
        return param;
    }

}

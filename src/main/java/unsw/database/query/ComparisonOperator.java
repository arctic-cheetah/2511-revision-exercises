package unsw.database.query;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import unsw.database.Query;
import unsw.database.Row;

public abstract class ComparisonOperator extends Query {
    private static final int COLUMN_NAME = 0;
    private static final int VALUE = 1;

    protected List<String> param = new ArrayList<>();
    // Query function
    protected BiFunction<String, Object, List<Row>> func = null;

    public List<Row> evaluate() {
        return func.apply(param.get(COLUMN_NAME), param.get(VALUE));
    };

    public void setFunc(BiFunction<String, Object, List<Row>> func) {
        // func = (x, y) -> db.querySimpleToRow(x, y);
        this.func = func;
    }

    public List<String> getParam() {
        return param;
    }

}

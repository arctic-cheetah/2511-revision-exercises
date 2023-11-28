package unsw.database.query;

import java.util.List;
import java.util.function.BiFunction;

import unsw.database.Query;
import unsw.database.Row;

public class BooleanOperator extends Query {
    protected Query left = null;
    protected Query right = null;

    protected BiFunction<List<Row>, List<Row>, List<Row>> func = null;

    public List<Row> evaluate() {
        List<Row> r1 = left.evaluate();
        List<Row> r2 = right.evaluate();
        return func.apply(r1, r2);
    };

    public void setLeft(Query left) {
        this.left = left;
    }

    public void setRight(Query right) {
        this.right = right;
    }

}

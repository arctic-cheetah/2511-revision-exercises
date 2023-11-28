package unsw.database;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import unsw.database.Column.ColumnType;
import unsw.database.query.BooleanOperator;
import unsw.database.query.QueryFactory;

public class Database {
    private Map<String, Column> fields = new LinkedHashMap<>();

    // One Row
    // Map<String, Object> oneRow;
    // Multiple rows
    private List<Row> records = new ArrayList<>();

    public Database(List<Column> columns) {
        new Fields();
        columns.forEach(e -> fields.put(e.getName(), e));
    }

    // Gets the column type for the specified column name
    public ColumnType getColumn(String name) {
        Column res = fields.get(name);
        return res == null ? null : res.getType();
    }

    // Query is an empty class that you can do whatever you want to (add
    // subclasses/functions/whatever)
    // the only requirement is that the name remains the same.
    public Query parseQuery(String query) {
        // wrapped in an array list to allow us to remove tokens from the "stream"
        // you don't have to change this function.

        // Split the input
        List<String> token = new ArrayList<>(Arrays.asList(query.split("\\s")));
        Query q = null;
        q = QueryFactory.queryFactory(token, this, q);
        // Create a new queries via a tree that will operate
        return parseOrExpr(token);
    }

    // Queries database using already compiled Query
    // If a record matches twice you can add it twice (i.e. you don't have to handle
    // distinctly)
    public List<Map<String, Object>> queryComplex(Query query) {
        return new ArrayList<Map<String, Object>>();
        // TODO: ^^
    }

    // should return number of new records inserted
    public int ingest(String contents) {
        // split up into rows
        List<String> rows = new ArrayList<>(Arrays.asList(contents.split("\n")));

        // grab the first row for schema
        // NOTE: When splitting on certain characters in java you need to escape them
        // (this is due to split actually taking in a regex).
        // So if you need to split on `|` you'll want to do `\\|` instead as per below.
        // (you shouldn't need to split on anything else other than newlines as above)
        String[] header = rows.remove(0).split("\\|");

        // trim schema to remove surrounding whitespace
        for (int i = 0; i < header.length; i++)
            header[i] = header[i].trim();

        // == end of starter code ==

        // Create a new Record for each row iterated over
        // WARNING check for null values!

        int i = 0;
        for (String e : rows) {
            String[] values = getValuesCleaned(e);
            Row r = new Row(header, values, fields);
            records.add(r);
            i++;
        }

        return i;
    }

    private String[] getValuesCleaned(String e) {
        String[] values = e.split("\\|");
        for (int i = 0; i < values.length; i++)
            values[i] = values[i].trim();
        return values;
    }

    // Queries database for all records where columnName has a value that .equals()
    // value.
    /**
     * 
     * @pre columName is a valid value
     * @return
     */
    public List<Map<String, Object>> querySimple(String columnName, Object value) {
        // Get the columndata type
        // Type cast the data?? yes
        String val = value.toString();

        List<Map<String, Object>> res = records.stream()
                .filter(e -> e.getValue(columnName).equals(val))
                .map(e -> e.getRow())
                .collect(Collectors.toList());
        return res;
    }

    public void updateData(String queryColumnName, Object queryValue, String columnName, Object columnValue) {
        String val = queryValue.toString();

        // Get all the rows that match the query
        List<Row> res = records.stream()
                .filter(e -> e.getValue(queryColumnName).equals(val))
                .collect(Collectors.toList());

        res.forEach(e -> e.updateValue(columnName, columnValue));
        return;
    }

    // Need add a fucking observer pattern as the derived field is ALWAYS updated IF
    // IT CHANGES!!
    // Publisher = Rows (checks for any updates)
    // Observer = Column (derived ones only!) updates the derived field and listens
    // to each ROW!
    public void addDerivedColumn(String columnName, List<String> dependencies,
            Function<Map<String, Object>, Object> compute) {

        // Check the derive column types
        // They must be all int, or all String
        ColumnType t = checkAllColumnTypes(dependencies);
        Column col = new Column(columnName, t, dependencies, compute);
        fields.put(columnName, col);
        records.forEach(e -> e.addDerviedField(columnName, dependencies, compute, col));

        return;

    }

    private ColumnType checkAllColumnTypes(List<String> dependencies) {
        for (int i = 0; i < ColumnType.values().length; i++) {
            boolean foundType = helperFoundType(dependencies, i);
            if (foundType) {
                return ColumnType.values()[i];
            }
        }

        return null;
    }

    private boolean helperFoundType(List<String> dependencies, int i) {
        boolean foundType = dependencies.stream()
                .map(e -> getColumn(e).ordinal() == i)
                .reduce((res, curr) -> res && curr)
                .orElse(false);
        return foundType;
    }

    /**
     * 
     * @param columnName
     * @param value
     * @pre Assume that we only accept value to be a integer! Comparison of strings does not make sense here!
     * @post return a List of Map<String,Objects> -> a list of rows that match the condition
     * 
     */
    public List<Map<String, Object>> queryGreaterThan(String columnName, Object value) {
        // Get the columndata type
        // Type cast the data?? yes
        int val = Integer.parseInt(value.toString());

        List<Map<String, Object>> res = records.stream()
                .filter(e -> (int) e.getValue(columnName) > (val))
                .map(e -> e.getRow())
                .collect(Collectors.toList());
        return res;
    }

    // -------------------------------------------------------------------------
    // FUCK YOU IF YOU WANT ME TO UNDERSTAND YOUR SHIT BELOW IN EXAM TIME
    // I DO THINGS MY WAY YOU MOTHERFUCKING TWAT
    /*
     * For the following functions you'll want to change them a very tiny amount,
     * you will probably
     * be changing the return types and making it so it constructs objects in this
     * said recursive manner.
     * 
     * To make it simple, the query language presumes all input is valid and doesn't
     * support `()` to decide precedence.
     * 
     * As a very rough explanation of how this works (it's an exam, you do *NOT*
     * need to understand the specifics just
     * focus on changing the return new Query()'s to what you need to construct the
     * query object).
     * 
     * If you are REALLY struggling look at the practice exam, how did you do the
     * query structure for business rules there?
     * How, can you apply that structure to this question in a similar fashion...
     */

    public Query parseAtom(List<String> tokens) {
        if (tokens.size() == 0) {
            return null;
        }

        String tok = tokens.remove(0);
        try {
            // Integer constant
            int result = Integer.parseInt(tok);
            // return new Query();
            // TODO: ^^
        } catch (NumberFormatException e) {
            // (ignore)
        }

        // then it must be a String
        // we may have to combine multiple tokens into ones
        String agg = tok.substring(1);
        if (agg.charAt(agg.length() - 1) == '\'') {
            // A string constant.
            String result = agg;
            // return new Query();
            // TODO: ^^
        }

        // this is where the text has spaces i.e. 'a b c', what we do is recombine the
        // tokens
        // until we find one with a ' terminator, this isn't a great strategy, but it's
        // simple!
        // this presumes we'll terminate, again we always presume valid input!
        while (true) {
            String next = tokens.remove(0);

            if (next.charAt(next.length() - 1) == '\'') {
                // A string constant.
                String result = agg + " " + next.substring(0, next.length() - 1);
                // return new Query();
                return new BooleanOperator();

            } else {
                agg += " " + next;
            }
        }
    }

    public Query parseOperatorExpr(List<String> tokens) {
        if (tokens.size() == 0) {
            return null;
        }

        // we presume we always need at least one operator and since
        // columns can't have boolean values we always need a symbol

        // lhs is the column name
        String lhs = tokens.remove(0);
        // the symbol (i.e. = or >)
        String op = tokens.remove(0);
        // what to compare it to i.e. 'A' or 2
        Query rhs = parseAtom(tokens);

        // return new Query();
        return new BooleanOperator();

        // TODO: ^^
    }

    public Query parseAndExpr(List<String> tokens) {
        if (tokens.size() == 0) {
            return null;
        }

        // lhs
        Query lhs = parseOperatorExpr(tokens);

        // read AND
        if (tokens.size() >= 1 && tokens.get(0).equals("AND") && lhs != null) {
            tokens.remove(0);
            // recurse i.e. a AND b AND c => a AND (b AND c)
            Query rhs = parseAndExpr(tokens);

            // you should do something with the results of above...
            // something like X x = new X(lhs, rhs);
            // return new Query();
            return new BooleanOperator();

            // TODO:^
        } else {
            return lhs;
        }
    }

    public Query parseOrExpr(List<String> tokens) {
        if (tokens.size() == 0) {
            return null;
        }

        // lhs
        Query lhs = parseAndExpr(tokens);

        // read OR
        if (tokens.size() >= 1 && tokens.get(0).equals("OR") && lhs != null) {
            tokens.remove(0);
            // recurse i.e. a OR b OR c => a OR (b OR c)
            Query rhs = parseOrExpr(tokens);

            // you should do something with the results of above...
            // something like X x = new X(lhs, rhs);
            // return new Query();
            return new BooleanOperator();

            // TODO:^
        } else {
            return lhs;
        }
    }
}

package unsw.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Column {
    public enum ColumnType {
        MARK, // Int
        TEXT; // string
    }

    private String name;
    private ColumnType type;
    List<String> dependencies = new ArrayList<>();
    private boolean isDerived = false;
    private Function<Map<String, Object>, Object> compute = null;

    public Column(String name, ColumnType type) {
        this.name = name;
        this.type = type;
        isDerived = false;
    }

    public Column(String name, ColumnType type, List<String> dependencies,
            Function<Map<String, Object>, Object> compute) {
        this.name = name;
        this.type = type;
        this.dependencies = dependencies;
        this.compute = compute;
        isDerived = true;
    }

    public String getName() {
        return name;
    }

    public ColumnType getType() {
        return type;
    }

    public boolean isDerived() {
        return isDerived;
    }

    public Object computeDerived(Row r) {
        Map<String, Object> val = r.getFields(dependencies);
        return compute.apply(val);
    }

    public boolean depedencyMatch(String depend) {
        return dependencies.contains(depend);
    }

    // public addPublisher() {

    // }

    // private ColumnType checkAllColumnTypes(List<String> dependencies) {
    // for (int i = 0; i < ColumnType.values().length; i++) {
    // boolean foundType = helperFoundType(dependencies, i);
    // if (foundType) {
    // return ColumnType.values()[i];
    // }
    // }

    // return null;
    // }

    // private boolean helperFoundType(List<String> dependencies, int i) {
    // boolean foundType = dependencies.stream()
    // .map(e -> this.type.ordinal() == i)
    // .reduce((res, curr) -> res && curr)
    // .orElse(false);
    // return foundType;
    // }
}

package lt.tastybytes.receptaiserver.sorter;

public enum RecipeSorter implements BaseSorter {
    NAME("name"),
    DATE_CREATED("dateCreated");

    private final String columnName;

    RecipeSorter(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}

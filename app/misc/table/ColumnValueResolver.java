package misc.table;

public interface ColumnValueResolver<T> {

    String label(T value);

    Comparable value(T value);
}

package misc.table;

public interface ColumnValueResolver<T> {

    String resolve(T value);

}

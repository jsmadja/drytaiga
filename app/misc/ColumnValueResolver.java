package misc;

public interface ColumnValueResolver<T> {
    String resolve(T value);
}

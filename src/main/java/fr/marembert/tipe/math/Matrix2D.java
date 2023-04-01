package fr.marembert.tipe.math;

import java.util.Objects;
import java.util.function.BiFunction;

public abstract class Matrix2D<T> {

    protected final Class<T> typeClass;

    private final int numberOfRows;
    private final int numberOfColumns;

    Matrix2D(int numberOfRows, int numberOfColumns, Class<T> typeClass) {
        if (numberOfRows < 1 || numberOfColumns < 1)
            throw new IllegalArgumentException("matrix cannot be smaller than 1x1");

        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.typeClass = typeClass;
    }

    /**
     * Mutates the current object by replacing the previous value stored at (row,column).<br>
     * Throws an {@link ArrayIndexOutOfBoundsException} if row or column is out of bounds
     */
    public abstract void set(int row, int column, T data);

    /**
     * Gets the current value stored at (row,column) (can be null).<br>
     * Throws an {@link ArrayIndexOutOfBoundsException} if row or column is out of bounds
     */
    public abstract T get(int row, int column);

    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }

    public void fillMatrix(T value) {
        for (int row = 0; row < getNumberOfRows(); row++) {
            for (int column = 0; column < getNumberOfColumns(); column++) {
                set(row, column, value);
            }
        }
    }

    public void fillMatrix(ValueSupplier<T> value) {
        for (int row = 0; row < getNumberOfRows(); row++) {
            for (int column = 0; column < getNumberOfColumns(); column++) {
                set(row, column, value.getValue(row, column));
            }
        }
    }

    public <U, R> Matrix2D<R> operateWith(Class<R> resultClass, Matrix2D<U> other, BiFunction<T, U, R> operation) {
        if (getNumberOfRows() != other.getNumberOfRows() || getNumberOfColumns() != other.getNumberOfColumns())
            throw new IllegalArgumentException("cannot operate on matrix of different sizes");

        Matrix2D<R> result = Matrix.createMatrix(getNumberOfRows(), getNumberOfColumns(), resultClass);
        result.fillMatrix((row, column) -> operation.apply(get(row, column), other.get(row, column)));
        return result;
    }

    public Matrix2D<T> transpose() {
        Matrix2D<T> result = Matrix.createMatrix(getNumberOfColumns(), getNumberOfRows(), this.typeClass);
        result.fillMatrix((row, column) -> get(column, row));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Matrix2D<?> other))
            return false;

        if (getNumberOfRows() != other.getNumberOfRows() || getNumberOfColumns() != other.getNumberOfColumns())
            return false;

        for (int row = 0; row < getNumberOfRows(); row++) {
            for (int column = 0; column < getNumberOfColumns(); column++) {
                T value = get(row, column);
                Object otherValue = other.get(row, column);
                if (value == null && otherValue != null || !Objects.equals(value, otherValue)) {
                    return false;
                }
            }
        }
        return true;
    }

    public interface ValueSupplier<T> {

        T getValue(int row, int column);
    }

}

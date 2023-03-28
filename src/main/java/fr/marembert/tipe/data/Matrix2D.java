package fr.marembert.tipe.data;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.BiFunction;

/**
 * A 2-dimensional array, with predefined size.
 * The {@link #set(int, int, T)} and {@link #get(int, int)} operations are done in constant time.<br>
 * The estimated space complexity magnitude is rows*columns.
 *
 * @param <T> the type of the values stored in the matrix.
 * @author David Marembert
 */
public class Matrix2D<T> {

    private final T[][] matrix;

    @SuppressWarnings("unchecked")
    public Matrix2D(int numberOfRows, int numberOfColumns, Class<T> dataClass) {
        if (numberOfRows < 1 || numberOfColumns < 1)
            throw new IllegalArgumentException("matrix cannot be smaller than 1x1");

        this.matrix = (T[][]) Array.newInstance(dataClass, numberOfRows, numberOfColumns);
    }

    /**
     * Mutates the current object by replacing the previous value stored at (row,column).<br>
     * Throws an {@link ArrayIndexOutOfBoundsException} if row or column is out of bounds
     */
    public void set(int row, int column, T data) {
        this.matrix[row][column] = data;
    }

    /**
     * Gets the current value stored at (row,column) (can be null).<br>
     * Throws an {@link ArrayIndexOutOfBoundsException} if row or column is out of bounds
     */
    public T get(int row, int column) {
        return this.matrix[row][column];
    }

    public int getNumberOfRows() {
        return this.matrix.length;
    }

    public int getNumberOfColumns() {
        return this.matrix[0].length;
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

        Matrix2D<R> result = new Matrix2D<>(getNumberOfRows(), getNumberOfColumns(), resultClass);
        result.fillMatrix((row, column) -> operation.apply(get(row, column), other.get(row, column)));
        return result;
    }

    @Override
    public String toString() {
        String matrix = Arrays.deepToString(this.matrix);
        return "Matrix2D{\n" + matrix.substring(1, matrix.length() - 1).replaceAll("], ", "]\n") + "\n}";
    }

    public interface ValueSupplier<T> {

        T getValue(int row, int column);
    }

}

package fr.marembert.tipe.math;

import java.lang.reflect.Array;
import java.util.Arrays;

public class GenericMatrix2D<T> extends Matrix2D<T> {

    private final T[][] matrix;

    @SuppressWarnings("unchecked")
    GenericMatrix2D(int numberOfRows, int numberOfColumns, Class<T> typeClass) {
        super(numberOfRows, numberOfColumns, typeClass);

        this.matrix = (T[][]) Array.newInstance(typeClass, numberOfRows, numberOfColumns);
    }

    @Override
    public void set(int row, int column, T data) {
        this.matrix[row][column] = data;
    }

    @Override
    public T get(int row, int column) {
        return this.matrix[row][column];
    }

    @Override
    public String toString() {
        String matrix = Arrays.deepToString(this.matrix);
        String format = "GenericMatrix2D(%s){%n%s%n}";
        return String.format(format, typeClass.getSimpleName(), matrix.substring(1, matrix.length() - 1)
                .replaceAll("], ", "]\n"));
    }
}

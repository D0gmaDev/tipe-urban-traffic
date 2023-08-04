package fr.marembert.tipe.math;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

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
        String matrixRepresentation = Arrays.stream(this.matrix)
                .map(row -> Arrays.stream(row).map(Objects::toString).collect(Collectors.joining(" ", "  | ", " |")))
                .collect(Collectors.joining("\n"));

        return String.format("GenericMatrix2D(%s) {%n%s%n}", typeClass.getSimpleName(), matrixRepresentation);
    }
}

package fr.marembert.tipe.math;

/**
 * Factory class to create matrices
 */
public class Matrix {

    @SuppressWarnings("unchecked")
    public static <T> Matrix2D<T> createMatrix(int numberOfRows, int numberOfColumns, Class<T> dataClass) {
        if (dataClass.equals(Double.class))
            return (Matrix2D<T>) createRealMatrix(numberOfRows, numberOfColumns);

        return new GenericMatrix2D<>(numberOfRows, numberOfColumns, dataClass);
    }

    public static RealMatrix2D createRealMatrix(int numberOfRows, int numberOfColumns) {
        return new RealMatrix2D(numberOfRows, numberOfColumns);
    }

}

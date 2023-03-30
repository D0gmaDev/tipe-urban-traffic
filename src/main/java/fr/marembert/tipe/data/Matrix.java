package fr.marembert.tipe.data;

/**
 * Factory class to create matrices
 */
public class Matrix {

    @SuppressWarnings("unchecked")
    public static <T> Matrix2D<T> createMatrix(int numberOfRows, int numberOfColumns, Class<T> dataClass) {
        if (dataClass.equals(Double.class))
            return (Matrix2D<T>) createRealMatrix(numberOfRows, numberOfColumns);

        return new Matrix2D<>(numberOfRows, numberOfColumns, dataClass);
    }

    public static RealMatrix2D createRealMatrix(int numberOfRows, int numberOfColumns) {
        return new RealMatrix2D(numberOfRows, numberOfColumns);
    }

}

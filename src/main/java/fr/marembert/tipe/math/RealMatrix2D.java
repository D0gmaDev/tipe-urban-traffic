package fr.marembert.tipe.math;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * A real matrix is a wrapper for a matrix containing primitive doubles.
 *
 * @see Matrix2D
 */
public class RealMatrix2D extends Matrix2D<Double> implements Iterable<double[]> {

    private final double[][] matrix;

    RealMatrix2D(int numberOfRows, int numberOfColumns) {
        super(numberOfRows, numberOfColumns, Double.class);

        this.matrix = new double[numberOfRows][numberOfColumns];
    }

    /**
     * Returns the primitive-typed value.
     *
     * @param row the row index
     * @param column the column index
     * @return the value, but cast to a double
     * @see Double
     */
    public double getValue(int row, int column) {
        return get(row, column);
    }

    public RealMatrix2D plus(RealMatrix2D other) {
        return (RealMatrix2D) operateWith(Double.class, other, Double::sum);
    }

    public RealMatrix2D minus(RealMatrix2D other) {
        return (RealMatrix2D) operateWith(Double.class, other, (a, b) -> a - b);
    }

    public double[] getDoubleRow(int row) {
        double[] values = new double[getNumberOfColumns()];
        for (int column = 0; column < getNumberOfColumns(); column++) {
            values[column] = getValue(row, column);
        }
        return values;
    }

    public double[] getDoubleColumn(int column) {
        double[] values = new double[getNumberOfRows()];
        for (int row = 0; row < getNumberOfColumns(); row++) {
            values[row] = getValue(row, column);
        }
        return values;
    }

    public DoubleStream doubleStream() {
        return Arrays.stream(this.matrix).flatMapToDouble(Arrays::stream);
    }

    @Override
    public void set(int row, int column, Double data) {
        this.matrix[row][column] = data;
    }

    @Override
    public Double get(int row, int column) {
        return this.matrix[row][column];
    }

    /* toString() fixed-size value formatter */
    private static final NumberFormat NUMBER_FORMAT = new DecimalFormat("0.00");

    @Override
    public String toString() {
        String matrixRepresentation = Arrays.stream(this.matrix)
                .map(row -> Arrays.stream(row).mapToObj(NUMBER_FORMAT::format).collect(Collectors.joining(" ", "  | ", " |")))
                .collect(Collectors.joining("\n"));

        return String.format("RealMatrix2D {%n%s%n}", matrixRepresentation);
    }

    @Override
    public Iterator<double[]> iterator() {
        return new RealMatrixRowIterator();
    }

    private class RealMatrixRowIterator implements Iterator<double[]> {

        private int currentRow = 0;

        @Override
        public boolean hasNext() {
            return this.currentRow < getNumberOfRows();
        }

        @Override
        public double[] next() {
            if (!hasNext())
                throw new NoSuchElementException();

            return getDoubleRow(this.currentRow++);
        }
    }

}

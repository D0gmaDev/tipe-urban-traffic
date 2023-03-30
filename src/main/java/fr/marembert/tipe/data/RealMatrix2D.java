package fr.marembert.tipe.data;

import java.util.Arrays;

/**
 * A real matrix is a wrapper for a matrix containing doubles.
 *
 * @see Matrix2D
 */
public class RealMatrix2D extends Matrix2D<Double> {

    RealMatrix2D(int numberOfRows, int numberOfColumns) {
        super(numberOfRows, numberOfColumns, Double.class);
    }

    /**
     * Returns the primitive-typed value.
     *
     * @param row the row index
     * @param column the column index
     * @return the value, or 0 if null
     */
    public double getValue(int row, int column) {
        Double value = get(row, column);
        return value != null ? (double) value : 0;
    }

    public RealMatrix2D plus(RealMatrix2D other) {
        return (RealMatrix2D) operateWith(Double.class, other, Double::sum);
    }

    public double[] getDoubleRow(int row) {
        double[] values = new double[getNumberOfColumns()];
        for (int column = 0; column < getNumberOfColumns(); column++) {
            values[column] = getValue(row, column);
        }
        return values;
    }

    public double[] getDoubleColumn(int column) {
        double[] values = new double[getNumberOfColumns()];
        for (int row = 0; row < getNumberOfColumns(); row++) {
            values[row] = getValue(row, column);
        }
        return values;
    }

    @Override
    public String toString() {
        String matrix = Arrays.deepToString(this.matrix);
        return "RealMatrix2D{\n" + matrix.substring(1, matrix.length() - 1)
                .replaceAll("], ", "]\n") + "\n}";
    }

}

package fr.marembert.tipe.math;

import java.util.Arrays;
import java.util.function.ToDoubleFunction;
import java.util.stream.IntStream;

public class MathUtils {

    private static final double ZERO_TOLERANCE = 1e-7F;

    /**
     * Checks if a double can be considered as 0, because it is close enough to 0.
     * The term <i>close to</i> is voluntarily vague here, since it depends on the value {@value ZERO_TOLERANCE}.
     *
     * @param number the double to check.
     * @return whether the argument is "close to" 0.
     */
    public static boolean isZero(double number) {
        return Math.abs(number) <= ZERO_TOLERANCE;
    }

    /**
     * Returns the array of function(x) for x in points,<br>
     * i.e. {@code [function(points[0]), function(points[1]), ..., function(points[points.length - 1])]}.
     *
     * @param points the selected pre-images.
     * @param function the application to map points to.
     * @return the double array of the images.
     */
    public static double[] getFunctionImage(double[] points, ToDoubleFunction<Double> function) {
        return Arrays.stream(points).map(function::applyAsDouble).toArray();
    }

    /**
     * Creates the array of the absolute differences between two series.
     * Useful to compare a model's predictions with theoretical values.
     *
     * @param amplification a factor to multiply each differences by.
     * @return a new array containing the positive differences.
     */
    public static double[] getAbsoluteDifference(double[] first, double[] second, int amplification) {
        return IntStream.range(0, first.length).mapToDouble(i -> Math.abs(first[i] - second[i]) * amplification).toArray();
    }
}

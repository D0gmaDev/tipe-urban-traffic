package fr.marembert.tipe.utils;

import java.util.Arrays;
import java.util.function.ToDoubleFunction;

public class Utils {

    /**
     * Returns the array of function(x) for x in points,<br>
     * i.e. {@code [function(points[0]), function(points[1]), ..., function(points[points.length - 1])]}.
     *
     * @param points the selected pre-images.
     * @param function the application to map points to.
     * @return the double array of the images
     */
    public static double[] getFunctionImage(double[] points, ToDoubleFunction<Double> function) {
        return Arrays.stream(points).map(function::applyAsDouble).toArray();
    }
}

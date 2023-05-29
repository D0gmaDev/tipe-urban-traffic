package fr.marembert.tipe.experiment.density;

import fr.marembert.tipe.experiment.TrafficExperiment;
import fr.marembert.tipe.math.MathUtils;
import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

public class DensityExperiment implements TrafficExperiment<DensityResult> {

    private static final DoubleUnaryOperator AMPLIFIER = value -> 10 * value;

    private double maximumSpeed;
    private double maximumDensity;
    private double criticalDensity;

    private final DoubleUnaryOperator speed = density -> density <= criticalDensity ? maximumSpeed : density > maximumDensity ? 0 : maximumSpeed / Math.log(maximumDensity / criticalDensity) * Math.log(maximumDensity / density);

    private final DoubleUnaryOperator flux = density -> density * speed.applyAsDouble(density);

    public DensityExperiment(double carLength, double maximumSpeed, double criticalDensity) {
        this.maximumSpeed = maximumSpeed;
        this.maximumDensity = 1. / carLength;
        this.criticalDensity = criticalDensity;
    }

    @Override
    public DensityResult runExperiment() {

        double[] density = MathUtils.linSpace(0, maximumDensity + .1, 500);

        double[] speed = Arrays.stream(density).map(this.speed).toArray();

        double[] flux = Arrays.stream(density).map(this.flux.andThen(AMPLIFIER)).toArray(); // amplify for readability

        double optimalDensity = maximumDensity / Math.E;
        double maximumFlux = this.flux.applyAsDouble(optimalDensity);

        return new DensityResult(this.maximumSpeed, density, speed, flux, optimalDensity, maximumFlux);
    }
}

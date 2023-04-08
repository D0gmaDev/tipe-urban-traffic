package fr.marembert.tipe.experiment;

import fr.marembert.tipe.math.MathUtils;
import fr.marembert.tipe.math.Matrix;
import fr.marembert.tipe.math.RealMatrix2D;
import fr.marembert.tipe.traffic.Car;
import fr.marembert.tipe.traffic.DynamicTick;
import java.util.Arrays;

public class ConstantAccelerationExperiment implements TrafficExperiment<CarsPositionResult> {

    private static final double STEP        = DynamicTick.TIME_STEP;
    private static final int    SAMPLE_SIZE = 50;

    private final double duration;
    private final double acceleration;

    public ConstantAccelerationExperiment(double duration, double acceleration) {
        this.duration = duration;
        this.acceleration = acceleration;
    }

    @Override
    public CarsPositionResult runExperiment() {

        Car car = new Car(0);
        car.setAcceleration(acceleration);

        int iterationsNumber = (int) (duration / STEP);

        double[] timeSample = MathUtils.linSpace(0, duration, SAMPLE_SIZE);
        RealMatrix2D positionsSample = Matrix.createRealMatrix(2, SAMPLE_SIZE);

        int j = 0;
        double nextSample = timeSample[0];

        for (int i = 0; i < iterationsNumber; i++) {
            car.tick(STEP);

            double time = i * STEP;

            if (time >= nextSample) {
                positionsSample.set(0, j, car.getPosition());
                nextSample = timeSample[++j];
            }
        }

        positionsSample.set(0, SAMPLE_SIZE - 1, car.getPosition()); // adjust final position

        double[] theoreticalSeries = MathUtils.getFunctionImage(timeSample, x -> 0.5 * acceleration * x * x);

        positionsSample.fillRow(1, Arrays.stream(theoreticalSeries).boxed().toList());

        return new CarsPositionResult("Position du véhicule", timeSample, positionsSample, new String[]{"Voiture", "Position Théorique"});
    }
}

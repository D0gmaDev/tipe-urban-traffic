package fr.marembert.tipe.experiment;

import static fr.marembert.tipe.traffic.DynamicTick.TIME_STEP;

import fr.marembert.tipe.math.MathUtils;
import fr.marembert.tipe.math.Matrix;
import fr.marembert.tipe.math.RealMatrix2D;
import fr.marembert.tipe.traffic.Car;
import java.util.Arrays;
import java.util.List;

public class ConstantAccelerationExperiment implements TrafficExperiment<CarsPositionResult> {

    private final double duration;
    private final int    sampleSize;
    private final double acceleration;

    public ConstantAccelerationExperiment(double duration, int sampleSize, double acceleration) {
        this.duration = duration;
        this.sampleSize = sampleSize;
        this.acceleration = acceleration;
    }

    @Override
    public CarsPositionResult runExperiment() {

        Car car = new Car(0, 4., 0., 0., acceleration);

        int iterationsNumber = (int) (duration / TIME_STEP);

        double[] timeSample = MathUtils.linSpace(0, duration, sampleSize);
        RealMatrix2D positionsSample = Matrix.createRealMatrix(2, sampleSize);

        int j = 0;
        double nextSample = timeSample[0];

        for (int i = 0; i < iterationsNumber; i++) {
            car.tick(TIME_STEP);

            double time = i * TIME_STEP;

            if (time >= nextSample) {
                positionsSample.set(0, j, car.getPosition());
                nextSample = timeSample[++j];
            }
        }

        positionsSample.set(0, sampleSize - 1, car.getPosition()); // adjust final position

        double[] theoreticalSeries = MathUtils.getFunctionImage(timeSample, x -> 0.5 * acceleration * x * x);

        positionsSample.fillRow(1, Arrays.stream(theoreticalSeries).boxed().toList());

        return new CarsPositionResult("Car's Position", timeSample, positionsSample, List.of("Car", "Theoretical position"));
    }
}

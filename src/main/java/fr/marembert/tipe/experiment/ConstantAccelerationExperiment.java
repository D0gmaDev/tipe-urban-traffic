package fr.marembert.tipe.experiment;

import fr.marembert.tipe.math.MathUtils;
import fr.marembert.tipe.math.Matrix;
import fr.marembert.tipe.math.RealMatrix2D;
import fr.marembert.tipe.traffic.Car;
import fr.marembert.tipe.traffic.DynamicTick;
import java.time.Duration;
import java.util.Arrays;

public class ConstantAccelerationExperiment implements TrafficExperiment<CarsPositionResult> {

    private static final double STEP = DynamicTick.DEFAULT_TIME_STEP;

    private final double duration;
    private final double acceleration;

    public ConstantAccelerationExperiment(double duration, double acceleration) {
        this.duration = duration;
        this.acceleration = acceleration;
    }

    @Override
    public CarsPositionResult runExperiment(Duration timout) {

        Car car = new Car(0);
        car.setAcceleration(acceleration);

        int iterationsNumber = (int) (duration / STEP);

        int sampleSize = 100;

        int record = (iterationsNumber - 1) / (sampleSize - 1);

        RealMatrix2D speeds = Matrix.createRealMatrix(2, sampleSize);
        double[] sample = new double[sampleSize];

        int j = 0;

        for (int i = 0; i < iterationsNumber; i++) {
            car.tick(STEP);

            if (i % record == 0) {
                double time = i * STEP;

                sample[j] = time;
                speeds.set(0, j, car.getPosition());
                j++;
            }
        }

        sample[sampleSize - 1] = duration;
        speeds.set(0, sampleSize - 1, car.getPosition());

        double[] theory = MathUtils.getFunctionImage(sample, x -> 0.5 * acceleration * x * x);

        speeds.fillRow(1, Arrays.stream(theory).boxed().toList());

        return new CarsPositionResult("Position du véhicule", sample, speeds, new String[]{"Voiture", "Position Théorique"});
    }
}

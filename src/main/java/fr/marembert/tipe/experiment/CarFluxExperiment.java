package fr.marembert.tipe.experiment;

import fr.marembert.tipe.math.MathUtils;
import fr.marembert.tipe.math.Matrix;
import fr.marembert.tipe.math.RealMatrix2D;
import fr.marembert.tipe.traffic.Car;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CarFluxExperiment implements TrafficExperiment<CarFluxResult> {

    private static final double STEP = 1e-2;

    private final double duration;

    private final int    numberOfCars;
    private final double carLength;

    private final double initialDistance;
    private final double defaultSpeed;
    private final double reactionTime;

    private final DoubleFunction<Double> firstCarSpeed;

    private final List<Car> carsLane;

    private RealMatrix2D carsPositions;
    private RealMatrix2D carsSpeed;

    public CarFluxExperiment(double duration, int numberOfCars, double carLength, double initialDistance, double defaultSpeed, double reactionTime, DoubleFunction<Double> firstCarSpeed) {
        this.duration = duration;
        this.numberOfCars = numberOfCars;
        this.carLength = carLength;
        this.initialDistance = initialDistance;
        this.defaultSpeed = defaultSpeed;
        this.reactionTime = reactionTime;
        this.firstCarSpeed = firstCarSpeed;
        this.carsLane = IntStream.range(0, numberOfCars).mapToObj(id -> new Car(id, 1_000., carLength, -id * (initialDistance + carLength), defaultSpeed)).toList();
    }

    @Override
    public CarFluxResult runExperiment() {

        if (hasCrashed(0))
            throw new IllegalArgumentException("Wrong car initial placement");

        int reactionIterationShift = (int) (reactionTime / STEP);

        int iterationsNumber = (int) (duration / STEP);

        double[] timeSample = MathUtils.linSpace(0, duration, iterationsNumber);

        this.carsPositions = Matrix.createRealMatrix(numberOfCars, iterationsNumber);
        this.carsPositions.fillRow(0, Arrays.stream(timeSample).map(time -> time * defaultSpeed).boxed().toList());

        this.carsSpeed = Matrix.createRealMatrix(numberOfCars, iterationsNumber);
        this.carsSpeed.fillRow(0, Stream.generate(() -> defaultSpeed).limit(iterationsNumber).toList());

        for (int iteration = 0; iteration < iterationsNumber; iteration++) {

            int pastIteration = iteration - reactionIterationShift;

            for (Car car : this.carsLane) {

                car.tick(STEP);

                if (car.getId() == 0) {

                    car.setSpeed(this.firstCarSpeed.apply(STEP * iteration));

                } else if (pastIteration > 0)
                    car.setAcceleration(newAcceleration(car, pastIteration));


                this.carsPositions.set(car.getId(), iteration, car.getPosition());
                this.carsSpeed.set(car.getId(), iteration, car.getSpeed());
            }

            if (hasCrashed(iteration))
                throw new IllegalStateException();
        }

        System.out.println("Simulation Finished. Drawing graphs...");

        RealMatrix2D theoreticalPositions = Matrix.createRealMatrix(this.carsPositions.getNumberOfRows(), this.carsPositions.getNumberOfColumns());
        theoreticalPositions.fillMatrix((row, column) -> -row * (carLength + initialDistance) + (column * STEP * defaultSpeed));

        RealMatrix2D carShift = Matrix.createRealMatrix(this.carsPositions.getNumberOfRows(), this.carsPositions.getNumberOfColumns());
        carShift.fillMatrix((row, column) -> row * -(carLength + initialDistance));

        return new CarFluxResult(timeSample, this.carsPositions, this.carsPositions.minus(theoreticalPositions).plus(carShift), this.carsSpeed);
    }

    private boolean hasCrashed(int iteration) {
        for (int i = 1; i < numberOfCars; i++) {
            if (pos(i) >= pos(i - 1) - carLength) {
                System.out.printf("A crash has occurred at iteration %d: Car %s (%.2f) bumped into Car %s (%.2f)%n", iteration, i, pos(i), i - 1, pos(i - 1));
                return true;
            }
        }
        return false;
    }

    private double newAcceleration(Car car, int pastIteration) {
        double speedDelta = speed(car.getId() - 1, pastIteration) - speed(car.getId(), pastIteration);
        double positionDelta = position(car.getId() - 1, pastIteration) - position(car.getId(), pastIteration) - carLength;

        return 9 * speedDelta / positionDelta;
    }

    private double position(int id, int iteration) {
        return this.carsPositions.get(id, iteration);
    }

    private double speed(int id, int iteration) {
        return this.carsSpeed.get(id, iteration);
    }

    private double pos(int id) {
        return this.carsLane.get(id).getPosition();
    }

    private double speed(int id) {
        return this.carsLane.get(id).getSpeed();
    }

    private double accel(int id) {
        return this.carsLane.get(id).getAcceleration();
    }

    private double density() {
        return 1 / (pos(0) - pos(1));
    }
}
package fr.marembert.tipe;

import fr.marembert.tipe.display.CarsDisplay;
import fr.marembert.tipe.display.CarsPositionDisplay;
import fr.marembert.tipe.display.DensityDisplay.MultipleDensityDisplay;
import fr.marembert.tipe.display.MultipleCarsDisplay;
import fr.marembert.tipe.display.ResultDisplayHandler;
import fr.marembert.tipe.experiment.MultipleExperiment;
import fr.marembert.tipe.experiment.TrafficExperiment;
import fr.marembert.tipe.experiment.TrafficResult;
import fr.marembert.tipe.experiment.density.DensityExperiment;
import fr.marembert.tipe.experiment.flux.CarFluxExperiment;
import fr.marembert.tipe.experiment.flux.CarFluxResult;
import fr.marembert.tipe.experiment.flux.ConstantAccelerationExperiment;
import java.util.List;
import java.util.Scanner;
import java.util.function.DoubleUnaryOperator;

/**
 * A CPGE TIPE project
 *
 * @author David Marembert
 * @see <a href="https://www.scei-concours.fr/tipe.php">the SCEI website about TIPE</a>
 */
public class Main {

    private static final double REACTION_TIME       = 1; // s
    private static final double SPEED_50            = 13; // m/s
    private static final double SPEED_30            = 8; // m/s
    private static final double CAR_LENGTH          = 4; // m
    private static final double CRITICAL_DENSITY_50 = 2 / 50.; // cars/m
    private static final double CRITICAL_DENSITY_30 = 2 * CRITICAL_DENSITY_50;

    public static void main(String[] args) {
        System.out.print("Please type an experiment id: ");

        switch (new Scanner(System.in).nextLine().toLowerCase()) {
            case "acc" -> constantAccelerationExperiment();
            case "flux" -> carFluxExperiment();
            case "flux2" -> carFluxExperiment2();
            case "flux3" -> carFluxExperiment3();
            case "fullstop" -> fullStopExperiment();
            case "density" -> densityExperiment();
            default -> throw new IllegalStateException("Unknown experiment");
        }

    }

    private static <T extends TrafficResult> void runExperiment(TrafficExperiment<T> experiment, ResultDisplayHandler<T> resultDisplayHandler) {

        System.out.println("==== Starting simulation...");

        long start = System.currentTimeMillis();
        T result = experiment.runExperiment();
        long stop = System.currentTimeMillis();

        System.out.printf("==== Simulation done in %.2fs %n", (stop - start) / 1000.);

        start = System.currentTimeMillis();
        resultDisplayHandler.displayResult(result);
        stop = System.currentTimeMillis();

        System.out.printf("==== Displayed in %.2fs %n", (stop - start) / 1000.);
    }

    private static void constantAccelerationExperiment() {

        runExperiment(
                new ConstantAccelerationExperiment(40, 50, 5.),
                new CarsPositionDisplay()
        );
    }

    /**
     * Test a simple Traffic perturbation
     */
    private static void carFluxExperiment() {
        double defaultSpeed = 10.;

        DoubleUnaryOperator leadingCarSpeed = time -> time < 0 ? defaultSpeed : defaultSpeed * (1 - 1.5 * time * Math.exp((0.6 - time) / 0.6));

        runExperiment(
                new CarFluxExperiment(40, 5, CAR_LENGTH, 40, defaultSpeed, REACTION_TIME, 20, leadingCarSpeed),
                new CarsDisplay()
        );
    }

    /**
     * Test different acceleration factors
     */
    private static void carFluxExperiment2() {
        double defaultSpeed = 13.;

        DoubleUnaryOperator leadingCarSpeed = time -> time < 0 ? defaultSpeed : defaultSpeed * (1 - 1.5 * time * Math.exp((0.6 - time) / 0.6));

        MultipleExperiment<CarFluxResult> experiments = new MultipleExperiment<>();

        for (int i = 6; i < 12; i++) {
            experiments.addExperiment(new CarFluxExperiment(40, 6, CAR_LENGTH, 60, defaultSpeed, REACTION_TIME, i * 5, leadingCarSpeed));
        }

        runExperiment(
                experiments,
                new MultipleCarsDisplay()
        );
    }

    /**
     * Compare different target speeds
     */
    private static void carFluxExperiment3() {

        MultipleExperiment<CarFluxResult> experiments = new MultipleExperiment<>();

        for (double defaultSpeed : List.of(SPEED_30, SPEED_50)) {
            DoubleUnaryOperator leadingCarSpeed = time -> time < 0 ? defaultSpeed : defaultSpeed * (1 - 1.5 * time * Math.exp((0.6 - time) / 0.6));
            experiments.addExperiment(new CarFluxExperiment(40, 6, CAR_LENGTH, 50, defaultSpeed, REACTION_TIME, 20, leadingCarSpeed));
        }

        runExperiment(
                experiments,
                new MultipleCarsDisplay()
        );
    }


    /**
     * The first car breaks completely
     */
    private static void fullStopExperiment() {
        double defaultSpeed = 10.;

        DoubleUnaryOperator leadingCarSpeed = time -> time <= 0 ? defaultSpeed : defaultSpeed * Math.exp((-0.5 * time));

        runExperiment(
                new CarFluxExperiment(60, 3, CAR_LENGTH, 200, defaultSpeed, REACTION_TIME, 10, leadingCarSpeed),
                new CarsDisplay()
        );
    }

    /**
     * Density and Flux experiment
     */
    private static void densityExperiment() {

        runExperiment(
                new MultipleExperiment<>(List.of(new DensityExperiment(CAR_LENGTH, SPEED_50, CRITICAL_DENSITY_50), new DensityExperiment(CAR_LENGTH, SPEED_30, CRITICAL_DENSITY_30))),
                new MultipleDensityDisplay()
        );
    }

}

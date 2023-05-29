package fr.marembert.tipe;

import fr.marembert.tipe.display.CarsDisplay;
import fr.marembert.tipe.display.CarsPositionDisplay;
import fr.marembert.tipe.display.DensityDisplay.MultipleDensityDisplay;
import fr.marembert.tipe.display.MultipleCarsDisplay;
import fr.marembert.tipe.display.ResultDisplayHandler;
import fr.marembert.tipe.experiment.*;
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

    private static void carFluxExperiment() {
        double defaultSpeed = 10.;

        DoubleUnaryOperator leadingCarSpeed = time -> time < 0 ? defaultSpeed : defaultSpeed * (1 - 1.5 * time * Math.exp((0.6 - time) / 0.6));

        runExperiment(
                new CarFluxExperiment(40, 3, 4, 40, defaultSpeed, 1, 20, leadingCarSpeed),
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
            experiments.addExperiment(new CarFluxExperiment(40, 6, 4, 60, defaultSpeed, 1, i * 5, leadingCarSpeed));
        }

        runExperiment(
                experiments,
                new MultipleCarsDisplay()
        );
    }

    private static void carFluxExperiment3() {

        MultipleExperiment<CarFluxResult> experiments = new MultipleExperiment<>();

        for (int speed = 8; speed <= 13; speed++) {
            double defaultSpeed = speed;
            DoubleUnaryOperator leadingCarSpeed = time -> time < 0 ? defaultSpeed : defaultSpeed * (1 - 1.5 * time * Math.exp((0.6 - time) / 0.6));
            experiments.addExperiment(new CarFluxExperiment(40, 6, 4, 50, defaultSpeed, 1, 30, leadingCarSpeed));
        }

        runExperiment(
                experiments,
                new MultipleCarsDisplay()
        );
    }


    private static void fullStopExperiment() {
        double defaultSpeed = 10.;

        DoubleUnaryOperator leadingCarSpeed = time -> time <= 0 ? defaultSpeed : defaultSpeed * Math.exp((-0.5 * time));

        runExperiment(
                new CarFluxExperiment(60, 3, 4, 200, defaultSpeed, 1, 10, leadingCarSpeed),
                new CarsDisplay()
        );
    }

    private static void densityExperiment() {
        runExperiment(
                new MultipleExperiment<>(List.of(new DensityExperiment(4., 13., 2 / 50.), new DensityExperiment(4., 8., 2 / 25.))),
                new MultipleDensityDisplay()
        );
    }

}

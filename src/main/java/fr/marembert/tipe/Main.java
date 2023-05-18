package fr.marembert.tipe;

import fr.marembert.tipe.display.CarsDisplay;
import fr.marembert.tipe.display.CarsPositionDisplay;
import fr.marembert.tipe.experiment.CarFluxExperiment;
import fr.marembert.tipe.experiment.CarFluxResult;
import fr.marembert.tipe.experiment.CarsPositionResult;
import fr.marembert.tipe.experiment.ConstantAccelerationExperiment;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.DoubleFunction;

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
            case "fullstop" -> fullStopExperiment();
            default -> throw new IllegalStateException("Unknown experiment");
        }

    }

    private static void constantAccelerationExperiment() {
        ConstantAccelerationExperiment constantAccelerationExperiment = new ConstantAccelerationExperiment(50, 5.);
        CarsPositionResult result = constantAccelerationExperiment.runExperiment();

        new CarsPositionDisplay().displayResult(result);
    }

    private static void carFluxExperiment() {
        double defaultSpeed = 10.;

        DoubleFunction<Double> leadingCarSpeed = time -> time < 0 ? defaultSpeed : defaultSpeed * (1 - 1.5 * time * Math.exp((0.6 - time) / 0.6));

        CarFluxExperiment carFluxExperiment = new CarFluxExperiment(40, 3, 4, 40, defaultSpeed, 1, 20, leadingCarSpeed);
        CarFluxResult carFluxResult = carFluxExperiment.runExperiment();

        new CarsDisplay().displayResult(carFluxResult);
    }

    /**
     * Test different acceleration factors
     */
    private static void carFluxExperiment2() {
        double defaultSpeed = 10.;

        DoubleFunction<Double> leadingCarSpeed = time -> time < 0 ? defaultSpeed : defaultSpeed * (1 - 1.5 * time * Math.exp((0.6 - time) / 0.6));

        ArrayList<CarFluxResult> carFluxResults = new ArrayList<>();

        for (int i = 6; i < 12; i++) {
            CarFluxExperiment carFluxExperiment = new CarFluxExperiment(40, 6, 4, 60, defaultSpeed, 1, i * 5, leadingCarSpeed);
            CarFluxResult carFluxResult = carFluxExperiment.runExperiment();
            carFluxResults.add(carFluxResult);
        }

        new CarsDisplay().displayMany(carFluxResults);
    }

    private static void fullStopExperiment() {
        double defaultSpeed = 10.;

        DoubleFunction<Double> leadingCarSpeed = time -> time <= 0 ? defaultSpeed : defaultSpeed * Math.exp((-0.5 * time));

        CarFluxExperiment carFluxExperiment = new CarFluxExperiment(60, 3, 4, 200, defaultSpeed, 1, 10, leadingCarSpeed);
        CarFluxResult carFluxResult = carFluxExperiment.runExperiment();

        new CarsDisplay().displayResult(carFluxResult);
    }

}

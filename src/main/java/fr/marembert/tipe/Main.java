package fr.marembert.tipe;

import fr.marembert.tipe.display.CarsDisplay;
import fr.marembert.tipe.display.CarsPositionDisplay;
import fr.marembert.tipe.experiment.CarFluxExperiment;
import fr.marembert.tipe.experiment.CarFluxResult;
import fr.marembert.tipe.experiment.CarsPositionResult;
import fr.marembert.tipe.experiment.ConstantAccelerationExperiment;
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

        switch (new Scanner(System.in).nextLine().toLowerCase()) {
            case "acc" -> constantAccelerationExperiment();
            case "flux" -> carFluxExperiment();
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

        CarFluxExperiment carFluxExperiment = new CarFluxExperiment(40, 8, 4, 20, defaultSpeed, 1, leadingCarSpeed);
        CarFluxResult carFluxResult = carFluxExperiment.runExperiment();

        new CarsDisplay().displayResult(carFluxResult);
    }

}

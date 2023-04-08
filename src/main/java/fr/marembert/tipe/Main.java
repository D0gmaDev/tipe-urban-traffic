package fr.marembert.tipe;

import fr.marembert.tipe.display.CarsPositionDisplay;
import fr.marembert.tipe.experiment.CarsPositionResult;
import fr.marembert.tipe.experiment.ConstantAccelerationExperiment;

/**
 * A CPGE TIPE project
 *
 * @author David Marembert
 * @see <a href="https://www.scei-concours.fr/tipe.php">the SCEI website about TIPE</a>
 */
public class Main {

    public static void main(String[] args) {

        ConstantAccelerationExperiment constantAccelerationExperiment = new ConstantAccelerationExperiment(3, 5.);
        CarsPositionResult result = constantAccelerationExperiment.runExperiment();

        new CarsPositionDisplay().displayResult(result);
    }

}

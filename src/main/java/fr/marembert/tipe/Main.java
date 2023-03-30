package fr.marembert.tipe;

import fr.marembert.tipe.data.Matrix;
import fr.marembert.tipe.data.RealMatrix2D;
import fr.marembert.tipe.display.CarsSpeedDisplay;
import fr.marembert.tipe.experiment.CarsSpeedResult;
import java.util.stream.IntStream;

/**
 * A CPGE TIPE project
 *
 * @author David Marembert
 * @see <a href="https://www.scei-concours.fr/tipe.php">the SCEI website about TIPE</a>
 */
public class Main {

    public static void main(String[] args) {

        RealMatrix2D testMatrix = Matrix.createRealMatrix(3, 15);
        testMatrix.fillMatrix((row, column) -> row * (5. + column));
        System.out.println(testMatrix);

        CarsSpeedResult vehiculesResult = new CarsSpeedResult("Vitesse des véhicules", IntStream.range(0, 15).mapToDouble(i -> (double) i).toArray(), testMatrix);

        new CarsSpeedDisplay().displayResult(vehiculesResult);
    }

}

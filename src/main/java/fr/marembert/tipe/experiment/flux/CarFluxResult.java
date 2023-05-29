package fr.marembert.tipe.experiment.flux;

import fr.marembert.tipe.experiment.TrafficResult;
import fr.marembert.tipe.math.RealMatrix2D;

public record CarFluxResult(double[] timeSample, RealMatrix2D positions, RealMatrix2D displacements, RealMatrix2D speeds, RealMatrix2D distances) implements TrafficResult {

}

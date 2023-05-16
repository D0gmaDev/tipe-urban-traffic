package fr.marembert.tipe.experiment;

import fr.marembert.tipe.math.RealMatrix2D;

public record CarFluxResult(double[] timeSample, RealMatrix2D positions, RealMatrix2D displacement, RealMatrix2D speeds) implements TrafficResult {

}

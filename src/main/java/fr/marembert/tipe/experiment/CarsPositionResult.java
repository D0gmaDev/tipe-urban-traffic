package fr.marembert.tipe.experiment;

import fr.marembert.tipe.math.RealMatrix2D;
import java.util.List;

public record CarsPositionResult(String title, double[] timeSample, RealMatrix2D positions, List<String> labels) implements TrafficResult {

}

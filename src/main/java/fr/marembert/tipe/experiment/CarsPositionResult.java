package fr.marembert.tipe.experiment;

import fr.marembert.tipe.math.RealMatrix2D;

public record CarsPositionResult(String title, double[] timeSample, RealMatrix2D positions, String[] labels) implements TrafficResult {

}

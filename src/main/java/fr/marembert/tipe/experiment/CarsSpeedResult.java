package fr.marembert.tipe.experiment;

import fr.marembert.tipe.math.RealMatrix2D;

public record CarsSpeedResult(String title, double[] timeSample, RealMatrix2D speeds) implements TrafficResult {

}

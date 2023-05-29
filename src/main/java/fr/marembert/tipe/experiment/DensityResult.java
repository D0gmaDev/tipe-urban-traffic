package fr.marembert.tipe.experiment;

public record DensityResult(double maximumSpeed, double[] density, double[] speed, double[] flux, double optimalDensity, double maximumFlux) implements TrafficResult {

}

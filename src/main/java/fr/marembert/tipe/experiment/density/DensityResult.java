package fr.marembert.tipe.experiment.density;

import fr.marembert.tipe.experiment.TrafficResult;

public record DensityResult(double maximumSpeed, double[] density, double[] speed, double[] flux, double optimalDensity, double maximumFlux) implements TrafficResult {

}

package fr.marembert.tipe.display;

import fr.marembert.tipe.experiment.CarFluxResult;
import java.util.List;
import java.util.function.IntFunction;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

public class CarsDisplay implements ResultDisplayHandler<CarFluxResult> {

    @Override
    public void displayResult(CarFluxResult result) {

        IntFunction<String> seriesName = id -> "Car #" + id;

        XYChart positionChart = ChartHelper.makeChart("Car's Position", "Temps (s)", "Position (m)", result.timeSample(), result.positions(), seriesName);

        XYChart displacementChart = ChartHelper.makeChart("Car's Displacement", "Temps (s)", "Delta (m)", result.timeSample(), result.displacements(), seriesName);

        XYChart speedChart = ChartHelper.makeChart("Car's Speed", "Temps (s)", "Vitesse (m/s)", result.timeSample(), result.speeds(), seriesName);

        XYChart distanceChart = ChartHelper.makeChart("Car's Distance", "Temps (s)", "Distance (m)", result.timeSample(), result.distances(), seriesName);

        new SwingWrapper<>(List.of(positionChart, displacementChart, speedChart, distanceChart)).displayChartMatrix();
    }
}

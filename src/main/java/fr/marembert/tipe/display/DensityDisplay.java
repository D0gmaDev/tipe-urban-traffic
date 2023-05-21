package fr.marembert.tipe.display;

import fr.marembert.tipe.experiment.DensityResult;
import fr.marembert.tipe.experiment.MultipleExperiment.MultipleResult;
import java.awt.Color;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.lines.SeriesLines;

public class DensityDisplay implements ResultDisplayHandler<DensityResult> {

    @Override
    public void displayResult(DensityResult result) {
        new SwingWrapper<>(drawChart(result)).displayChart();
    }

    public static class MultipleDensityDisplay implements ResultDisplayHandler<MultipleResult<DensityResult>> {

        @Override
        public void displayResult(MultipleResult<DensityResult> result) {
            new SwingWrapper<>(result.getResults().stream().map(DensityDisplay::drawChart).toList()).displayChartMatrix();
        }
    }

    private static XYChart drawChart(DensityResult result) {
        XYChart xyChart = new XYChartBuilder().theme(ChartTheme.Matlab).title(String.format("Maximum Flux: %.3f cars/s", result.maximumFlux())).xAxisTitle("densité").yAxisTitle("vitesse").build();

        xyChart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
        xyChart.getStyler().setMarkerSize(1);
        xyChart.getStyler().setXAxisDecimalPattern("#.#");
        xyChart.getStyler().setYAxisMax(15.);

        XYSeries speedSeries = xyChart.addSeries("Vitesse", result.density(), result.speed());
        speedSeries.setLineStyle(SeriesLines.SOLID);
        speedSeries.setLineColor(Color.ORANGE);

        XYSeries fluxSeries = xyChart.addSeries("Flux", result.density(), result.flux());
        fluxSeries.setLineStyle(SeriesLines.SOLID);
        fluxSeries.setLineColor(Color.GRAY);
        return xyChart;
    }
}

package fr.marembert.tipe.display;

import fr.marembert.tipe.experiment.CarFluxResult;
import fr.marembert.tipe.math.RealMatrix2D;
import java.util.List;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;

public class CarsDisplay implements ResultDisplayHandler<CarFluxResult> {

    @Override
    public void displayResult(CarFluxResult result) {

        XYChart positionChart = chart("Car's Position", "Temps (s)", "Position (m)", result.timeSample(), result.positions());

        XYChart displacementChart = chart("Car's Displacement", "Temps (s)", "Delta (m)", result.timeSample(), result.displacement());

        XYChart speedChart = chart("Car's Speed", "Temps (s)", "Vitesse (m/s)", result.timeSample(), result.speeds());

        new SwingWrapper<>(List.of(positionChart, displacementChart, speedChart)).displayChartMatrix();
    }

    private XYChart chart(String title, String abs, String ord, double[] timeSample, RealMatrix2D values) {
        XYChart xyChart = new XYChartBuilder().theme(ChartTheme.Matlab).title(title).xAxisTitle(abs).yAxisTitle(ord).build();

        xyChart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
        xyChart.getStyler().setMarkerSize(2);
        xyChart.getStyler().setXAxisDecimalPattern("#.#");

        int i = 0;

        for (double[] series : values) {
            XYSeries xySeries = xyChart.addSeries(String.valueOf(i++), timeSample, series);
            xySeries.setLineStyle(SeriesLines.SOLID);
            xySeries.setLineColor(XChartSeriesColors.BLUE);
        }
        return xyChart;
    }
}

package fr.marembert.tipe.display;

import fr.marembert.tipe.experiment.CarsSpeedResult;
import fr.marembert.tipe.math.RealMatrix2D;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;

public class CarsSpeedDisplay implements ResultDisplayHandler<CarsSpeedResult> {

    @Override
    public void displayResult(CarsSpeedResult result) {

        // Create Chart
        XYChart chart = new XYChartBuilder().theme(ChartTheme.Matlab).title(result.title()).xAxisTitle("Temps (s)").yAxisTitle("Vitesse (m/s)").build();

        // Customize Chart
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
        chart.getStyler().setLegendPosition(LegendPosition.InsideSW);
        chart.getStyler().setMarkerSize(3);

        // Series

        RealMatrix2D speeds = result.speeds();

        for (int row = 0; row < speeds.getNumberOfRows(); row++) {
            double[] series = speeds.getDoubleRow(row);

            XYSeries xySeries = chart.addSeries(String.valueOf(row), result.timeSample(), series);
            xySeries.setLineStyle(SeriesLines.SOLID);
            xySeries.setLineColor(XChartSeriesColors.BLUE);
        }

        new SwingWrapper<>(chart).displayChart();
    }
}

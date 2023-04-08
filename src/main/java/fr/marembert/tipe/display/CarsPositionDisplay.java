package fr.marembert.tipe.display;

import fr.marembert.tipe.experiment.CarsPositionResult;
import fr.marembert.tipe.math.RealMatrix2D;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;

public class CarsPositionDisplay implements ResultDisplayHandler<CarsPositionResult> {

    @Override
    public void displayResult(CarsPositionResult result) {

        XYChart chart = new XYChartBuilder().theme(ChartTheme.Matlab).title(result.title()).xAxisTitle("Temps (s)").yAxisTitle("Position (m)").build();

        chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
        chart.getStyler().setMarkerSize(3);
        chart.getStyler().setXAxisDecimalPattern("#.#");

        RealMatrix2D speeds = result.positions();

        int i = 0;

        for (double[] series : speeds) {
            XYSeries xySeries = chart.addSeries(result.labels()[i++], result.timeSample(), series);
            xySeries.setLineStyle(SeriesLines.SOLID);
            xySeries.setLineColor(XChartSeriesColors.BLUE);
        }

        new SwingWrapper<>(chart).displayChart();
    }
}

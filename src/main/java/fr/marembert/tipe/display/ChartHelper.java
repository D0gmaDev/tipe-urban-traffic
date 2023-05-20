package fr.marembert.tipe.display;

import fr.marembert.tipe.math.RealMatrix2D;
import java.awt.Color;
import java.util.function.IntFunction;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;

class ChartHelper {

    static XYChart makeChart(String title, String abs, String ord, double[] timeSample, RealMatrix2D values, IntFunction<String> seriesName) {
        XYChart xyChart = new XYChartBuilder().theme(ChartTheme.Matlab).title(title).xAxisTitle(abs).yAxisTitle(ord).build();

        xyChart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Line);
        xyChart.getStyler().setMarkerSize(1);
        xyChart.getStyler().setXAxisDecimalPattern("#.#");

        Color[] seriesColors = new XChartSeriesColors().getSeriesColors();

        int i = 0;

        for (double[] series : values) {
            XYSeries xySeries = xyChart.addSeries(seriesName.apply(i++), timeSample, series);
            xySeries.setLineStyle(SeriesLines.SOLID);
            xySeries.setLineColor(seriesColors[i % seriesColors.length]);
        }
        return xyChart;
    }

}

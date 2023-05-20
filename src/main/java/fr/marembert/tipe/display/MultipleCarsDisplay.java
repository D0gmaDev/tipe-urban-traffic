package fr.marembert.tipe.display;

import fr.marembert.tipe.experiment.CarFluxResult;
import fr.marembert.tipe.experiment.MultipleExperiment.MultipleResult;
import java.util.List;
import org.knowm.xchart.SwingWrapper;

public class MultipleCarsDisplay implements ResultDisplayHandler<MultipleResult<CarFluxResult>> {

    @Override
    public void displayResult(MultipleResult<CarFluxResult> multipleResult) {

        List<CarFluxResult> results = multipleResult.getResults();

        new SwingWrapper<>(results.stream().map(result -> ChartHelper.makeChart("Displacement", "Temps (s)", "Distance (m)", result.timeSample(), result.displacements(), id -> "Car #" + id)).toList())
                .displayChartMatrix();
    }
}

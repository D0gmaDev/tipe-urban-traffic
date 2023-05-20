package fr.marembert.tipe.experiment;

import fr.marembert.tipe.experiment.MultipleExperiment.MultipleResult;
import java.util.ArrayList;
import java.util.List;

public class MultipleExperiment<T extends TrafficResult> implements TrafficExperiment<MultipleResult<T>> {

    private final List<TrafficExperiment<T>> experiments;

    public MultipleExperiment(List<TrafficExperiment<T>> experiments) {
        this.experiments = experiments;
    }

    public MultipleExperiment() {
        this.experiments = new ArrayList<>();
    }

    public void addExperiment(TrafficExperiment<T> experiment) {
        this.experiments.add(experiment);
    }

    @Override
    public MultipleResult<T> runExperiment() {

        MultipleResult<T> results = new MultipleResult<>();

        int i = 0;
        for (TrafficExperiment<T> experiment : this.experiments) {
            System.out.printf("Running experiment %d of %d...%n", ++i, this.experiments.size());
            results.addResult(experiment.runExperiment());
        }

        return results;
    }

    public static class MultipleResult<T extends TrafficResult> implements TrafficResult {

        private final List<T> results = new ArrayList<>();

        public void addResult(T result) {
            this.results.add(result);
        }

        public List<T> getResults() {
            return this.results;
        }
    }
}

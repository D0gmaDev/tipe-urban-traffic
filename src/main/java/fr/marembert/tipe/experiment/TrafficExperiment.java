package fr.marembert.tipe.experiment;

import java.time.Duration;

/**
 * The base interface for every experiment.
 *
 * @param <T> the result type that the experiment produces.
 * @author David Marembert
 */
public interface TrafficExperiment<T extends TrafficResult> {

    /**
     * Main method to run the experiment. Should be called only once.
     *
     * @param timout the duration before the experiment times out after its start.
     * @return the result of the experiment.
     */
    T runExperiment(Duration timout);

}

package fr.marembert.tipe.display;

import fr.marembert.tipe.experiment.TrafficResult;
import java.util.List;

public interface ResultDisplayHandler<T extends TrafficResult> {

    void displayResult(T result);

    default void displayMany(List<T> results) {
        results.forEach(this::displayResult);
    }

}

package fr.marembert.tipe.display;

import fr.marembert.tipe.experiment.TrafficResult;

public interface ResultDisplayHandler<T extends TrafficResult> {

    void displayResult(T result);

}

package fr.marembert.tipe.traffic;

/**
 * Represents an object that changes after a short time span.
 * The short time span ({@value DEFAULT_TIME_STEP} by default) allows to make a first derivative approximation.
 */
public interface DynamicTick {

    double DEFAULT_TIME_STEP = 1e-5; // seconds

    /**
     * Triggers the update of the object after the time span.
     *
     * @param timeSpan the short time elapsed (in seconds)
     */
    void tick(double timeSpan);

}

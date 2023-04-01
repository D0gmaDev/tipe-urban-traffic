package fr.marembert.tipe.traffic;

public class Car implements DynamicTick {

    private final int id;

    private double position;
    private double speed;
    private double acceleration;

    public Car(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public double getPosition() {
        return position;
    }

    @Override
    public void tick(double timeSpan) {
        this.speed += acceleration * timeSpan;
        this.position += speed * timeSpan;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", position=" + position +
                ", speed=" + speed +
                ", acceleration=" + acceleration +
                '}';
    }
}

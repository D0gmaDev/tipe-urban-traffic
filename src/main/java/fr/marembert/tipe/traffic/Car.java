package fr.marembert.tipe.traffic;

public class Car implements DynamicTick {

    private final int id;

    private final double mass = 1_000; // kg
    private final double length; // m

    private double position; // m
    private double speed; // m * s^(-1)
    private double acceleration; // m * s^(-2)

    public Car(int id, double length) {
        this.id = id;
        this.length = length;
    }

    public Car(int id, double length, double position, double speed, double acceleration) {
        this.id = id;
        this.length = length;
        this.position = position;
        this.speed = speed;
        this.acceleration = acceleration;
    }

    @Override
    public void tick(double timeSpan) {
        this.speed += acceleration * timeSpan;
        this.position += speed * timeSpan;
    }

    public int getId() {
        return id;
    }

    public double getMass() {
        return mass;
    }

    public double getLength() {
        return length;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", mass=" + mass +
                ", length=" + length +
                ", position=" + position +
                ", speed=" + speed +
                ", acceleration=" + acceleration +
                '}';
    }
}

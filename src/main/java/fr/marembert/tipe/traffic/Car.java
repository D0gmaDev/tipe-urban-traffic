package fr.marembert.tipe.traffic;

public class Car implements DynamicTick {

    private final int id;

    private final double mass;
    private final double length;

    private double position;
    private double speed;
    private double acceleration;

    public Car(int id, double mass, double length, double position) {
        this.id = id;
        this.mass = mass;
        this.length = length;
        this.position = position;
    }

    public Car(int id, double mass, double length, double position, double speed) {
        this.id = id;
        this.mass = mass;
        this.length = length;
        this.position = position;
        this.speed = speed;
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

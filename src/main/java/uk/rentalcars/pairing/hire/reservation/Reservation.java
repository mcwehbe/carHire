package uk.rentalcars.pairing.hire.reservation;

import uk.rentalcars.pairing.hire.vehicles.Car;

public class Reservation {

    private int id;
    private Car car;
    private int driverAge;

    public Reservation() {
    }

    public Reservation(Car car, int driverAge) {
        this(0, car, driverAge);
    }

    public Reservation(int id, Car car, int driverAge){
        this.id = id;
        this.car = car;
        this.driverAge = driverAge;
    }

    public int getId() { return id; }

    public Car getCar() {
        return car;
    }

    public int getDriverAge() {
        return driverAge;
    }
}

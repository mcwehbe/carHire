package uk.rentalcars.pairing.hire.vehicles;


import java.util.Objects;

public final class Car {

    private String make;
    private String model;

    public Car() {
    }

    public Car(String carMake, String carModel) {
        this.make = carMake;
        this.model = carModel;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getDisplayName() {
        return make + " " + model;
    }

    public boolean dataEquals(String make, String model) {
        return Objects.equals(make, this.make) &&
                Objects.equals(model, this.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(make, model);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return dataEquals(car.make, car.model);
    }
}

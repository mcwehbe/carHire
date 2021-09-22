package uk.rentalcars.pairing.hire.vehicles;

import java.util.Objects;

public class CarResponse {

    private String make;
    private String model;

    public CarResponse() {
    }

    public CarResponse(Car car) {
        this.make = car.getMake();
        this.model = car.getModel();
    }

    public String getMake() {
        return this.make;
    }

    public String getModel() {
        return this.model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CarResponse that = (CarResponse) o;
        return Objects.equals(make, that.make) &&
                Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), make, model);
    }
}

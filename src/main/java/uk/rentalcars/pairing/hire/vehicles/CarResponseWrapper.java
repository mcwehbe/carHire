package uk.rentalcars.pairing.hire.vehicles;

import java.util.List;

public class CarResponseWrapper {

    private List<CarResponse> carResponses;

    public CarResponseWrapper() {
    }

    public CarResponseWrapper(List<CarResponse> carResponses) {
        this.carResponses = carResponses;
    }

    public List<CarResponse> getCarResponses() {
        return this.carResponses;
    }
}

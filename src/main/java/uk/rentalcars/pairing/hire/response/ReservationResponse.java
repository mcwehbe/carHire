package uk.rentalcars.pairing.hire.response;

import uk.rentalcars.pairing.hire.reservation.Reservation;
import uk.rentalcars.pairing.hire.vehicles.Car;

public class ReservationResponse {

    private Reservation reservation;

    public ReservationResponse() {
    }

    public ReservationResponse(Reservation reservation) {
        this.reservation = reservation;
    }

    public int getReservationId() {
        return reservation.getId();
    }

    public Car getCar() {
        return reservation.getCar();
    }

    public int getDriverAge() {
        return reservation.getDriverAge();
    }
}

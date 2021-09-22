package uk.rentalcars.pairing.hire.reservation;

import javax.inject.Named;

@Named
public class ReservationValidator {

    private static int MIN_AGE = 17;

    public boolean isValid(Reservation reservation) {
        return reservation.getDriverAge() >= MIN_AGE;
    }
}

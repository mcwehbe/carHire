package uk.rentalcars.pairing.hire.unit.reservation;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import uk.rentalcars.pairing.hire.reservation.Reservation;
import uk.rentalcars.pairing.hire.reservation.ReservationValidator;


class ReservationValidatorTest {

    @Test
    public void when_ageIsSeventeen_then_ReservationIsValid() {
        Reservation reservation = new Reservation(null, 17);
        ReservationValidator validator = new ReservationValidator();
        assertTrue(validator.isValid(reservation));
    }

    @Test
    public void when_ageIsSixteen_then_ReservationIsNotValid() {
        Reservation reservation = new Reservation(null, 16);
        ReservationValidator validator = new ReservationValidator();
        assertFalse(validator.isValid(reservation));
    }

    @Test
    public void when_ageIsEighty_then_ReservationIsValid() {
        Reservation reservation = new Reservation(null, 80);
        ReservationValidator validator = new ReservationValidator();
        assertTrue(validator.isValid(reservation));
    }
}

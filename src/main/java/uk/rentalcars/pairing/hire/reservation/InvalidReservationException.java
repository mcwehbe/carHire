package uk.rentalcars.pairing.hire.reservation;

public class InvalidReservationException extends RuntimeException{
    public InvalidReservationException(String s) {
        super(s);
    }
}
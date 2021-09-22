package uk.rentalcars.pairing.hire.reservation;


import java.util.concurrent.atomic.AtomicInteger;

public final class ReservationIdSequence {

    private static final ReservationIdSequence INSTANCE = new ReservationIdSequence();
    private final AtomicInteger sequenceNumber;

    private ReservationIdSequence() {
        sequenceNumber = new AtomicInteger(1);
    }

    public static int getNextReservationId() {
        return INSTANCE.sequenceNumber.getAndIncrement();
    }
}

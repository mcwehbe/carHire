package uk.rentalcars.pairing.hire.reservation;


import org.springframework.web.bind.annotation.RequestBody;
import uk.rentalcars.pairing.hire.hirecompany.HireCompany;
import uk.rentalcars.pairing.hire.vehicles.Car;
import uk.rentalcars.pairing.hire.vehicles.CarNotFoundException;
import uk.rentalcars.pairing.hire.vehicles.CarRepository;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class CarReserver {

    private ReservationValidator reservationValidator;

    public CarReserver(ReservationValidator reservationValidator) {
        this.reservationValidator = reservationValidator;
    }

    public Reservation reserveCar(@RequestBody Reservation reservation, HireCompany company) {
        CarRepository carRepository = new CarRepository();

        Car car = reservation.getCar();

        if (!carRepository.carExists(car) || !company.isAvailable(car)) {
            throw new CarNotFoundException("Can not find car " + car);
        }

        if (!reservationValidator.isValid(reservation)) {
            throw new InvalidReservationException("Reservation is invalid");
        }

        int id = ReservationIdSequence.getNextReservationId();
        Reservation confirmedReservation = new Reservation(id, reservation.getCar(), reservation.getDriverAge());
        company.reserve(confirmedReservation);
        return confirmedReservation;
    }

}

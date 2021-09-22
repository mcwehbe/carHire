package uk.rentalcars.pairing.hire.unit.reservation;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.rentalcars.pairing.hire.hirecompany.HireCompany;
import uk.rentalcars.pairing.hire.reservation.CarReserver;
import uk.rentalcars.pairing.hire.reservation.InvalidReservationException;
import uk.rentalcars.pairing.hire.reservation.Reservation;
import uk.rentalcars.pairing.hire.reservation.ReservationValidator;
import uk.rentalcars.pairing.hire.vehicles.Car;

public class CarReserverTest {

  private ReservationValidator reservationValidator;
  private CarReserver carReserver;

  private Reservation reservation;
  private HireCompany company;

  @BeforeEach
  public void setUp() {

    reservationValidator = mock(ReservationValidator.class);
    when(reservationValidator.isValid(any())).thenReturn(true);

    carReserver = new CarReserver(reservationValidator);

    Car car = new Car("Audi", "A6");
    reservation = new Reservation(car, 18);

    List<Car> cars = new ArrayList<>();
    cars.add(car);
    company = new HireCompany("test company", cars);
  }

  @Test
  public void given_driverIsValid_then_makesReservation() {
    assertNotNull(carReserver.reserveCar(reservation, company));
  }

  @Test
  public void given_driverIsInvalid_then_doesNotMakeReservation() {
    when(reservationValidator.isValid(any())).thenReturn(false);
    assertThrows(InvalidReservationException.class,
        () -> carReserver.reserveCar(reservation, company));
  }
}

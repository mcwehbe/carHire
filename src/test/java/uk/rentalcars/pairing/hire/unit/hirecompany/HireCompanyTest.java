package uk.rentalcars.pairing.hire.unit.hirecompany;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.rentalcars.pairing.hire.hirecompany.HireCompany;
import uk.rentalcars.pairing.hire.reservation.Reservation;
import uk.rentalcars.pairing.hire.vehicles.Car;

public class HireCompanyTest {

    private HireCompany hireCompany;

    @BeforeEach
    public void setUp() {
        List<Car> cars = new ArrayList<>(Arrays.asList(

                new Car("Audi", "A6"),
                new Car("Audi", "A5"),
                new Car("Ford", "Mondeo"),
                new Car("Ford", "Focus")

        ));

        hireCompany = new HireCompany("Test hire company", cars);
    }

    @Test
    public void when_bookingACar_then_theCarToBeRemovedFromAvaliableCars() throws Exception {

        hireCompany.reserve(new Reservation(1, new Car("Ford", "Focus"), 21));

        assertEquals(3, hireCompany.getAvailableCars().size());

    }

    @Test
    public void when_noCarsAreBooked_then_reservedCarsAreEmpty() {
        assertTrue(hireCompany.getReservations().isEmpty());
    }

    @Test
    public void when_bookingACar_then_theCarToBeAddedToReservedCars() {

        Car carToBook = new Car("Ford", "Focus");
        hireCompany.reserve(new Reservation(1, carToBook, 21));
        assertEquals(1, hireCompany.getReservations().size());
        assertEquals(carToBook, hireCompany.getReservations().get(0).getCar());
    }

    @Test
    public void when_carIsNotBooked_then_carIsAvailable() {
        assertTrue(hireCompany.isAvailable(new Car("Audi", "A6")));
    }

    @Test
    public void when_carIsBooked_then_carIsNotAvailable() {

        Car carToBook = new Car("Audi", "A6");
        hireCompany.reserve(new Reservation(1, carToBook, 21));
        assertFalse(hireCompany.isAvailable(carToBook));
    }
}

package uk.rentalcars.pairing.hire.unit.vehicles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import uk.rentalcars.pairing.hire.vehicles.Car;


public class CarTest {

    private static final String MAKE = "Ford";
    private static final String MODEL = "Focus";
    private static final String NOT_THE_SAME = "NOT THE SAME";
    private Car carUnderTest = new Car(MAKE, MODEL);


    @Test
    public void given_MakeisGoodAndModelIsGood_then_iGetTrue() {

        Car car = new Car(MAKE, MODEL);

        assertTrue(carUnderTest.equals(car));
    }

    @Test
    public void given_MakeisGoodAndModelIsBad_then_iGetFalse() {

        Car car = new Car(MAKE, NOT_THE_SAME);

        assertTrue(!carUnderTest.equals(car));
    }

    @Test
    public void given_MakeisBadAndModelIsGood_then_iGetFalse() {

        Car car = new Car(NOT_THE_SAME, MODEL);

        assertTrue(!carUnderTest.equals(car));
    }

    @Test
    public void given_MakeisBadAndModelIsBad_then_iGetFalse() {

        Car car = new Car(NOT_THE_SAME, NOT_THE_SAME);

        assertTrue(!carUnderTest.equals(car));
    }

    @Test
    public void given_MakeAndModelArePopulated_then_iGetDisplayName() {
        Car car = new Car("Yellow", "Taxi");

        assertEquals("Yellow Taxi", car.getDisplayName());
    }

}

package uk.rentalcars.pairing.hire.vehicles;


import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named
public class CarRepository {

    private static List<Car> availableCars;

    static {
         availableCars = new ArrayList<>(Arrays.asList(
            new Car("Audi", "A6"),
            new Car("Audi", "A5"),
            new Car("Ford", "Mondeo"),
            new Car("Ford", "Focus")

        ));
    }

    public static List<Car> getCars(){
        return availableCars;
    }

    public boolean carExists(Car car) {
        return availableCars.contains(car);
    }
}

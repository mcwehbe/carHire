package uk.rentalcars.pairing.hire.vehicles;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cars/")
public class CarController {

    private CarRepository carRepository;

    @Inject
    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Car>> getMakes() {
        return ResponseEntity.ok(CarRepository.getCars());
    }


    @RequestMapping(method = RequestMethod.GET, value = "{make}")
    public ResponseEntity<List<Car>> getModels(@PathVariable("make") String make) {
        List<Car> cars = new ArrayList<>();

        carRepository.getCars().forEach(car -> {
            if (make.equals(car.getMake())) {
                cars.add(car);
            }
        });

        if (cars.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }


        return ResponseEntity.ok(cars);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{make}/{model}")
    public ResponseEntity<CarResponse> getByMakeModel(@PathVariable("make") String make, @PathVariable("model") String model) {
        CarResponse carResponse = null;

        for (Car car : CarRepository.getCars()) {
            if (car.dataEquals(make, model)) {
                carResponse = new CarResponse(car);
            }
        }

        if (carResponse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        return ResponseEntity.ok(carResponse);
    }

}
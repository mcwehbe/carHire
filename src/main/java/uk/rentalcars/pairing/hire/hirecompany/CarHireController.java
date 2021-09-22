package uk.rentalcars.pairing.hire.hirecompany;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import uk.rentalcars.pairing.hire.reservation.CarReserver;
import uk.rentalcars.pairing.hire.reservation.InvalidReservationException;
import uk.rentalcars.pairing.hire.reservation.Reservation;
import uk.rentalcars.pairing.hire.response.CarHireResponseWrapper;
import uk.rentalcars.pairing.hire.response.HireCompanyResponse;
import uk.rentalcars.pairing.hire.response.ReservationResponse;
import uk.rentalcars.pairing.hire.vehicles.CarNotFoundException;

@RestController
@RequestMapping("/carhirecompanies/")
public class CarHireController {

    private final CarHireRepository carHireRepository;
    private final CarReserver carReserver;

    public CarHireController(CarHireRepository carHireRepository, CarReserver carReserver) {
        this.carHireRepository = carHireRepository;
        this.carReserver = carReserver;
    }

    @GetMapping
    public ResponseEntity<CarHireResponseWrapper> getAllCompanies() {
        CarHireResponseWrapper carHireWrapper = new CarHireResponseWrapper(CarHireRepository.getCompanies());

        return ResponseEntity.ok(carHireWrapper);
    }

    @GetMapping(value = "{hirecompany}")
    public ResponseEntity<HireCompanyResponse> getCompanyByName(@PathVariable("hirecompany") String hireCompany) {
        HireCompany company = carHireRepository.getHireCompany(hireCompany);

        HireCompanyResponse response = new HireCompanyResponse(company.getName());

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "{hirecompany}/reservation/{reservationid}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable("hirecompany") String hireCompany, @PathVariable("reservationid") Integer reservationId) {
        HireCompany company = carHireRepository.getHireCompany(hireCompany);

        if (hireCompany == null || reservationId == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Reservation reservation = company.getReservationById(reservationId);

        if (reservation == null) {
            return ResponseEntity.badRequest().body(null);
        }

        ReservationResponse response = new ReservationResponse(reservation);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "{hirecompany}/reservations")
    public ResponseEntity bookCarForHireCompany(@PathVariable("hirecompany") String hireCompany, @RequestBody Reservation reservation) {
        HireCompany company = carHireRepository.getHireCompany(hireCompany);

        if (company == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            Reservation confirmedReservation = carReserver.reserveCar(reservation, company);

            UriComponents uriComponents = MvcUriComponentsBuilder
                    .fromMethodName(this.getClass(), "getReservationById", hireCompany, confirmedReservation.getId())
                    .buildAndExpand();
            URI uri = uriComponents.toUri();

            return ResponseEntity.created(uri).build();
        } catch (CarNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidReservationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

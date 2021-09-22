package uk.rentalcars.pairing.hire.functional.hirecompany;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import uk.rentalcars.pairing.hire.hirecompany.CarHireRepository;
import uk.rentalcars.pairing.hire.hirecompany.HireCompany;
import uk.rentalcars.pairing.hire.reservation.Reservation;
import uk.rentalcars.pairing.hire.response.CarHireResponseWrapper;
import uk.rentalcars.pairing.hire.response.HireCompanyResponse;
import uk.rentalcars.pairing.hire.vehicles.Car;



@SpringBootTest(webEnvironment= WebEnvironment.DEFINED_PORT, value = "CarHireConfig")
class CarHireControllerTest {

    private static final String MINE = "mine";
    private static final String CAR_HIRE_URL = "http://localhost:8080/carhirecompanies/";
    private static final String CAR_HIRE_RESERVATION_PATH = "/reservations";

    @BeforeEach
    public void cancelAllReservations() {
        // Make each test start from a clean base
        HireCompany company = new CarHireRepository().getHireCompany(MINE);
        List<Reservation> reservations = new ArrayList<>(company.getReservations());
        reservations.forEach(company::cancelReservation);
    }

    @Test
    public void given_iAskforCompanies_then_iGetAListOfAllCompanies() {

        RestTemplate restTemplate = new RestTemplate();

        CarHireResponseWrapper res = restTemplate.getForObject(CAR_HIRE_URL, CarHireResponseWrapper.class);

        assertEquals(res.getCompanies().size(), 2);

    }

    @Test
    public void given_iAskForACompanyByName_then_iGetCompanyInfo() {

        RestTemplate restTemplate = new RestTemplate();

        HireCompanyResponse res = restTemplate.getForObject(CAR_HIRE_URL + MINE, HireCompanyResponse.class);

        assertEquals(res.getName(), MINE);
    }

    @Test
    public void given_iMakeAReservation_then_itsLocationIsReturned() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        Reservation reservation = new Reservation(new Car("Ford","Mondeo"), 18);

        ResponseEntity<String> response = restTemplate.postForEntity(new URI(CAR_HIRE_URL + MINE + CAR_HIRE_RESERVATION_PATH), reservation, String.class);
        assertEquals(201, response.getStatusCode().value() );
        String location = response.getHeaders().get("Location").get(0);

        assertFalse(location.isEmpty());

        Reservation bookedReservation = restTemplate.getForObject(location, Reservation.class);
        assertEquals(reservation.getDriverAge(), bookedReservation.getDriverAge());
        assertEquals(reservation.getCar(), bookedReservation.getCar());
    }


    @Test
    public void given_iReserveACarTwice_then_a404isReturned() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();

        Reservation reservation = new Reservation(new Car("Ford","Mondeo"), 18);

        ResponseEntity<String> response = restTemplate.postForEntity(new URI(CAR_HIRE_URL + MINE + CAR_HIRE_RESERVATION_PATH), reservation, String.class);
        assertEquals(201, response.getStatusCode().value() );

        try {
            restTemplate.postForEntity(new URI(CAR_HIRE_URL + MINE + CAR_HIRE_RESERVATION_PATH), reservation, String.class);
        }catch(HttpClientErrorException e){
            assertEquals(404, e.getStatusCode().value() );
            return;
        }
        assertFalse(true);
    }

    @Test
    public void given_iReserveAnUnknownCar_then_a404isReturned() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        Reservation reservation = new Reservation(new Car("Ford","Prefect"), 18);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(new URI(CAR_HIRE_URL + MINE + CAR_HIRE_RESERVATION_PATH), reservation, String.class);
            assertFalse(false, "Should have got a 404 thrown but got " + response.getStatusCode());
        }catch(HttpClientErrorException e){
            assertEquals(404, e.getStatusCode().value() );
            return;
        }
        assertFalse(true);
    }

}

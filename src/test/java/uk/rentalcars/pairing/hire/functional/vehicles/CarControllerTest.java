package uk.rentalcars.pairing.hire.functional.vehicles;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import uk.rentalcars.pairing.hire.vehicles.CarResponse;

@SpringBootTest(webEnvironment= WebEnvironment.DEFINED_PORT, value = "CarHireConfig")
public class CarControllerTest {

    private static final String MAKE = "Ford";
    private static final String MODEL = "Focus";
    private static final String BAD_MODEL = "badmodel";
    private static final String CAR_URL = "http://localhost:8080/cars/";

    @Test
    public void given_iRequestAListOfCars_then_iGetAllCars() {
        RestTemplate restTemplate = new RestTemplate();

        List res = restTemplate.getForObject(CAR_URL + MAKE, List.class);
        assertEquals(2, res.size());
    }

    @Test
    public void given_iAskForAMake_then_iGetAllModelsForTheMake() {
        RestTemplate restTemplate = new RestTemplate();

        List res = restTemplate.getForObject(CAR_URL + MAKE, List.class);

        assertEquals(2, res.size());
    }

    @Test
    public void given_iAskForABadMake_then_iGet404() {
        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.getForObject(CAR_URL + "notAMake", String.class);
        } catch (HttpClientErrorException e) {
            assertEquals(404, e.getStatusCode().value());
            return;
        }
        assertTrue(false);

    }

    @Test
    public void given_iAskForMakeModel_then_iGetCarInfo() {

        RestTemplate restTemplate = new RestTemplate();

        CarResponse resp = restTemplate.getForObject(CAR_URL + MAKE + "/" + MODEL, CarResponse.class);

        assertEquals("Ford", resp.getMake());
        assertEquals(MODEL, resp.getModel());
    }

    @Test
    public void given_iAskForABadModel_then_iGet404() {
        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.getForObject(CAR_URL + MAKE + "/notAModel", String.class);
        } catch (HttpClientErrorException e) {
            assertEquals(404, e.getStatusCode().value());
            return;
        }
        assertTrue(false);
    }

    @Test
    public void given_IAskforAMakeModelThatDNE_then_iGetA404() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.getForEntity(new URI(CAR_URL + MAKE + "/" + BAD_MODEL), String.class);
        } catch (HttpClientErrorException e) {
            assertEquals(404, e.getStatusCode().value());
            return;
        }
        assertTrue(false);
    }
}

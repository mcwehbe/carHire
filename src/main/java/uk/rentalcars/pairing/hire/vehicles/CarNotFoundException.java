package uk.rentalcars.pairing.hire.vehicles;

public class CarNotFoundException extends RuntimeException{
    public CarNotFoundException(String s) {
        super(s);
    }
}

package uk.rentalcars.pairing.hire.hirecompany;

import uk.rentalcars.pairing.hire.reservation.Reservation;
import uk.rentalcars.pairing.hire.vehicles.Car;
import uk.rentalcars.pairing.hire.vehicles.CarNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class HireCompany {
    private List<Car> availableCars = new ArrayList<>();
    private Map<Integer, Reservation> reservations = new HashMap<>();

    private String name;

    public HireCompany(String companyName, List<Car> carsForRent){

        availableCars.addAll(carsForRent);
        name = companyName;
    }

    public List<Car> getAvailableCars(){
        return availableCars;
    }

    public Reservation getReservationById(int id) {
        return reservations.get(id);
    }

    public List<Reservation> getReservations() {
        List<Reservation> reservationsCopy = new ArrayList<>(this.reservations.size());
        this.reservations.forEach((id, reservation) -> reservationsCopy.add(reservation));
        return reservationsCopy;
    }

    public String getName(){
        return name;
    }


    public void reserve(Reservation reservation) {

        availableCars.remove(reservation.getCar());
        reservations.put(reservation.getId(), reservation);
    }

    public void cancelReservation(Reservation reservation) {
        Reservation removed = reservations.remove(reservation.getId());
        if (removed != null) {
            availableCars.add(removed.getCar());
        } else {
            throw new CarNotFoundException("Tried to remove a car that is not reserved by this company");
        }
    }

    public boolean isAvailable(Car car) {
        return availableCars.contains(car);
    }
}

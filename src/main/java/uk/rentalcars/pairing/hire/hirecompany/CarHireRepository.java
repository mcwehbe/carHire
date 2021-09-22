package uk.rentalcars.pairing.hire.hirecompany;

import uk.rentalcars.pairing.hire.vehicles.CarRepository;

import javax.inject.Named;
import java.util.*;

@Named
public class CarHireRepository {
    private static Map<String, HireCompany> companies = new HashMap<>();

    static {
        HireCompany myHireCompany = new HireCompany("mine", CarRepository.getCars());
        companies.put(myHireCompany.getName(), myHireCompany);

        HireCompany anotherHireCompany = new HireCompany("another hire company", CarRepository.getCars());
        companies.put(anotherHireCompany.getName(), anotherHireCompany);

    }

    public static Collection<HireCompany> getCompanies(){
        return companies.values();
    }

    public HireCompany getHireCompany(String hireCompany) {
        return companies.get(hireCompany);
    }
}

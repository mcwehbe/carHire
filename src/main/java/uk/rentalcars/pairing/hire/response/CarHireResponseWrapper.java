package uk.rentalcars.pairing.hire.response;

import uk.rentalcars.pairing.hire.hirecompany.HireCompany;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CarHireResponseWrapper {

    private List<HireCompanyResponse> companies = new ArrayList<>();

    public CarHireResponseWrapper() {
    }

    public CarHireResponseWrapper(Collection<HireCompany> hireCompanies) {
        hireCompanies.forEach(hireCompany ->
                companies.add(new HireCompanyResponse(hireCompany.getName())));
    }

    public List<HireCompanyResponse> getCompanies() {
        return this.companies;
    }

}

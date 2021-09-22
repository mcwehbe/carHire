package uk.rentalcars.pairing.hire.response;

public class HireCompanyResponse {

    private String name;

    public HireCompanyResponse() {
    }

    public HireCompanyResponse(String companyName){
        name = companyName;
    }

    public String getName(){
        return name;
    }
}

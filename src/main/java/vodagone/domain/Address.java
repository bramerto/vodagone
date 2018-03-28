package vodagone.domain;

public class Address {
    private String street;
    private String postalcode;
    private String city;

    @Override
    public String toString() {
        return street + ", " + postalcode + ", " + city;
    }

    public void resolveAddress(String fullAddress) {
        String[] splittedAdress = fullAddress.split(", ");
        this.street = splittedAdress[0];
        this.postalcode = splittedAdress[1];
        this.city = splittedAdress[2];
    }
}

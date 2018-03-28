package vodagone.dto.response;

import vodagone.dto.CompactSubscription;

import java.util.ArrayList;

public class SubscriptionsForUserResponse {
    private double totalPrice;
    private ArrayList<CompactSubscription> abonnementen;

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public void setAbonnementen(ArrayList<CompactSubscription> abonnementen) {
        this.abonnementen = abonnementen;
    }
}

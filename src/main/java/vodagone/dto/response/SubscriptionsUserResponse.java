package vodagone.dto.response;

import vodagone.domain.compact.CompactSubscription;

import java.util.ArrayList;

public class SubscriptionsUserResponse implements IResponse {
    private double totalPrice;
    private ArrayList<CompactSubscription> abonnementen;

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public void setAbonnementen(ArrayList<CompactSubscription> abonnementen) {
        this.abonnementen = abonnementen;
    }

    public ArrayList<CompactSubscription> getAbonnementen() {
        return abonnementen;
    }

    public void addEntryToList(CompactSubscription compactSubscription) {
        abonnementen.add(compactSubscription);
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}

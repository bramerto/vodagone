package vodagone.dto.request;

import vodagone.domain.compact.CompactSubscription;

public class AddSubscriptionRequest implements IRequest {
    private int id;
    private String aanbieder;
    private String dienst;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getAanbieder() {
        return aanbieder;
    }

    public String getDienst() {
        return dienst;
    }

    public void setAanbieder(String aanbieder) {
        this.aanbieder = aanbieder;
    }

    public void setDienst(String dienst) {
        this.dienst = dienst;
    }

    public CompactSubscription toCompact() {
        CompactSubscription compactSubscription = new CompactSubscription();

        compactSubscription.setId(this.getId());
        compactSubscription.setAanbieder(this.getAanbieder());
        compactSubscription.setDienst(this.getDienst());

        return compactSubscription;
    }
}

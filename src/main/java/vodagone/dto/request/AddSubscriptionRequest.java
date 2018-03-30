package vodagone.dto.request;

public class AddSubscriptionRequest implements IRequest {
    private int id;
    private String aanbieder;
    private String dienst;

    public int getId() {
        return id;
    }

    public String getAanbieder() {
        return aanbieder;
    }

    public String getDienst() {
        return dienst;
    }
}

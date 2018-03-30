package vodagone.domain;

import java.util.Date;

public class Subscription implements IMappable {
    private int id;
    private String aanbieder;
    private String dienst;
    private String prijs;
    private double pricetag;
    private Date startDatum;
    private String verdubbeling;
    private boolean deelbaar;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAanbieder() {
        return aanbieder;
    }

    public void setAanbieder(String aanbieder) {
        this.aanbieder = aanbieder;
    }

    public String getDienst() {
        return dienst;
    }

    public void setDienst(String dienst) {
        this.dienst = dienst;
    }

    public String getPrijs() {
        return prijs;
    }

    public void setPrijs(String prijs) {
        this.prijs = prijs;
    }

    public double getPricetag() {
        return pricetag;
    }

    public void setPricetag(double pricetag) {
        this.pricetag = pricetag;
    }

    public Date getStartDatum() {
        return startDatum;
    }

    public void setStartDatum(Date startDatum) {
        this.startDatum = startDatum;
    }

    public String getVerdubbeling() {
        return verdubbeling;
    }

    public void setVerdubbeling(String verdubbeling) {
        this.verdubbeling = verdubbeling;
    }

    public boolean isDeelbaar() {
        return deelbaar;
    }

    public void setDeelbaar(boolean deelbaar) {
        this.deelbaar = deelbaar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

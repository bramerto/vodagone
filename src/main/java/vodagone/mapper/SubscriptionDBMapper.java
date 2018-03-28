package vodagone.mapper;

import vodagone.domain.Subscription;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubscriptionDBMapper {
    public ArrayList<Subscription> getSubscriptionList(ResultSet rs) throws SQLException {

        ArrayList<Subscription> subscriptions = new ArrayList<>();

        while (rs.next()) {
            subscriptions.add(mapToSubscription(rs));
        }

        return subscriptions;
    }

    public Subscription getSingleSubscription(ResultSet rs) throws SQLException {
        if (!rs.next()) return null;
        return mapToSubscription(rs);
    }

    private Subscription mapToSubscription(ResultSet rs) throws SQLException {
        Subscription subscription = new Subscription();

        subscription.setId(rs.getInt("id"));
        subscription.setAanbieder(rs.getString("aanbieder"));
        subscription.setDienst(rs.getString("dienst"));
        subscription.setPrijs(rs.getString("prijs"));
        subscription.setPricetag(rs.getDouble("pricetag"));
        subscription.setStartDatum(rs.getDate("startDatum"));
        subscription.setVerdubbeling(rs.getString("verdubbeling"));
        subscription.setDeelbaar(rs.getBoolean("deelbaar"));
        subscription.setStatus(rs.getString("status"));

        return subscription;
    }
}

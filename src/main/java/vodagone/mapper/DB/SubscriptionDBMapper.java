package vodagone.mapper.DB;

import vodagone.domain.Abonnement;
import vodagone.domain.Subscription;
import vodagone.domain.compact.CompactSubscription;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubscriptionDBMapper implements IDBMapper {
    public ArrayList<Subscription> getList(ResultSet rs) throws SQLException {

        if (!rs.next()) return null;
        rs.beforeFirst();
        ArrayList<Subscription> subscriptions = new ArrayList<>();

        while (rs.next()) {
            subscriptions.add(map(rs));
        }

        return subscriptions;
    }

    public Subscription getSingle(ResultSet rs) throws SQLException {
        if (!rs.next()) return null;
        return map(rs);
    }

    public ArrayList<CompactSubscription> getCompactList(ResultSet rs) throws SQLException {

        if (!rs.next()) return null;
        rs.beforeFirst();
        ArrayList<CompactSubscription> subscriptions = new ArrayList<>();

        while (rs.next()) {
            subscriptions.add(mapCompact(rs));
        }

        return subscriptions;
    }

    private CompactSubscription mapCompact(ResultSet rs) throws SQLException {
        CompactSubscription subscription = new CompactSubscription();

        subscription.setId(rs.getInt("id"));
        subscription.setAanbieder(rs.getString("aanbieder"));
        subscription.setDienst(rs.getString("dienst"));

        return subscription;
    }

    public Subscription map(ResultSet rs) throws SQLException {
        Subscription subscription = new Subscription();

        subscription.setId(rs.getInt("id"));
        subscription.setAanbieder(rs.getString("aanbieder"));
        subscription.setDienst(rs.getString("dienst"));
        subscription.setPrijs(rs.getString("prijs"));
        subscription.setPricetag(rs.getDouble("price"));
        subscription.setStartDatum(rs.getDate("startDatum"));
        subscription.setVerdubbeling(rs.getString("verdubbeling"));
        subscription.setDeelbaar(rs.getBoolean("deelbaar"));
        subscription.setStatus(rs.getString("status"));

        return subscription;
    }

    public Abonnement getSingleAbonnement(ResultSet rs) throws SQLException {
        if (!rs.next()) return null;
        return mapAbonnement(rs);
    }

    private Abonnement mapAbonnement(ResultSet rs) throws SQLException {
        Abonnement abonnement = new Abonnement();

        abonnement.setId(rs.getInt("id"));
        abonnement.setAanbieder(rs.getString("aanbieder"));
        abonnement.setDienst(rs.getString("dienst"));
        abonnement.setPrijs(rs.getDouble("prijs"));
        abonnement.setDeelbaar(rs.getBoolean("dienst"));

        return abonnement;
    }
}

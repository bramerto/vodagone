package vodagone.mapper.DB;

import vodagone.domain.Subscription;

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
}

package vodagone.store;

import vodagone.domain.Subscription;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Calendar;

public class SubscriptionDao implements ISubscriptionDao {

    private Connection connection;
    private String defaultSelect;

    @Inject
    public SubscriptionDao(@Named("connection") Connection connection) {
        defaultSelect = "SELECT a.id, a.aanbieder, a.dienst, a.prijs, a.deelbaar, " +
                        "ua.price, ua.status, ua.startDatum, ua.verdubbeling " +
                        "FROM abonnementen AS a " +
                        "INNER JOIN userabonnementen AS ua " +
                        "ON a.id = ua.abbonementid ";

        this.connection = connection;
    }

    public ResultSet getAllSubscriptions(String filter) throws SQLException {
        String sql = defaultSelect + "WHERE a.aanbieder = ? OR a.dienst = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, filter);
        preparedStatement.setString(2, filter);

        return preparedStatement.executeQuery();
    }

    public ResultSet getAllSubscriptions() throws SQLException {
        String sql = defaultSelect;

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }

    public ResultSet getAllSubscriptionsByUser(int userId) throws SQLException {

        String sql = defaultSelect + "WHERE ua.userid = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        return preparedStatement.executeQuery();
    }

    public ResultSet getSubscription(int id, int authenticatedUserId) throws SQLException {
        String sql = defaultSelect + "WHERE ua.userid = ? AND a.id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, authenticatedUserId);
        preparedStatement.setInt(2, id);
        return preparedStatement.executeQuery();
    }

    public void shareSubscription(int sharedToUserId, int toBeSharedSubscriptionId) throws SQLException {
        String sql = "INSERT INTO userabonnementen(userid, abbonementid) " +
                     "VALUES(?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, sharedToUserId);
        preparedStatement.setInt(2, toBeSharedSubscriptionId);
        preparedStatement.executeUpdate();
    }

    public void addSubscription(Subscription subscription, int userId) throws SQLException {
        String sql = "INSERT INTO userabonnementen(userId, abbonementid, price, status, startDatum) " +
                     "VALUES(?, ?, ?, ?, ?) ";

        Calendar calendar = Calendar.getInstance();
        Date date = new Date(calendar.getTime().getTime());

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, subscription.getId());
        preparedStatement.setDouble(3, subscription.getPricetag());
        preparedStatement.setString(4, "actief");
        preparedStatement.setDate(5, date);

        preparedStatement.executeUpdate();
    }

    public void upgradeSubscription(int id, int userId, String upgrade) throws SQLException {
        String sql = "UPDATE userabonnementen " +
                     "SET verdubbeling = ?, price = price*2 " +
                     "WHERE abbonementid = ? AND userid = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, upgrade);
        preparedStatement.setInt(2, id);
        preparedStatement.setInt(3, userId);
        preparedStatement.executeUpdate();
    }

    public void terminateSubscription(int id) throws SQLException {
        String sql = "UPDATE userabonnementen " +
                     "SET status = 'opgezegd' " +
                     "WHERE abbonementid = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }
}

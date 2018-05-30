package vodagone.store;

import org.junit.Test;
import vodagone.domain.Abonnement;
import vodagone.domain.Subscription;
import vodagone.domain.User;

import java.sql.*;
import java.util.Calendar;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SubscriptionDaoTest {

    private String defaultUserSelect = "SELECT a.id, a.aanbieder, a.dienst, a.prijs, a.deelbaar, " +
            "ua.price, ua.status, ua.startDatum, ua.verdubbeling " +
            "FROM abonnementen AS a " +
            "INNER JOIN userabonnementen AS ua " +
            "ON a.id = ua.abbonementid";

    private String defaultSelect = "SELECT id, aanbieder, dienst, prijs, deelbaar " +
                                   "FROM abonnementen";

    @Test
    public void getAllSubscriptions() {
        //SETUP
        String sql = defaultSelect;

        //MOCKS
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        try {
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);

            //TEST
            SubscriptionDao subscriptionDao = new SubscriptionDao(connection);
            subscriptionDao.getAllSubscriptions();

            verify(preparedStatement).executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllSubscriptionsWithFilter() {
        //SETUP
        String sql = defaultSelect + " WHERE aanbieder LIKE ? OR dienst LIKE ?";
        String filter = "test";

        //MOCKS
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        try {
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);

            //TEST
            SubscriptionDao subscriptionDao = new SubscriptionDao(connection);
            subscriptionDao.getAllSubscriptions(filter);

            verify(preparedStatement).setString(1, "%"+filter+"%");
            verify(preparedStatement).setString(2, "%"+filter+"%");
            verify(preparedStatement).executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllSubscriptionsByUser() {
        //SETUP
        String sql = defaultUserSelect + " WHERE ua.userid = ?";
        int userId = 1;

        //MOCKS
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        try {
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);

            //TEST
            SubscriptionDao subscriptionDao = new SubscriptionDao(connection);
            subscriptionDao.getAllSubscriptionsByUser(userId);

            verify(preparedStatement).setInt(1, userId);
            verify(preparedStatement).executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSubscription() {
        //SETUP
        String sql = defaultUserSelect + " WHERE ua.userid = ? AND a.id = ?";
        int userId = 1;
        int subscriptionId = 1;

        //MOCKS
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        try {
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);

            //TEST
            SubscriptionDao subscriptionDao = new SubscriptionDao(connection);
            subscriptionDao.getSubscription(subscriptionId, userId);

            verify(preparedStatement).setInt(1, userId);
            verify(preparedStatement).executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shareSubscription() {
        String sql = "INSERT INTO userabonnementen(userid, abbonementid) VALUES(?, ?)";
        int sharedToUserID = 1;
        int toBeSharedSubscriptionId = 1;

        //MOCKS
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        try {
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);

            //TEST
            SubscriptionDao subscriptionDao = new SubscriptionDao(connection);
            subscriptionDao.shareSubscription(sharedToUserID, toBeSharedSubscriptionId);

            verify(preparedStatement).setInt(1, sharedToUserID);
            verify(preparedStatement).setInt(2, toBeSharedSubscriptionId);
            verify(preparedStatement).executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addSubscription() {

        String sql = "INSERT INTO userabonnementen(userId, abbonementid, price, status, startDatum) " +
                "VALUES(?, ?, ?, ?, ?) ";
        Abonnement subscription = new Abonnement();

        //MOCKS
        Connection connection = mock(Connection.class);
        User user = mock(User.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        try {
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);

            //TEST
            SubscriptionDao subscriptionDao = new SubscriptionDao(connection);
            subscriptionDao.addSubscription(subscription, user.getId());

            verify(preparedStatement).setInt(1, subscription.getId());
            verify(preparedStatement).setInt(2, user.getId());
            verify(preparedStatement).setDouble(3, subscription.getPrijs());
            verify(preparedStatement).setString(4, "actief");

            verify(preparedStatement).executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void upgradeSubscription() {
        String sql = "UPDATE userabonnementen SET verdubbeling = ?, price = price*2 WHERE abbonementid = ? AND userid = ?";
        int subscriptionid = 1;
        int userid = 1;
        String upgrade = "";

        //MOCKS
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        try {
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);

            //TEST
            SubscriptionDao subscriptionDao = new SubscriptionDao(connection);
            subscriptionDao.upgradeSubscription(subscriptionid, userid, upgrade);

            verify(preparedStatement).setString(1, upgrade);
            verify(preparedStatement).setInt(2, subscriptionid);
            verify(preparedStatement).setInt(3, userid);

            verify(preparedStatement).executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void terminateSubscription() {
        String sql = "UPDATE userabonnementen SET status = 'opgezegd' WHERE abbonementid = ?";
        int subscriptionid = 1;

        //MOCKS
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        try {
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);

            //TEST
            SubscriptionDao subscriptionDao = new SubscriptionDao(connection);
            subscriptionDao.terminateSubscription(subscriptionid);

            verify(preparedStatement).setInt(1, subscriptionid);
            verify(preparedStatement).executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

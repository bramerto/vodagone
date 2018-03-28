package vodagone.store;

import vodagone.domain.Subscription;
import vodagone.domain.User;
import vodagone.mapper.SubscriptionDBMapper;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubscriptionDao {

    private Connection connection;

    @Inject
    private SubscriptionDBMapper subscriptionDBMapper; //TODO: DI does not work

    @Inject
    public SubscriptionDao(@Named("connection") Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Subscription> getAllSubscriptions(String filter) throws SQLException {
        String sql = "SELECT * FROM abbonementen WHERE aanbieder = ? OR dienst = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, filter);
        preparedStatement.setString(2, filter);
        ResultSet resultSet = preparedStatement.executeQuery();

        return subscriptionDBMapper.getSubscriptionList(resultSet);
    }

    public ArrayList<Subscription> getAllSubscriptions() throws SQLException {
        String sql = "SELECT * FROM abbonementen";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        return subscriptionDBMapper.getSubscriptionList(resultSet);
    }

    public ArrayList<Subscription> getAllSubscriptionsByUser(int userId) throws SQLException {

        String sql = "SELECT a.id, a.aanbieder, a.dienst, a.prijs, a.deelbaar, " +
                     "ua.price, ua.status, ua.startDatum, ua.verdubbeling " +
                     "FROM abbonementen AS a " +
                     "INNER JOIN userabbonementen AS ua ON a.id = ua.abbonementid " +
                     "WHERE ua.userid = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();

        return subscriptionDBMapper.getSubscriptionList(resultSet);
    }

    public Subscription getSubscription(int id, int authenticatedUserId) throws SQLException {
        String sql = "SELECT a.id, a.aanbieder, a.dienst, a.prijs, a.deelbaar, " +
                     "ua.price, ua.status, ua.startDatum, ua.verdubbeling, " +
                     "FROM abbonementen AS a " +
                     "INNER JOIN userabbonementen AS ua ON a.id = ua.abbonementid " +
                     "WHERE ua.userid = ? AND a.id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, authenticatedUserId);
        preparedStatement.setInt(2, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        return subscriptionDBMapper.getSingleSubscription(resultSet);
    }

    public void shareSubscription(User sharedToUser, Subscription toBeSharedSubscription) throws SQLException {
        String sql = "INSERT INTO userabbonementen(userid, abbonementid) VALUES(?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, sharedToUser.getId());
        preparedStatement.setInt(2, toBeSharedSubscription.getId());
        preparedStatement.executeUpdate();
    }

    public void addSubscription(Subscription subscription) throws SQLException {
        String sql = "INSERT OR REPLACE INTO abbonementen(id, aanbieder, dienst) VALUES(?, ?, ?) ";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, subscription.getId());
        preparedStatement.setString(2, subscription.getAanbieder());
        preparedStatement.setString(3, subscription.getDienst());
        preparedStatement.executeUpdate();
    }

    public void upgradeSubscription(int id, int userId, String upgrade) throws SQLException {
        String sql = "UPDATE userabbonementen SET verdubbeling = ?, price = price*2 WHERE abbonementid = ? AND userid = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, upgrade);
        preparedStatement.setInt(2, id);
        preparedStatement.setInt(3, userId);
        preparedStatement.executeUpdate();
    }

    public void terminateSubscription(Subscription subscription) throws SQLException {
        String sql = "UPDATE abbonementen SET status = 'opgezegd' WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, subscription.getId());
        preparedStatement.executeUpdate();
    }
}

package vodagone.store;

import org.junit.Before;
import org.junit.Test;
import vodagone.domain.Subscription;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class SubscriptionDaoTest {

    private Subscription subscription;

    @Before
    public void setup() {
        subscription = new Subscription();
        subscription.setId(1);
    }

    @Test
    public void getAllSubscriptions() {

    }

    @Test
    public void getAllSubscriptions1() {
    }

    @Test
    public void getAllSubscriptionsByUser() {
        //SETUP TEST
        String sql = "SELECT a.id, a.aanbieder, a.dienst, a.prijs, a.deelbaar, " +
                     "ua.price, ua.status, ua.startDatum, ua.verdubbeling " +
                     "FROM abbonementen AS a " +
                     "INNER JOIN userabbonementen AS ua ON a.id = ua.abbonementid " +
                     "WHERE ua.userid = ?";
        int id = 1;

        try {
            //PREPARE MOCK
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);
            ResultSet resultSet = mock(ResultSet.class);

            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);

            SubscriptionDao subscriptionDao = new SubscriptionDao(connection);
            subscriptionDao.getAllSubscriptionsByUser(id);

            //TEST
            verify(connection).prepareStatement(sql);
            verify(preparedStatement).setInt(1, this.subscription.getId());
            verify(preparedStatement).executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertTrue(true);
    }

    @Test
    public void getSubscription() {
    }

    @Test
    public void shareSubscription() {
    }

    @Test
    public void addSubscription() {
    }

    @Test
    public void upgradeSubscription() {
    }

    @Test
    public void terminateSubscription() {
    }
}

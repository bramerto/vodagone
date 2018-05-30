package vodagone.mapper.DB;

import org.junit.Test;
import vodagone.domain.Abonnement;
import vodagone.domain.Subscription;
import vodagone.domain.compact.CompactSubscription;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SubscriptionDBMapperTest {

    @Test
    public void getListSucceed() {
        try {
            ResultSet resultSet = mock(ResultSet.class);
            when(resultSet.next()).thenReturn(true, true, false);

            SubscriptionDBMapper subscriptionDBMapper = new SubscriptionDBMapper();
            ArrayList<Subscription> subscriptions = subscriptionDBMapper.getList(resultSet);

            assertNotNull(subscriptions);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getListFail() {
        try {
            ResultSet resultSet = mock(ResultSet.class);
            when(resultSet.next()).thenReturn(false);

            SubscriptionDBMapper subscriptionDBMapper = new SubscriptionDBMapper();
            ArrayList<Subscription> subscriptions = subscriptionDBMapper.getList(resultSet);

            assertNull(subscriptions);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCompactListSucceed() {
        try {
            ResultSet resultSet = mock(ResultSet.class);
            when(resultSet.next()).thenReturn(true, true, false);

            SubscriptionDBMapper subscriptionDBMapper = new SubscriptionDBMapper();
            ArrayList<CompactSubscription> subscriptions = subscriptionDBMapper.getCompactList(resultSet);

            assertNotNull(subscriptions);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCompactListFail() {
        try {
            ResultSet resultSet = mock(ResultSet.class);
            when(resultSet.next()).thenReturn(false);

            SubscriptionDBMapper subscriptionDBMapper = new SubscriptionDBMapper();
            ArrayList<CompactSubscription> subscriptions = subscriptionDBMapper.getCompactList(resultSet);

            assertNull(subscriptions);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSingleSucceed() {
        try {
            ResultSet resultSet = mock(ResultSet.class);
            when(resultSet.next()).thenReturn(true, true, false);

            SubscriptionDBMapper subscriptionDBMapper = new SubscriptionDBMapper();
            Subscription subscription = subscriptionDBMapper.getSingle(resultSet);

            assertNotNull(subscription);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSingleFail() {
        try {
            ResultSet resultSet = mock(ResultSet.class);
            when(resultSet.next()).thenReturn(false);

            SubscriptionDBMapper subscriptionDBMapper = new SubscriptionDBMapper();
            Subscription subscription = subscriptionDBMapper.getSingle(resultSet);

            assertNull(subscription);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSingleAbonnementSucceed() {
        try {
            ResultSet resultSet = mock(ResultSet.class);
            when(resultSet.next()).thenReturn(true, true, false);

            SubscriptionDBMapper subscriptionDBMapper = new SubscriptionDBMapper();
            Abonnement subscription = subscriptionDBMapper.getSingleAbonnement(resultSet);

            assertNotNull(subscription);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSingleAbonnementFail() {
        try {
            ResultSet resultSet = mock(ResultSet.class);
            when(resultSet.next()).thenReturn(false);

            SubscriptionDBMapper subscriptionDBMapper = new SubscriptionDBMapper();
            Abonnement subscription = subscriptionDBMapper.getSingleAbonnement(resultSet);

            assertNull(subscription);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

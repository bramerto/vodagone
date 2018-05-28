package vodagone.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import vodagone.domain.Subscription;
import vodagone.domain.User;
import vodagone.dto.request.AddSubscriptionRequest;
import vodagone.dto.response.SubscriptionsUserResponse;
import vodagone.mapper.DB.SubscriptionDBMapper;
import vodagone.mapper.SubscriptionResponseMapper;
import vodagone.store.SubscriptionDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionControllerTest {

    @InjectMocks
    private SubscriptionController subscriptionController;
    @Mock
    private SubscriptionDao subscriptionDao;
    @Mock
    private SubscriptionResponseMapper subscriptionResponseMapper;
    @Mock
    private SubscriptionDBMapper subscriptionDBMapper;
    @Mock
    private ArrayList<Subscription> subscriptionList;

    @Test
    public void addSubscriptionSucceed() {
        //SETUP
        Subscription subscription = mock(Subscription.class);
        User user = mock(User.class);
        ResultSet resultSet1 = mock(ResultSet.class);
        ResultSet resultSet2 = mock(ResultSet.class);
        SubscriptionsUserResponse subscriptionsUserResponse = mock(SubscriptionsUserResponse.class);

        AddSubscriptionRequest request = mock(AddSubscriptionRequest.class);

        try {
            //TEST
            when(subscriptionDao.getSubscription(request.getId(), user.getId())).thenReturn(resultSet1);
            when(subscriptionDBMapper.getSingle(resultSet1)).thenReturn(subscription);

            when(subscriptionDao.getAllSubscriptions()).thenReturn(resultSet2);
            when(subscriptionDBMapper.getList(resultSet2)).thenReturn(subscriptionList);
            when(subscriptionResponseMapper.mapToCompactResponse(subscriptionList)).thenReturn(subscriptionsUserResponse);

            subscriptionController.addSubscription(request, user);

            //VERIFY
            verify(subscriptionDao).addSubscription(subscription, user.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addSubscriptionFail() {
        //SETUP
        Subscription subscription = mock(Subscription.class);
        ResultSet resultSet1 = mock(ResultSet.class);
        ResultSet resultSet2 = mock(ResultSet.class);
        User user = mock(User.class);
        SubscriptionsUserResponse subscriptionsUserResponse = mock(SubscriptionsUserResponse.class);

        AddSubscriptionRequest request = mock(AddSubscriptionRequest.class);

        try {
            //TEST
            when(subscriptionDao.getSubscription(request.getId(), user.getId())).thenReturn(resultSet1);
            when(subscriptionDBMapper.getSingle(resultSet1)).thenReturn(subscription);

            when(subscriptionDao.getAllSubscriptions()).thenReturn(resultSet2);
            when(subscriptionDBMapper.getList(resultSet2)).thenReturn(null);
            when(subscriptionResponseMapper.mapToCompactResponse(new ArrayList<>())).thenReturn(subscriptionsUserResponse);

            subscriptionController.addSubscription(request, user);

            //VERIFY
            verify(subscriptionDao).addSubscription(subscription, user.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSubscriptionsForUserSucceed() {
        //SETUP
        ResultSet resultSet = mock(ResultSet.class);
        User user = mock(User.class);

        try {
            //TEST
            when(subscriptionDao.getAllSubscriptionsByUser(user.getId())).thenReturn(resultSet);
            when(subscriptionDBMapper.getList(resultSet)).thenReturn(subscriptionList);

            subscriptionController.getSubscriptionsForUser(user);

            //VERIFY
            verify(subscriptionResponseMapper).mapToCompactResponse(subscriptionList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSubscriptionsForUserFail() {
        //SETUP
        ResultSet resultSet = mock(ResultSet.class);
        User user = mock(User.class);

        try {
            //TEST
            when(subscriptionDao.getAllSubscriptionsByUser(user.getId())).thenReturn(resultSet);
            when(subscriptionDBMapper.getList(resultSet)).thenReturn(null);

            subscriptionController.getSubscriptionsForUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSubscriptionSucceed() {
        //SETUP
        ResultSet resultSet = mock(ResultSet.class);
        Subscription subscription = mock(Subscription.class);
        User user = mock(User.class);
        int subscriptionId = 1;

        try {
            //TEST
            when(subscriptionDao.getSubscription(subscriptionId, user.getId())).thenReturn(resultSet);
            when(subscriptionDBMapper.getSingle(resultSet)).thenReturn(subscription);

            subscriptionController.getSubscription(subscriptionId, user);

            //VERIFY
            verify(subscriptionResponseMapper).mapToResponse(subscription);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSubscriptionFail() {
        ResultSet resultSet = mock(ResultSet.class);
        User user = mock(User.class);

        int subscriptionId = 1;

        try {
            when(subscriptionDao.getSubscription(subscriptionId, user.getId())).thenReturn(resultSet);
            when(subscriptionDBMapper.getSingle(resultSet)).thenReturn(null);

            subscriptionController.getSubscription(subscriptionId, user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void terminateSubscriptionSucceed() {
        //SETUP
        ResultSet resultSet = mock(ResultSet.class);
        Subscription subscription = mock(Subscription.class);
        User user = mock(User.class);

        try {
            //TEST
            when(subscriptionDao.getSubscription(subscription.getId(), user.getId())).thenReturn(resultSet);
            when(subscriptionDBMapper.getSingle(resultSet)).thenReturn(subscription);

            subscriptionController.terminateSubscription(subscription.getId(), user);

            //VERIFY
            verify(subscriptionDao, times(2)).getSubscription(subscription.getId(), user.getId());
            verify(subscriptionDBMapper, times(2)).getSingle(resultSet);
            verify(subscriptionDao).terminateSubscription(subscription.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void terminateSubscriptionFail() {
        //SETUP
        ResultSet resultSet = mock(ResultSet.class);
        Subscription subscription = mock(Subscription.class);
        User user = mock(User.class);

        try {
            //TEST
            when(subscriptionDao.getSubscription(subscription.getId(), user.getId())).thenReturn(resultSet);
            when(subscriptionDBMapper.getSingle(resultSet)).thenReturn(null);

            subscriptionController.terminateSubscription(subscription.getId(), user);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void upgradeSubscription() {
        int id = 1, userId = 1;
        String verdubbeling = "";

        try {
            subscriptionController.upgradeSubscription(id, userId, verdubbeling);
            verify(subscriptionDao).upgradeSubscription(id, userId, verdubbeling);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllSubscriptionsWithFilterSucceed() {
        ResultSet resultSet = mock(ResultSet.class);
        String filter = "asdsa";

        try {
            when(subscriptionDao.getAllSubscriptions(filter)).thenReturn(resultSet);
            when(subscriptionDBMapper.getList(resultSet)).thenReturn(subscriptionList);

            subscriptionController.getAllSubscriptions(filter);

            verify(subscriptionDao).getAllSubscriptions(filter);
            verify(subscriptionResponseMapper).mapToCompactList(subscriptionList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllSubscriptionsWithEmptyFilterSucceed() {
        ResultSet resultSet = mock(ResultSet.class);
        String filter = "";

        try {
            when(subscriptionDao.getAllSubscriptions()).thenReturn(resultSet);
            when(subscriptionDBMapper.getList(resultSet)).thenReturn(subscriptionList);

            subscriptionController.getAllSubscriptions(filter);

            verify(subscriptionDao).getAllSubscriptions();
            verify(subscriptionResponseMapper).mapToCompactList(subscriptionList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllSubscriptionsWithNoFilterSucceed() {
        ResultSet resultSet = mock(ResultSet.class);

        try {
            when(subscriptionDao.getAllSubscriptions()).thenReturn(resultSet);
            when(subscriptionDBMapper.getList(resultSet)).thenReturn(subscriptionList);

            subscriptionController.getAllSubscriptions(null);

            verify(subscriptionDao).getAllSubscriptions();
            verify(subscriptionResponseMapper).mapToCompactList(subscriptionList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllSubscriptionsWithFilterFail() {
        ResultSet resultSet = mock(ResultSet.class);
        String filter = "asdsa";

        try {
            when(subscriptionDao.getAllSubscriptions(filter)).thenReturn(resultSet);
            when(subscriptionDBMapper.getList(resultSet)).thenReturn(null);

            subscriptionController.getAllSubscriptions(filter);

            verify(subscriptionDao).getAllSubscriptions(filter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllSubscriptionsWithEmptyFilterFail() {
        ResultSet resultSet = mock(ResultSet.class);
        String filter = "";

        try {
            when(subscriptionDao.getAllSubscriptions()).thenReturn(resultSet);
            when(subscriptionDBMapper.getList(resultSet)).thenReturn(null);

            subscriptionController.getAllSubscriptions(filter);

            verify(subscriptionDao).getAllSubscriptions();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllSubscriptionsWithNoFilterFail() {
        ResultSet resultSet = mock(ResultSet.class);

        try {
            when(subscriptionDao.getAllSubscriptions()).thenReturn(resultSet);
            when(subscriptionDBMapper.getList(resultSet)).thenReturn(null);

            subscriptionController.getAllSubscriptions(null);

            verify(subscriptionDao).getAllSubscriptions();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkIfShareableSucceed() {
        //SETUP
        ResultSet resultSet = mock(ResultSet.class);

        Subscription subscription = new Subscription();
        subscription.setId(1);
        subscription.setDeelbaar(true);

        User user = mock(User.class);

        try {
            //TEST
            when(subscriptionDao.getSubscription(subscription.getId(), user.getId())).thenReturn(resultSet);
            when(subscriptionDBMapper.getSingle(resultSet)).thenReturn(subscription);

            Subscription isDeelbaar = subscriptionController.checkIfShareable(subscription.getId(), user.getId());
            assertNotNull(isDeelbaar);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkIfShareableFail() {
        //SETUP
        ResultSet resultSet = mock(ResultSet.class);

        Subscription subscription = new Subscription();
        subscription.setId(1);
        subscription.setDeelbaar(false);

        User user = mock(User.class);

        try {
            //TEST
            when(subscriptionDao.getSubscription(subscription.getId(), user.getId())).thenReturn(resultSet);
            when(subscriptionDBMapper.getSingle(resultSet)).thenReturn(subscription);

            Subscription isDeelbaar = subscriptionController.checkIfShareable(subscription.getId(), user.getId());
            assertNull(isDeelbaar);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

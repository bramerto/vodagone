package vodagone.controller;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import vodagone.dto.request.AddSubscriptionRequest;
import vodagone.mapper.DB.SubscriptionDBMapper;
import vodagone.mapper.SubscriptionResponseMapper;
import vodagone.store.SubscriptionDao;

import java.sql.SQLException;

public class SubscriptionControllerTest {

    @InjectMocks
    private SubscriptionController subscriptionController;
    @Mock
    SubscriptionDao subscriptionDao;
    @Mock
    SubscriptionResponseMapper subscriptionResponseMapper;
    @Mock
    SubscriptionDBMapper subscriptionDBMapper;

    @Test
    public void addSubscription() {
        AddSubscriptionRequest addSubscriptionRequest = new AddSubscriptionRequest();
        addSubscriptionRequest.setId(1);
        addSubscriptionRequest.setAanbieder("ziggo");
        addSubscriptionRequest.setDienst("TESTVEZEL");



        try {
            subscriptionController.addSubscription(addSubscriptionRequest);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSubscriptionsForUser() {
    }

    @Test
    public void getSubscription() {
    }

    @Test
    public void terminateSubscription() {
    }

    @Test
    public void upgradeSubscription() {
    }

    @Test
    public void getAllSubscriptions() {
    }

    @Test
    public void checkIfShareable() {
    }
}

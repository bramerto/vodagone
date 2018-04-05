package vodagone.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import vodagone.application.util.Validation;
import vodagone.controller.SubscriptionController;
import vodagone.controller.UserController;
import vodagone.domain.Subscription;
import vodagone.domain.User;
import vodagone.dto.request.UpgradeSubscriptionRequest;
import vodagone.dto.response.SubscriptionResponse;

import javax.ws.rs.core.Response;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionServiceTest {

    @InjectMocks
    private SubscriptionService subscriptionService;
    @Mock
    private SubscriptionController subscriptionController;
    @Mock
    private UserController userController;
    @Mock
    private Validation validation;

    @Test
    public void getSubscriptionsForUser() {

    }

    @Test
    public void addSubscription() {
    }

    @Test
    public void getSubscriptionSucceed() {
        //SETUP
        int id = 1;
        String token = "test";
        User user = mock(User.class);
        SubscriptionResponse subscriptionResponse = mock(SubscriptionResponse.class);

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(validation.checkId(id)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(user);
            when(subscriptionController.getSubscription(id, user)).thenReturn(subscriptionResponse);

            //TEST
            Response response = subscriptionService.getSubscription(id, token);

            //VERIFY
            assertEquals(Response.Status.OK, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSubscriptionTokenFail() {
        //SETUP
        int id = 1;
        String token = "test";

        when(validation.checkToken(token)).thenReturn(false);

        //TEST
        Response response = subscriptionService.getSubscription(id, token);

        //VERIFY
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test
    public void getSubscriptionIdFail() {
        //SETUP
        int id = 1;
        String token = "test";

        when(validation.checkToken(token)).thenReturn(true);
        when(validation.checkId(id)).thenReturn(false);

        //TEST
        Response response = subscriptionService.getSubscription(id, token);

        //VERIFY
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test
    public void getSubscriptionUnauthorized() {
        //SETUP
        int id = 1;
        String token = "test";

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(validation.checkId(id)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(null);

            //TEST
            Response response = subscriptionService.getSubscription(id, token);

            //VERIFY
            assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSubscriptionNotFound() {
        //SETUP
        int id = 1;
        String token = "test";
        User user = mock(User.class);

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(validation.checkId(id)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(user);
            when(subscriptionController.getSubscription(id, user)).thenReturn(null);

            //TEST
            Response response = subscriptionService.getSubscription(id, token);

            //VERIFY
            assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSubscriptionSqlFail() {
        //SETUP
        int id = 1;
        String token = "test";

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(validation.checkId(id)).thenReturn(true);
            //noinspection unchecked
            when(userController.AuthenticateUser(token)).thenThrow(SQLException.class);

            //TEST
            Response response = subscriptionService.getSubscription(id, token);

            //VERIFY
            assertEquals(Response.Status.INTERNAL_SERVER_ERROR, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void terminateSubscriptionSucceed() {
        //SETUP
        int id = 1;
        String token = "test";
        User user = mock(User.class);
        Subscription subscription = mock(Subscription.class);

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(validation.checkId(id)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(user);
            when(subscriptionController.terminateSubscription(id, user)).thenReturn(subscription);

            //TEST
            Response response = subscriptionService.terminateSubscription(id, token);

            //VERIFY
            assertEquals(Response.Status.OK, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void terminateSubscriptionTokenFail() {
        //SETUP
        int id = 1;
        String token = "test";

        when(validation.checkToken(token)).thenReturn(false);

        //TEST
        Response response = subscriptionService.terminateSubscription(id, token);

        //VERIFY
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test
    public void terminateSubscriptionIdFail() {
        //SETUP
        int id = 1;
        String token = "test";

        when(validation.checkToken(token)).thenReturn(true);
        when(validation.checkId(id)).thenReturn(false);

        //TEST
        Response response = subscriptionService.terminateSubscription(id, token);

        //VERIFY
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test
    public void terminateSubscriptionUnauthorized() {
        //SETUP
        int id = 1;
        String token = "test";

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(validation.checkId(id)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(null);

            //TEST
            Response response = subscriptionService.terminateSubscription(id, token);

            //VERIFY
            assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void terminateSubscriptionNotFound() {
        //SETUP
        int id = 1;
        String token = "test";
        User user = mock(User.class);

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(validation.checkId(id)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(user);
            when(subscriptionController.terminateSubscription(id, user)).thenReturn(null);

            //TEST
            Response response = subscriptionService.terminateSubscription(id, token);

            //VERIFY
            assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void terminateSubscriptionSqlFail() {
        //SETUP
        int id = 1;
        String token = "test";

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(validation.checkId(id)).thenReturn(true);
            //noinspection unchecked
            when(userController.AuthenticateUser(token)).thenThrow(SQLException.class);

            //TEST
            Response response = subscriptionService.terminateSubscription(id, token);

            //VERIFY
            assertEquals(Response.Status.INTERNAL_SERVER_ERROR, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void upgradeSubscriptionSucceed() {
        //SETUP
        int id = 1;
        String token = "test";
        User user = mock(User.class);
        UpgradeSubscriptionRequest upgradeSubscriptionRequest = mock(UpgradeSubscriptionRequest.class);

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(validation.checkId(id)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(user);

            //TEST
            Response response = subscriptionService.upgradeSubscription(id, token, upgradeSubscriptionRequest);

            //VERIFY
            verify(subscriptionController).upgradeSubscription(id, user.getId(), upgradeSubscriptionRequest.getVerdubbeling());
            assertEquals(Response.Status.CREATED, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void upgradeSubscriptionTokenFail() {
        //SETUP
        int id = 1;
        String token = "test";
        UpgradeSubscriptionRequest upgradeSubscriptionRequest = mock(UpgradeSubscriptionRequest.class);

        when(validation.checkToken(token)).thenReturn(false);

        //TEST
        Response response = subscriptionService.upgradeSubscription(id, token, upgradeSubscriptionRequest);

        //VERIFY
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test
    public void upgradeSubscriptionIdFail() {
        //SETUP
        int id = 1;
        String token = "test";
        UpgradeSubscriptionRequest upgradeSubscriptionRequest = mock(UpgradeSubscriptionRequest.class);

        when(validation.checkToken(token)).thenReturn(true);
        when(validation.checkId(id)).thenReturn(false);

        //TEST
        Response response = subscriptionService.upgradeSubscription(id, token, upgradeSubscriptionRequest);

        //VERIFY
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test
    public void upgradeSubscriptionUnauthorized() {
        //SETUP
        int id = 1;
        String token = "test";
        UpgradeSubscriptionRequest upgradeSubscriptionRequest = mock(UpgradeSubscriptionRequest.class);

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(validation.checkId(id)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(null);

            //TEST
            Response response = subscriptionService.upgradeSubscription(id, token, upgradeSubscriptionRequest);

            //VERIFY
            assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void upgradeSubscriptionSqlFail() {
        //SETUP
        int id = 1;
        String token = "test";
        UpgradeSubscriptionRequest upgradeSubscriptionRequest = mock(UpgradeSubscriptionRequest.class);

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(validation.checkId(id)).thenReturn(true);
            //noinspection unchecked
            when(userController.AuthenticateUser(token)).thenThrow(SQLException.class);

            //TEST
            Response response = subscriptionService.upgradeSubscription(id, token, upgradeSubscriptionRequest);

            //VERIFY
            assertEquals(Response.Status.INTERNAL_SERVER_ERROR, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllSubscriptions() {
    }
}

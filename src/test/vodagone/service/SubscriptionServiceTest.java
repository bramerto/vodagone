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
import vodagone.domain.compact.CompactSubscription;
import vodagone.dto.request.AddSubscriptionRequest;
import vodagone.dto.request.UpgradeSubscriptionRequest;
import vodagone.dto.response.SubscriptionResponse;
import vodagone.dto.response.SubscriptionsUserResponse;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

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
    @Mock
    private ArrayList<CompactSubscription> subscriptionList;

    @Test
    public void getSubscriptionsForUserSucceed() {
        //SETUP
        String token = "test";
        User user = mock(User.class);
        SubscriptionsUserResponse subscriptionsUserResponse = mock(SubscriptionsUserResponse.class);

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(user);
            when(subscriptionController.getSubscriptionsForUser(user)).thenReturn(subscriptionsUserResponse);

            //TEST
            Response response = subscriptionService.getSubscriptions(token);

            //VERIFY
            verify(subscriptionController).getSubscriptionsForUser(user);
            assertEquals(Response.Status.OK, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSubscriptionsForUserTokenFail() {
        //SETUP
        String token = "test";

        when(validation.checkToken(token)).thenReturn(false);

        //TEST
        Response response = subscriptionService.getSubscriptions(token);

        //VERIFY
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test
    public void getSubscriptionsForUserUnauthorized() {
        //SETUP
        String token = "test";

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(null);

            //TEST
            Response response = subscriptionService.getSubscriptions(token);

            //VERIFY
            assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSubscriptionsForUserNotFound() {
        //SETUP
        String token = "test";
        User user = mock(User.class);

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(user);
            when(subscriptionController.getSubscriptionsForUser(user)).thenReturn(null);

            //TEST
            Response response = subscriptionService.getSubscriptions(token);

            //VERIFY
            assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSubscriptionsForUserSqlFail() {
        //SETUP
        String token = "test";

        try {
            when(validation.checkToken(token)).thenReturn(true);
            //noinspection unchecked
            when(userController.AuthenticateUser(token)).thenThrow(SQLException.class);

            //TEST
            Response response = subscriptionService.getSubscriptions(token);

            //VERIFY
            assertEquals(Response.Status.INTERNAL_SERVER_ERROR, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addSubscriptionSucceed() {
        //SETUP
        String token = "test";
        User user = mock(User.class);
        AddSubscriptionRequest addSubscriptionRequest = mock(AddSubscriptionRequest.class);
        SubscriptionsUserResponse subscriptionsUserResponse = mock(SubscriptionsUserResponse.class);

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(user);
            when(subscriptionController.addSubscription(addSubscriptionRequest)).thenReturn(subscriptionsUserResponse);

            //TEST
            Response response = subscriptionService.addSubscription(token, addSubscriptionRequest);

            //VERIFY
            verify(subscriptionController).addSubscription(addSubscriptionRequest);
            assertEquals(Response.Status.CREATED, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addSubscriptionTokenFail() {
        //SETUP
        String token = "test";
        AddSubscriptionRequest addSubscriptionRequest = mock(AddSubscriptionRequest.class);

        when(validation.checkToken(token)).thenReturn(false);

        //TEST
        Response response = subscriptionService.addSubscription(token, addSubscriptionRequest);

        //VERIFY
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test
    public void addSubscriptionUnauthorized() {
        //SETUP
        String token = "test";
        AddSubscriptionRequest addSubscriptionRequest = mock(AddSubscriptionRequest.class);

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(null);

            //TEST
            Response response = subscriptionService.addSubscription(token, addSubscriptionRequest);

            //VERIFY
            assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addSubscriptionNotFound() {
        //SETUP
        String token = "test";
        User user = mock(User.class);
        AddSubscriptionRequest addSubscriptionRequest = mock(AddSubscriptionRequest.class);

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(user);
            when(subscriptionController.addSubscription(addSubscriptionRequest)).thenReturn(null);

            //TEST
            Response response = subscriptionService.addSubscription(token, addSubscriptionRequest);

            //VERIFY
            assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addSubscriptionSqlFail() {
        //SETUP
        String token = "test";
        User user = mock(User.class);
        AddSubscriptionRequest addSubscriptionRequest = mock(AddSubscriptionRequest.class);

        try {
            when(validation.checkToken(token)).thenReturn(true);
            //noinspection unchecked
            when(userController.AuthenticateUser(token)).thenThrow(SQLException.class);

            //TEST
            Response response = subscriptionService.addSubscription(token, addSubscriptionRequest);

            //VERIFY
            assertEquals(Response.Status.INTERNAL_SERVER_ERROR, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            verify(subscriptionController).getSubscription(id, user);
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
            verify(subscriptionController).terminateSubscription(id, user);
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
        //SETUP
        String token = "test";
        String filter = "test";
        User user = mock(User.class);

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(user);
            when(subscriptionController.getAllSubscriptions(filter)).thenReturn(subscriptionList);

            //TEST
            Response response = subscriptionService.getAllSubscriptions(token, filter);

            //VERIFY
            assertEquals(Response.Status.OK, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllSubscriptionsTokenFail() {
        //SETUP
        String token = "test";
        String filter = "test";
        when(validation.checkToken(token)).thenReturn(false);

        //TEST
        Response response = subscriptionService.getAllSubscriptions(token, filter);

        //VERIFY
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test
    public void getAllSubscriptionsUnauthorized() {
        //SETUP
        String token = "test";
        String filter = "test";

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(null);

            //TEST
            Response response = subscriptionService.getAllSubscriptions(token, filter);

            //VERIFY
            assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllSubscriptionsNotFound() {
        //SETUP
        String token = "test";
        String filter = "test";
        User user = mock(User.class);

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(user);
            when(subscriptionController.getAllSubscriptions(filter)).thenReturn(null);

            //TEST
            Response response = subscriptionService.getAllSubscriptions(token, filter);

            //VERIFY
            assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllSubscriptionsSqlFail() {
        //SETUP
        String token = "test";
        String filter = "test";

        try {
            when(validation.checkToken(token)).thenReturn(true);
            //noinspection unchecked
            when(userController.AuthenticateUser(token)).thenThrow(SQLException.class);

            //TEST
            Response response = subscriptionService.getAllSubscriptions(token, filter);

            //VERIFY
            assertEquals(Response.Status.INTERNAL_SERVER_ERROR, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

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
import vodagone.domain.compact.CompactUser;
import vodagone.dto.request.ShareSubscriptionRequest;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubscriberServiceTest {

    @InjectMocks
    private SubscriberService subscriberService;
    @Mock
    private UserController userController;
    @Mock
    private SubscriptionController subscriptionController;
    @Mock
    private Validation validation;

    @Test
    public void getAllSubscribersSucceed() {
        //SETUP
        String token = "test";
        User user = mock(User.class);
        ArrayList<CompactUser> userList = new ArrayList<>();

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(user);
            when(userController.getAllUsers()).thenReturn(userList);

            //TEST
            Response response = subscriberService.getAllSubscribers(token);

            //VERIFY
            assertEquals(Response.Status.OK, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllSubscribersTokenFail() {
        //SETUP
        String token = "test";

        when(validation.checkToken(token)).thenReturn(false);

        //TEST
        Response response = subscriberService.getAllSubscribers(token);

        //VERIFY
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test
    public void getAllSubscribersUnauthorized() {
        //SETUP
        String token = "test";

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(null);

            //TEST
            Response response = subscriberService.getAllSubscribers(token);

            //VERIFY
            assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllSubscribersNoSubscribers() {
        //SETUP
        String token = "test";
        User user = mock(User.class);

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(user);
            when(userController.getAllUsers()).thenReturn(null);

            //TEST
            Response response = subscriberService.getAllSubscribers(token);

            //VERIFY
            assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllSubscribersNoSubscribersSQLFail() {
        //SETUP
        String token = "test";

        try {
            when(validation.checkToken(token)).thenReturn(true);
            //noinspection unchecked
            when(userController.AuthenticateUser(token)).thenThrow(SQLException.class);

            //TEST
            Response response = subscriberService.getAllSubscribers(token);

            //VERIFY
            assertEquals(Response.Status.INTERNAL_SERVER_ERROR, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shareSubscriptionSucceed() {
        //SETUP
        ShareSubscriptionRequest shareSubscriptionRequest = mock(ShareSubscriptionRequest.class);
        int id = 1;
        String token = "test";
        User user = mock(User.class);
        Subscription subscription = mock(Subscription.class);

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(validation.checkId(id)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(user);
            when(subscriptionController.checkIfShareable(shareSubscriptionRequest.getId(), user.getId()))
                    .thenReturn(subscription);

            //TEST
            Response response = subscriberService.shareSubscription(id, token, shareSubscriptionRequest);

            //VERIFY
            verify(userController).shareSubscription(subscription.getId(), token);
            assertEquals(Response.Status.CREATED, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shareSubscriptionTokenFail() {
        //SETUP
        ShareSubscriptionRequest shareSubscriptionRequest = mock(ShareSubscriptionRequest.class);
        int id = 1;
        String token = "test";

        when(validation.checkToken(token)).thenReturn(false);

        Response response = subscriberService.shareSubscription(id, token, shareSubscriptionRequest);

        //VERIFY
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test
    public void shareSubscriptionIdFail() {
        //SETUP
        ShareSubscriptionRequest shareSubscriptionRequest = mock(ShareSubscriptionRequest.class);
        int id = 1;
        String token = "test";

        when(validation.checkToken(token)).thenReturn(true);
        when(validation.checkId(id)).thenReturn(false);

        //TEST
        Response response = subscriberService.shareSubscription(id, token, shareSubscriptionRequest);

        //VERIFY
        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test
    public void shareSubscriptionUnauthorized() {
        //SETUP
        ShareSubscriptionRequest shareSubscriptionRequest = mock(ShareSubscriptionRequest.class);
        int id = 1;
        String token = "test";

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(validation.checkId(id)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(null);

            //TEST
            Response response = subscriberService.shareSubscription(id, token, shareSubscriptionRequest);

            //VERIFY
            assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shareSubscriptionNotfound() {
        //SETUP
        ShareSubscriptionRequest shareSubscriptionRequest = mock(ShareSubscriptionRequest.class);
        int id = 1;
        String token = "test";
        User user = mock(User.class);

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(validation.checkId(id)).thenReturn(true);
            when(userController.AuthenticateUser(token)).thenReturn(user);
            when(subscriptionController.checkIfShareable(shareSubscriptionRequest.getId(), user.getId()))
                    .thenReturn(null);

            //TEST
            Response response = subscriberService.shareSubscription(id, token, shareSubscriptionRequest);

            //VERIFY
            assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shareSubscriptionSqlFail() {
        //SETUP
        ShareSubscriptionRequest shareSubscriptionRequest = mock(ShareSubscriptionRequest.class);
        int id = 1;
        String token = "test";

        try {
            when(validation.checkToken(token)).thenReturn(true);
            when(validation.checkId(id)).thenReturn(true);
            //noinspection unchecked
            when(userController.AuthenticateUser(token)).thenThrow(SQLException.class);

            //TEST
            Response response = subscriberService.shareSubscription(id, token, shareSubscriptionRequest);

            //VERIFY
            assertEquals(Response.Status.INTERNAL_SERVER_ERROR, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

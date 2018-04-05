package vodagone.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import vodagone.controller.UserController;
import vodagone.domain.User;
import vodagone.dto.request.LoginRequest;
import vodagone.dto.response.LoginResponse;


import javax.ws.rs.core.Response;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;
    @Mock
    private UserController userController;

    @Test
    public void loginSucceed() {
        LoginRequest loginRequest = mock(LoginRequest.class);
        LoginResponse loginResponse = mock(LoginResponse.class);
        User user = mock(User.class);

        try {
            when(userController.AuthenticateUser(loginRequest.getUser(), loginRequest.getPassword())).thenReturn(user);
            when(userController.getLogin(user)).thenReturn(loginResponse);

            Response response = loginService.login(loginRequest);

            verify(userController).AuthenticateUser(loginRequest.getUser(), loginRequest.getPassword());
            verify(userController).getLogin(user);
            assertEquals(Response.Status.CREATED, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loginUnAuthorized() {
        LoginRequest loginRequest = mock(LoginRequest.class);

        try {
            when(userController.AuthenticateUser(loginRequest.getUser(), loginRequest.getPassword())).thenReturn(null);

            Response response = loginService.login(loginRequest);

            verify(userController).AuthenticateUser(loginRequest.getUser(), loginRequest.getPassword());
            assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loginFail() {
        LoginRequest loginRequest = mock(LoginRequest.class);

        try {
            //noinspection unchecked
            when(userController.AuthenticateUser(loginRequest.getUser(), loginRequest.getPassword()))
                    .thenThrow(SQLException.class);

            Response response = loginService.login(loginRequest);

            verify(userController).AuthenticateUser(loginRequest.getUser(), loginRequest.getPassword());
            assertEquals(Response.Status.INTERNAL_SERVER_ERROR, response.getStatusInfo());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

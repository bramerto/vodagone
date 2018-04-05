package vodagone.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import vodagone.domain.User;
import vodagone.mapper.DB.UserDBMapper;
import vodagone.mapper.UserResponseMapper;
import vodagone.store.SubscriptionDao;
import vodagone.store.UserDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;
    @Mock
    private UserDao userDao;
    @Mock
    private UserDBMapper userDBMapper;
    @Mock
    private UserResponseMapper userResponseMapper;
    @Mock
    private SubscriptionDao subscriptionDao;
    @Mock
    private ArrayList<User> userList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void authenticateLoginUser() {
        try {
            //SETUP TEST
            User user = new User();
            user.setUser("John");
            user.setPassword("password123");

            ResultSet resultSet = mock(ResultSet.class);

            when(userDao.getUserByLogin(user.getUser(), user.getPassword())).thenReturn(resultSet);

            //TEST
            userController.AuthenticateUser(user.getUser(), user.getPassword());

            //VERIFY
            verify(userDao).getUserByLogin(user.getUser(), user.getPassword());
            verify(userDBMapper).getSingle(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void authenticateUser() {
        try {
            //SETUP TEST
            String token = "1234";

            ResultSet resultSet = mock(ResultSet.class);

            when(userDao.getUserByToken(token)).thenReturn(resultSet);

            //TEST
            userController.AuthenticateUser(token);

            //VERIFY
            verify(userDao).getUserByToken(token);
            verify(userDBMapper).getSingle(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getAllUsersSucceed() {
        try {
            //SETUP
            ResultSet resultSet = mock(ResultSet.class);

            //TEST
            when(userDao.getAllUsers()).thenReturn(resultSet);
            when(userDBMapper.getList(resultSet)).thenReturn(userList);

            userController.getAllUsers();

            //VERIFY
            verify(userDao).getAllUsers();
            verify(userDBMapper).getList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllUsersFail() {
        try {
            ResultSet resultSet = mock(ResultSet.class);

            //TEST
            when(userDao.getAllUsers()).thenReturn(resultSet);
            when(userDBMapper.getList(resultSet)).thenReturn(null);

            userController.getAllUsers();

            //VERIFY
            verify(userDao).getAllUsers();
            verify(userDBMapper).getList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shareSubscription() {
        try {
            //SETUP
            User user = new User();
            user.setId(1);

            ResultSet resultSet = mock(ResultSet.class);
            String token = "1234";
            int subscriptionid = 1;

            //TEST
            when(userDao.getUserByToken(token)).thenReturn(resultSet);
            when(userDBMapper.getSingle(resultSet)).thenReturn(user);

            userController.shareSubscription(subscriptionid, token);

            //VERIFY
            verify(userDao).getUserByToken(token);
            verify(subscriptionDao).shareSubscription(user.getId(), subscriptionid);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getLogin() {
        //SETUP
        User user = new User();
        //TEST
        userController.getLogin(user);
        //VERIFY
        verify(userResponseMapper).mapToResponse(user);
    }
}

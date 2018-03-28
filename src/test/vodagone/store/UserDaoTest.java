package vodagone.store;

import org.junit.Before;
import org.junit.Test;
import vodagone.domain.User;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserDaoTest {

    private User user = new User();
    private String address;

    @Before
    public void setupTest() {

        this.user.setUser("JohnDoe");
        this.user.setPassword("password123");
        this.user.setName("John Doe");
        this.user.setEmail("Johndoe@test.com");
        this.user.setUser("JohnDoe");
        this.user.setToken("1234-1234-1234");
        this.address = "TestLane 3, 3193HA, United Kingdom";
    }

    @Test
    public void getUserByToken() {
        //SETUP TEST
        String sql = "SELECT * FROM user WHERE token = ?";

        try {
            //PREPARE MOCK
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);
            ResultSet resultSet = mock(ResultSet.class);

            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);

            UserDao userDao = new UserDao(connection);
            userDao.getUserByToken(this.user.getToken());

            //TEST
            verify(connection).prepareStatement(sql);
            verify(preparedStatement).setString(1, this.user.getToken());
            verify(preparedStatement).executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertTrue(true);
    }

    @Test
    public void getUserByLogin() {
        //SETUP TEST
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";

        try {
            //PREPARE MOCK
            Connection connection = mock(Connection.class);
            PreparedStatement preparedStatement = mock(PreparedStatement.class);
            ResultSet resultSet = mock(ResultSet.class);

            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);

            UserDao userDao = new UserDao(connection);
            userDao.getUserByLogin(this.user.getUser(), this.user.getPassword());

            //TEST
            verify(connection).prepareStatement(sql);
            verify(preparedStatement).setString(1, this.user.getUser());
            verify(preparedStatement).setString(2, this.user.getPassword());
            verify(preparedStatement).executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertTrue(true);
    }

    @Test
    public void getUserById() {
    }

    @Test
    public void getAllUsers() {
    }
}
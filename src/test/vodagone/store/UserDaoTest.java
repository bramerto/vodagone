package vodagone.store;

import org.junit.Ignore;
import org.junit.Test;
import vodagone.domain.User;
import vodagone.mapper.DB.UserDBMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Useless unit tests, because all the functions in this method are out of my range to test and most likely
 * already tested by the creator of the following classes:
 * @Class: Connection
 * @Class: PreparedStatement
 * @Class: ResultSet
 *
 * if any future features are implemented or implemented, you can add or change them here
 */

public class UserDaoTest {

    @Test
    public void getUserByToken() {
        //SETUP
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        User user = new User();
        user.setUser("John");
        user.setPassword("password123");

        //MOCKS
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);


        try {
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);

            //TEST
            UserDao userDao = new UserDao(connection);
            userDao.getUserByLogin(user.getUser(), user.getPassword());

            verify(preparedStatement).setString(1, user.getUser());
            verify(preparedStatement).setString(2, user.getPassword());
            verify(preparedStatement).executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUserByLogin() {
        //SETUP
        String sql = "SELECT * FROM user WHERE token = ?";

        User user = new User();
        user.setToken("1234");

        //MOCKS
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        try {
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);

            //TEST
            UserDao userDao = new UserDao(connection);
            userDao.getUserByToken(user.getToken());

            verify(preparedStatement).setString(1, user.getToken());
            verify(preparedStatement).executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllUsers() {
        //SETUP
        String sql = "SELECT * FROM user";

        //MOCKS
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        try {
            when(connection.prepareStatement(sql)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);

            //TEST
            UserDao userDao = new UserDao(connection);
            userDao.getAllUsers();

            verify(preparedStatement).executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package vodagone.store;

import vodagone.domain.User;
import vodagone.mapper.UserDBMapper;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {

    private Connection connection;

    @Inject
    private UserDBMapper userDBMapper; //TODO: DI does not work

    @Inject
    public UserDao(@Named("connection") Connection connection) {
        this.connection = connection;
    }

    public User getUserByToken(String token) throws SQLException {
        String sql = "SELECT * FROM user WHERE token = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, token);
        ResultSet resultSet = preparedStatement.executeQuery();

        return userDBMapper.getSingleUser(resultSet);
    }

    public User getUserByLogin(String user, String password) throws SQLException {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();

        return userDBMapper.getSingleUser(resultSet);
    }

    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM abbonementen WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        return userDBMapper.getSingleUser(resultSet);
    }

    public ArrayList<User> getAllUsers() throws SQLException {

        String sql = "SELECT * FROM user";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        return userDBMapper.getUsersList(resultSet);
    }
}

package vodagone.store;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao implements IUserDao {

    private Connection connection;

    @Inject
    public UserDao(@Named("connection") Connection connection) {
        this.connection = connection;
    }

    public ResultSet getUserByToken(String token) throws SQLException {
        String sql = "SELECT * FROM user WHERE token = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, token);
        return preparedStatement.executeQuery();
    }

    public ResultSet getUserByLogin(String user, String password) throws SQLException {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user);
        preparedStatement.setString(2, password);
        return preparedStatement.executeQuery();
    }

    public ResultSet getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM user WHERE id = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        return preparedStatement.executeQuery();
    }

    public ResultSet getAll() throws SQLException {

        String sql = "SELECT * FROM user";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        return preparedStatement.executeQuery();
    }
}

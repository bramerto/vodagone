package vodagone.store;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IUserDao {
    ResultSet getUserByToken(String token) throws SQLException;
    ResultSet getUserByLogin(String user, String password) throws SQLException;
    ResultSet getAllUsers() throws SQLException;
}

package vodagone.mapper;

import vodagone.domain.Address;
import vodagone.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDBMapper {
    public ArrayList<User> getUsersList(ResultSet rs) throws SQLException {
        if (!rs.next()) return null;

        rs.beforeFirst();
        ArrayList<User> users = new ArrayList<>();

        while (rs.next()) {
            users.add(setUser(rs));
        }

        return users;
    }

    public User getSingleUser(ResultSet rs) throws SQLException {

        if (!rs.next()) return null;
        return setUser(rs);
    }

    private User setUser(ResultSet checkedRS) throws SQLException {
        User user = new User();
        Address address = new Address();

        user.setId(checkedRS.getInt("id"));
        user.setUser(checkedRS.getString("username"));
        user.setPassword(checkedRS.getString("password"));
        user.setName(checkedRS.getString("name"));
        user.setEmail(checkedRS.getString("email"));

        address.resolveAddress(checkedRS.getString("address"));
        user.setAddress(address);

        user.setToken(checkedRS.getString("token"));

        return user;
    }
}

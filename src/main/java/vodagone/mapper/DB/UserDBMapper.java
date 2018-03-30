package vodagone.mapper.DB;

import vodagone.domain.Address;
import vodagone.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDBMapper implements IDBMapper {
    public ArrayList<User> getList(ResultSet rs) throws SQLException {
        if (!rs.next()) return null;

        rs.beforeFirst();
        ArrayList<User> users = new ArrayList<>();

        while (rs.next()) {
            users.add(map(rs));
        }

        return users;
    }

    public User getSingle(ResultSet rs) throws SQLException {
        if (!rs.next()) return null;
        return map(rs);
    }

    public User map(ResultSet checkedRS) throws SQLException {
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

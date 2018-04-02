package vodagone.mapper.DB;

import org.junit.Test;
import vodagone.domain.Address;
import vodagone.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserDBMapperTest {

    @Test
    public void getListSucceed() {
        try {
            ResultSet resultSet = mock(ResultSet.class);
            mock(Address.class);
            when(resultSet.getString("address")).thenReturn("Testdrive 1, 1234AB, Testcity");
            when(resultSet.next()).thenReturn(true, true, false);

            UserDBMapper userDBMapper = new UserDBMapper();
            ArrayList<User> users = userDBMapper.getList(resultSet);

            assertNotNull(users);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getListFail() {
        try {
            ResultSet resultSet = mock(ResultSet.class);
            when(resultSet.next()).thenReturn(false);

            UserDBMapper userDBMapper = new UserDBMapper();
            ArrayList<User> users = userDBMapper.getList(resultSet);

            assertNull(users);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSingleSucceed() {
        try {

            ResultSet resultSet = mock(ResultSet.class);
            mock(Address.class);
            when(resultSet.getString("address")).thenReturn("Testdrive 1, 1234AB, Testcity");
            when(resultSet.next()).thenReturn(true);

            UserDBMapper userDBMapper = new UserDBMapper();
            User user = userDBMapper.getSingle(resultSet);

            assertNotNull(user);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSingleFail() {
        try {

            ResultSet resultSet = mock(ResultSet.class);
            when(resultSet.next()).thenReturn(false);

            UserDBMapper userDBMapper = new UserDBMapper();
            User user = userDBMapper.getSingle(resultSet);

            assertNull(user);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

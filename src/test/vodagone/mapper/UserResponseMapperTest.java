package vodagone.mapper;

import org.junit.Test;
import vodagone.domain.User;
import vodagone.domain.compact.CompactUser;
import vodagone.dto.response.LoginResponse;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class UserResponseMapperTest {

    @Test
    public void mapToResponseSucceed() {
        User user = new User();
        UserResponseMapper userResponseMapper = new UserResponseMapper();

        user.setToken("testtoken");
        user.setName("testuser");

        LoginResponse loginResponse = userResponseMapper.mapToResponse(user);
        assertEquals(loginResponse.getToken(), user.getToken());
        assertEquals(loginResponse.getUser(), user.getName());
    }

    @Test
    public void mapToCompactListSucceed() {
        UserResponseMapper userResponseMapper = new UserResponseMapper();

        User user1 = new User();
        User user2 = new User();

        user1.setId(1);
        user1.setName("test1");
        user1.setEmail("John@test.com");

        user2.setId(2);
        user2.setName("test2");
        user2.setEmail("Doe@test.com");

        ArrayList<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        ArrayList<CompactUser> compactUsers = userResponseMapper.mapToCompactList(users);

        CompactUser actualUser1 = compactUsers.get(0);
        CompactUser actualUser2 = compactUsers.get(1);

        assertEquals(actualUser1.getId(), user1.getId());
        assertEquals(actualUser1.getName(), user1.getName());
        assertEquals(actualUser1.getEmail(), user1.getEmail());

        assertEquals(actualUser2.getId(), user2.getId());
        assertEquals(actualUser2.getName(), user2.getName());
        assertEquals(actualUser2.getEmail(), user2.getEmail());
    }
}

package vodagone.mapper;

import vodagone.domain.User;
import vodagone.domain.compact.CompactUser;
import vodagone.dto.response.LoginResponse;

import java.util.ArrayList;

public class UserResponseMapper {

    public LoginResponse mapToResponse(User user) {
        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setToken(user.getToken());
        loginResponse.setUser(user.getName());

        return loginResponse;
    }

    public ArrayList<CompactUser> mapToCompactList(ArrayList<User> users) {
        ArrayList<CompactUser> compactUsers = new ArrayList<>();

        for (User user : users) {
            CompactUser compactUser = new CompactUser();

            compactUser.setId(user.getId());
            compactUser.setName(user.getName());
            compactUser.setEmail(user.getEmail());

            compactUsers.add(compactUser);
        }

        return compactUsers;
    }
}

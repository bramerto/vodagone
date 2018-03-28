package vodagone.mapper;

import vodagone.domain.User;
import vodagone.dto.CompactUser;
import vodagone.dto.response.LoginResponse;

import java.util.ArrayList;

public class UserResponseMapper {

    public LoginResponse mapSingleUserToLoginResponse(User user) {
        LoginResponse loginResponse = new LoginResponse();

        loginResponse.setToken(user.getToken());
        loginResponse.setUser(user.getName());

        return loginResponse;
    }

    public ArrayList<CompactUser> mapUserListToCompactList(ArrayList<User> users) {
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

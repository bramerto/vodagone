package vodagone.controller;

import vodagone.domain.User;
import vodagone.domain.compact.CompactUser;
import vodagone.dto.response.LoginResponse;
import vodagone.mapper.DB.UserDBMapper;
import vodagone.mapper.UserResponseMapper;
import vodagone.store.IUserDao;
import vodagone.store.SubscriptionDao;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserController {

    @Inject
    private IUserDao userDao;
    @Inject
    private UserDBMapper userDBMapper;
    @Inject
    private UserResponseMapper userResponseMapper;
    @Inject
    private SubscriptionDao subscriptionDao;

    public User AuthenticateUser(String user, String password) throws SQLException {
        ResultSet resultSet = userDao.getUserByLogin(user, password);
        return userDBMapper.getSingle(resultSet);
    }

    public User AuthenticateUser(String token) throws SQLException {
        ResultSet resultSet = userDao.getUserByToken(token);
        return userDBMapper.getSingle(resultSet);
    }

    public LoginResponse getLogin(User user) {
        return userResponseMapper.mapToResponse(user);
    }

    public ArrayList<CompactUser> getAllUsers() throws SQLException {
        ResultSet resultSet = userDao.getAllUsers();
        ArrayList<User> users = userDBMapper.getList(resultSet);

        if (users == null) {
            return null;
        }

        return userResponseMapper.mapToCompactList(users);
    }

    public void shareSubscription(int subscriptionid, String token) throws SQLException {
        ResultSet resultSet = userDao.getUserByToken(token);
        User sharedToUser = userDBMapper.getSingle(resultSet);

        subscriptionDao.shareSubscription(sharedToUser.getId(), subscriptionid);
    }
}

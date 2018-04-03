package vodagone.controller;

import vodagone.domain.User;
import vodagone.domain.compact.CompactUser;
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

    public User AuthenticateUser(String token) throws SQLException {
        ResultSet RSuser = userDao.getUserByToken(token);
        return userDBMapper.getSingle(RSuser);
    }

    public ArrayList<CompactUser> getAllUsers() throws SQLException {
        ResultSet RSusers = userDao.getAll();

        ArrayList<User> users = userDBMapper.getList(RSusers);

        if (users == null) {
            return null;
        }

        return userResponseMapper.mapToCompactList(users);
    }

    public boolean shareSubscription(int subscriptionid, String token) throws SQLException {
        ResultSet RSsharedToUser = userDao.getUserByToken(token);
        User sharedToUser = userDBMapper.getSingle(RSsharedToUser);

        subscriptionDao.shareSubscription(sharedToUser.getId(), subscriptionid);
        return true;
    }
}

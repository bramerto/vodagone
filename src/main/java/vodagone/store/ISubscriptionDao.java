package vodagone.store;

import vodagone.domain.Subscription;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ISubscriptionDao {
    ResultSet getAll() throws SQLException;
    ResultSet getAll(String filter) throws SQLException;
    ResultSet getAllSubscriptionsByUser(int userId) throws SQLException;
    ResultSet getSubscription(int id, int authenticatedUserId) throws SQLException;
    void shareSubscription(int sharedToUserId, int toBeSharedSubscriptionId) throws SQLException;
    void addSubscription(Subscription subscription) throws SQLException;
    void upgradeSubscription(int id, int userId, String upgrade) throws SQLException;
    void terminateSubscription(int id) throws SQLException;
}

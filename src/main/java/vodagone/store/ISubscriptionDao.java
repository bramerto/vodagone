package vodagone.store;

import vodagone.domain.Abonnement;
import vodagone.domain.Subscription;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ISubscriptionDao {
    ResultSet getAllSubscriptions() throws SQLException;
    ResultSet getAllSubscriptions(String filter) throws SQLException;
    ResultSet getAllSubscriptionsByUser(int userId) throws SQLException;
    ResultSet getSubscription(int id, int authenticatedUserId) throws SQLException;
    ResultSet getSubscription(int id) throws SQLException;
    void shareSubscription(int sharedToUserId, Subscription toBeSharedSubscription) throws SQLException;
    void addSubscription(Abonnement subscription, int userid) throws SQLException;
    void upgradeSubscription(int id, int userId, String upgrade) throws SQLException;
    void terminateSubscription(int id) throws SQLException;
}

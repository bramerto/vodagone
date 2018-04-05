package vodagone.controller;

import vodagone.domain.Subscription;
import vodagone.domain.User;
import vodagone.domain.compact.CompactSubscription;
import vodagone.dto.request.AddSubscriptionRequest;
import vodagone.dto.response.SubscriptionResponse;
import vodagone.dto.response.SubscriptionsUserResponse;
import vodagone.mapper.DB.SubscriptionDBMapper;
import vodagone.mapper.SubscriptionResponseMapper;
import vodagone.store.ISubscriptionDao;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubscriptionController {

    @Inject
    private SubscriptionResponseMapper subscriptionResponseMapper;
    @Inject
    private ISubscriptionDao subscriptionDao;
    @Inject
    private SubscriptionDBMapper subscriptionDBMapper;

    public SubscriptionsUserResponse addSubscription(AddSubscriptionRequest request) throws SQLException {
        Subscription subscription = subscriptionResponseMapper.mapToSubscription(request);
        subscriptionDao.addSubscription(subscription);

        ResultSet RSsubscriptions = subscriptionDao.getAllSubscriptions();
        ArrayList<Subscription> subscriptions = subscriptionDBMapper.getList(RSsubscriptions);

        if (subscriptions == null) {
            subscriptions = new ArrayList<>();
        }

        SubscriptionsUserResponse response = subscriptionResponseMapper.mapToCompactResponse(subscriptions);
        response.addEntryToList(request.toCompact());

        return response;
    }

    public SubscriptionsUserResponse getSubscriptionsForUser(User user) throws SQLException {
        ResultSet RSsubscriptions = subscriptionDao.getAllSubscriptionsByUser(user.getId());
        ArrayList<Subscription> subscriptions = subscriptionDBMapper.getList(RSsubscriptions);

        if (subscriptions == null) {
            return null;
        }

        return subscriptionResponseMapper.mapToCompactResponse(subscriptions);
    }

    public SubscriptionResponse getSubscription(int id, User user) throws SQLException {
        Subscription subscription = getSubscription(id, user.getId());

        if (subscription == null) {
            return null;
        }

        return subscriptionResponseMapper.mapToResponse(subscription);
    }

    public Subscription terminateSubscription(int id, User user) throws SQLException {
        Subscription subscription = getSubscription(id, user.getId());

        if (subscription == null) {
            return null;
        }

        subscriptionDao.terminateSubscription(subscription.getId());

        return getSubscription(id, user.getId());
    }

    public void upgradeSubscription(int id, int userid, String verdubbling) throws SQLException {
        subscriptionDao.upgradeSubscription(id, userid, verdubbling);
    }

    public ArrayList<CompactSubscription> getAllSubscriptions(String filter) throws SQLException {
        ResultSet RSsubscriptions = (filter != null && !filter.isEmpty()) ?
                subscriptionDao.getAllSubscriptions(filter) :
                subscriptionDao.getAllSubscriptions();
        ArrayList<Subscription> subscriptions = subscriptionDBMapper.getList(RSsubscriptions);

        if (subscriptions == null) {
            return null;
        }

        return subscriptionResponseMapper.mapToCompactList(subscriptions);
    }

    public Subscription checkIfShareable(int subscriptionId, int userId) throws SQLException {
        Subscription subscription = getSubscription(subscriptionId, userId);
        return (subscription.isDeelbaar()) ?
                subscription :
                null;
    }

    private Subscription getSubscription(int id, int userId) throws SQLException {
        ResultSet RSsubscription = subscriptionDao.getSubscription(id, userId);
        return subscriptionDBMapper.getSingle(RSsubscription);
    }
}

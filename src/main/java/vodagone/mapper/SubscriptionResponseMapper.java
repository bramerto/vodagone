package vodagone.mapper;

import vodagone.domain.Subscription;
import vodagone.domain.compact.CompactSubscription;
import vodagone.dto.request.AddSubscriptionRequest;
import vodagone.dto.response.SubscriptionResponse;
import vodagone.dto.response.SubscriptionsForUserResponse;

import java.util.ArrayList;

public class SubscriptionResponseMapper {
    public SubscriptionResponse mapToResponse(Subscription subscription) {
        SubscriptionResponse subscriptionResponse = new SubscriptionResponse();

        SubscriptionResponse response = new SubscriptionResponse();
        response.setId(subscription.getId());
        response.setAanbieder(subscription.getAanbieder());
        response.setDienst(subscription.getDienst());
        response.setPrijs(subscription.getPrijs());
        response.setStartDatum(subscription.getStartDatum());
        response.setVerdubbeling(subscription.getVerdubbeling());
        response.setDeelbaar(subscription.isDeelbaar());
        response.setStatus(subscription.getStatus());

        return subscriptionResponse;
    }

    public Subscription mapToSubscription(AddSubscriptionRequest request) {
        Subscription subscription = new Subscription();
        subscription.setId(request.getId());
        subscription.setAanbieder(request.getAanbieder());
        subscription.setDienst(request.getDienst());

        return subscription;
    }

    public SubscriptionsForUserResponse mapToCompactResponse(ArrayList<Subscription> subscriptions) {
        ArrayList<CompactSubscription> compactSubscriptions = new ArrayList<>();
        double totalPrice = 0;

        for (Subscription sub : subscriptions) {
            CompactSubscription compactSubscription = new CompactSubscription();

            compactSubscription.setAanbieder(sub.getAanbieder());
            compactSubscription.setDienst(sub.getDienst());
            compactSubscription.setId(sub.getId());

            compactSubscriptions.add(compactSubscription);
            totalPrice += sub.getPricetag();
        }

        SubscriptionsForUserResponse subscriptionsForUserResponse = new SubscriptionsForUserResponse();
        subscriptionsForUserResponse.setAbonnementen(compactSubscriptions);
        subscriptionsForUserResponse.setTotalPrice(totalPrice);

        return subscriptionsForUserResponse;
    }

    public ArrayList<CompactSubscription> mapToCompactList(ArrayList<Subscription> subscriptions) {
        ArrayList<CompactSubscription> compactSubscriptions = new ArrayList<>();

        for (Subscription subscription : subscriptions) {
            CompactSubscription compactSubscription = new CompactSubscription();

            compactSubscription.setId(subscription.getId());
            compactSubscription.setAanbieder(subscription.getAanbieder());
            compactSubscription.setDienst(subscription.getDienst());

            compactSubscriptions.add(compactSubscription);
        }

        return compactSubscriptions;
    }
}

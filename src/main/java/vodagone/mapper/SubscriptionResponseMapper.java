package vodagone.mapper;

import vodagone.domain.Subscription;
import vodagone.domain.compact.CompactSubscription;
import vodagone.dto.request.AddSubscriptionRequest;
import vodagone.dto.response.SubscriptionResponse;
import vodagone.dto.response.SubscriptionsUserResponse;

import java.util.ArrayList;

public class SubscriptionResponseMapper {
    public SubscriptionResponse mapToResponse(Subscription subscription) {

        SubscriptionResponse subscriptionResponse = new SubscriptionResponse();

        subscriptionResponse.setId(subscription.getId());
        subscriptionResponse.setAanbieder(subscription.getAanbieder());
        subscriptionResponse.setDienst(subscription.getDienst());
        subscriptionResponse.setPrijs(subscription.getPrijs());
        subscriptionResponse.setStartDatum(subscription.getStartDatum().toString());
        subscriptionResponse.setVerdubbeling(subscription.getVerdubbeling());
        subscriptionResponse.setDeelbaar(subscription.isDeelbaar());
        subscriptionResponse.setStatus(subscription.getStatus());

        return subscriptionResponse;
    }

    public Subscription mapToSubscription(AddSubscriptionRequest request) {
        Subscription subscription = new Subscription();

        subscription.setId(request.getId());
        subscription.setAanbieder(request.getAanbieder());
        subscription.setDienst(request.getDienst());

        return subscription;
    }

    public SubscriptionsUserResponse mapToCompactResponse(ArrayList<Subscription> subscriptions) {
        ArrayList<CompactSubscription> compactSubscriptions = new ArrayList<>();
        double totalPrice = 0;

        for (Subscription sub : subscriptions) {
            CompactSubscription compactSubscription = new CompactSubscription();

            compactSubscription.setId(sub.getId());
            compactSubscription.setAanbieder(sub.getAanbieder());
            compactSubscription.setDienst(sub.getDienst());

            compactSubscriptions.add(compactSubscription);
            totalPrice += sub.getPricetag();
        }

        SubscriptionsUserResponse subscriptionsUserResponse = new SubscriptionsUserResponse();
        subscriptionsUserResponse.setAbonnementen(compactSubscriptions);
        subscriptionsUserResponse.setTotalPrice(totalPrice);

        return subscriptionsUserResponse;
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

package vodagone.mapper;

import org.junit.Test;
import vodagone.domain.Subscription;
import vodagone.domain.compact.CompactSubscription;
import vodagone.dto.request.AddSubscriptionRequest;
import vodagone.dto.response.SubscriptionResponse;
import vodagone.dto.response.SubscriptionsUserResponse;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class SubscriptionResponseMapperTest {

    @Test
    public void mapToResponseSucceed() {
        SubscriptionResponseMapper subscriptionResponseMapper = new SubscriptionResponseMapper();

        Subscription subscription = new Subscription();

        subscription.setId(1);
        subscription.setAanbieder("");
        subscription.setDienst("");
        subscription.setPrijs("");
        subscription.setStartDatum(new Date());
        subscription.setVerdubbeling("");
        subscription.setDeelbaar(true);
        subscription.setStatus("");

        SubscriptionResponse subscriptionResponse = subscriptionResponseMapper.mapToResponse(subscription);

        assertEquals(subscriptionResponse.getId(), subscription.getId());
        assertEquals(subscriptionResponse.getAanbieder(), subscription.getAanbieder());
        assertEquals(subscriptionResponse.getDienst(), subscription.getDienst());
        assertEquals(subscriptionResponse.getPrijs(), subscription.getPrijs());
        assertEquals(subscriptionResponse.getStartDatum(), subscription.getStartDatum());
        assertEquals(subscriptionResponse.getVerdubbeling(), subscription.getVerdubbeling());
        assertEquals(subscriptionResponse.isDeelbaar(), subscription.isDeelbaar());
        assertEquals(subscriptionResponse.getStatus(), subscription.getStatus());
    }

    @Test
    public void mapToSubscriptionSucceed() {
        SubscriptionResponseMapper subscriptionResponseMapper = new SubscriptionResponseMapper();
        AddSubscriptionRequest addSubscriptionRequest = new AddSubscriptionRequest();

        addSubscriptionRequest.setId(1);
        addSubscriptionRequest.setAanbieder("");
        addSubscriptionRequest.setDienst("");

        Subscription subscription = subscriptionResponseMapper.mapToSubscription(addSubscriptionRequest);

        assertEquals(subscription.getId(), addSubscriptionRequest.getId());
        assertEquals(subscription.getAanbieder(), addSubscriptionRequest.getAanbieder());
        assertEquals(subscription.getDienst(), addSubscriptionRequest.getDienst());
    }

    @Test
    public void mapToCompactResponseSucceed() {
        SubscriptionResponseMapper subscriptionResponseMapper = new SubscriptionResponseMapper();

        Subscription subscription1 = new Subscription();
        Subscription subscription2 = new Subscription();

        subscription1.setId(1);
        subscription1.setAanbieder("");
        subscription1.setDienst("");
        subscription1.setPricetag(15.15);

        subscription2.setId(2);
        subscription2.setAanbieder("");
        subscription2.setDienst("");
        subscription2.setPricetag(15.15);

        ArrayList<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(subscription1);
        subscriptions.add(subscription2);

        SubscriptionsUserResponse subscriptionsUserResponse = subscriptionResponseMapper.mapToCompactResponse(subscriptions);

        CompactSubscription actualSubscription1 = subscriptionsUserResponse.getAbonnementen().get(0);
        CompactSubscription actualSubscription2 = subscriptionsUserResponse.getAbonnementen().get(1);

        assertEquals(actualSubscription1.getId(), subscription1.getId());
        assertEquals(actualSubscription1.getAanbieder(), subscription1.getAanbieder());
        assertEquals(actualSubscription1.getDienst(), subscription1.getDienst());

        assertEquals(actualSubscription2.getId(), subscription2.getId());
        assertEquals(actualSubscription2.getAanbieder(), subscription2.getAanbieder());
        assertEquals(actualSubscription2.getDienst(), subscription2.getDienst());

        assertEquals(subscriptionsUserResponse.getTotalPrice(), 30.30, 0.001);
    }

    @Test
    public void mapToCompactListSucceed() {
        SubscriptionResponseMapper subscriptionResponseMapper = new SubscriptionResponseMapper();

        Subscription subscription1 = new Subscription();
        Subscription subscription2 = new Subscription();

        subscription1.setId(1);
        subscription1.setAanbieder("");
        subscription1.setDienst("");

        subscription2.setId(2);
        subscription2.setAanbieder("");
        subscription2.setDienst("");

        ArrayList<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(subscription1);
        subscriptions.add(subscription2);

        ArrayList<CompactSubscription> compactUsers = subscriptionResponseMapper.mapToCompactList(subscriptions);

        CompactSubscription actualSubscription1 = compactUsers.get(0);
        CompactSubscription actualSubscription2 = compactUsers.get(1);

        assertEquals(actualSubscription1.getId(), subscription1.getId());
        assertEquals(actualSubscription1.getAanbieder(), subscription1.getAanbieder());
        assertEquals(actualSubscription1.getDienst(), subscription1.getDienst());

        assertEquals(actualSubscription2.getId(), subscription2.getId());
        assertEquals(actualSubscription2.getAanbieder(), subscription2.getAanbieder());
        assertEquals(actualSubscription2.getDienst(), subscription2.getDienst());
    }
}

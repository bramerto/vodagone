package vodagone.service;

import vodagone.application.Authentication;
import vodagone.domain.Subscription;
import vodagone.domain.User;
import vodagone.dto.CompactSubscription;
import vodagone.dto.request.AddSubscriptionRequest;
import vodagone.dto.request.UpgradeSubscriptionRequest;
import vodagone.dto.response.SubscriptionResponse;
import vodagone.dto.response.SubscriptionsForUserResponse;
import vodagone.store.SubscriptionDao;
import vodagone.store.UserDao;
import vodagone.mapper.SubscriptionResponseMapper;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/abonnementen")
public class SubscriptionService {

    @Inject
    private UserDao userDao; //TODO: DI does not work
    @Inject
    private SubscriptionDao subscriptionDao; //TODO: DI does not work
    @Inject
    private SubscriptionResponseMapper subscriptionResponseMapper; //TODO: DI does not work
    @Inject
    private Authentication authentication; //TODO: DI does not work

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubscriptionsForUser(@QueryParam("token") String token) {
        try {
            Response.Status status = authentication.checkUserByToken(token);

            if (status != Response.Status.OK) {
                return Response.status(status).build();
            }

            User user = userDao.getUserByToken(token);

            ArrayList<Subscription> subscriptions = subscriptionDao.getAllSubscriptionsByUser(user.getId());
            SubscriptionsForUserResponse response = subscriptionResponseMapper.mapSubscriptionListToCompactResponse(subscriptions);

            return Response.status(status).entity(response).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSubscription(@QueryParam("token") String token, AddSubscriptionRequest request) {
        try {
            Response.Status status = authentication.checkUserByToken(token);

            if (status != Response.Status.OK) {
                return Response.status(status).build();
            }

            Subscription subscription = subscriptionResponseMapper.mapSingleAddRequestToSubscription(request);

            subscriptionDao.addSubscription(subscription);

            ArrayList<Subscription> subscriptions = subscriptionDao.getAllSubscriptions();
            SubscriptionsForUserResponse response = subscriptionResponseMapper.mapSubscriptionListToCompactResponse(subscriptions);

            return Response.status(Response.Status.CREATED).entity(response).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubscription(@PathParam("id") int id, @QueryParam("token") String token) {
        try {
            Response.Status status = authentication.checkUserByToken(token);

            if (status != Response.Status.OK) {
                return Response.status(status).build();
            } else if (id == 0) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            User user = userDao.getUserByToken(token);

            Subscription subscription = subscriptionDao.getSubscription(id, user.getId());
            SubscriptionResponse response = subscriptionResponseMapper.mapSingleSubscriptionToResponse(subscription);

            return Response.status(Response.Status.OK).entity(response).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response terminateSubscription(@PathParam("id") int id, @QueryParam("token") String token) {
        try {
            Response.Status status = authentication.checkUserByToken(token);

            if (status != Response.Status.OK) {
                return Response.status(status).build();

            } else if (id == 0) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            User user = userDao.getUserByToken(token);

            Subscription subscription = subscriptionDao.getSubscription(id, user.getId());
            subscriptionDao.terminateSubscription(subscription);
            Subscription updatedSubscription = subscriptionDao.getSubscription(id, user.getId());

            return Response.status(Response.Status.OK).entity(updatedSubscription).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upgradeSubscription(@PathParam("id") int id, @QueryParam("token") String token,
                                        UpgradeSubscriptionRequest request) {
        try {
            Response.Status status = authentication.checkUserByToken(token);

            if (status != Response.Status.OK) {
                return Response.status(status).build();
            } else if (id == 0) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            User user = userDao.getUserByToken(token);

            subscriptionDao.upgradeSubscription(id, user.getId(), request.getVerdubbeling());
            return Response.status(Response.Status.CREATED).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSubscriptions(@QueryParam("token") String token, @QueryParam("filter") String filter) {
        try {
            Response.Status status = authentication.checkUserByToken(token);

            if (status != Response.Status.OK) {
                return Response.status(status).build();
            }

            ArrayList<Subscription> subscriptions = (filter != null) ? subscriptionDao.getAllSubscriptions(filter) : subscriptionDao.getAllSubscriptions();
            ArrayList<CompactSubscription> compactSubscriptions = subscriptionResponseMapper.mapSubscriptionListToCompactList(subscriptions);

            return Response.status(Response.Status.OK).entity(compactSubscriptions).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}

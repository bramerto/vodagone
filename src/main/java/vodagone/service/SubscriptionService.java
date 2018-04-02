package vodagone.service;

import vodagone.application.util.Validation;
import vodagone.domain.Subscription;
import vodagone.domain.User;
import vodagone.domain.compact.CompactSubscription;
import vodagone.dto.request.AddSubscriptionRequest;
import vodagone.dto.request.UpgradeSubscriptionRequest;
import vodagone.dto.response.SubscriptionResponse;
import vodagone.dto.response.SubscriptionsUserResponse;
import vodagone.mapper.DB.SubscriptionDBMapper;
import vodagone.mapper.DB.UserDBMapper;
import vodagone.store.SubscriptionDao;
import vodagone.store.UserDao;
import vodagone.mapper.SubscriptionResponseMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/abonnementen")
public class SubscriptionService {

    private UserDao userDao = new UserDao();
    private SubscriptionDao subscriptionDao = new SubscriptionDao();
    private SubscriptionDBMapper subscriptionDBMapper = new SubscriptionDBMapper();
    private UserDBMapper userDBMapper = new UserDBMapper();
    private SubscriptionResponseMapper subscriptionResponseMapper = new SubscriptionResponseMapper();
    private Validation validation = new Validation();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubscriptionsForUser(@QueryParam("token") String token) {
        try {
            if (!validation.checkToken(token)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            ResultSet RSuser = userDao.getUserByToken(token);
            User user = userDBMapper.getSingle(RSuser);

            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            ResultSet RSsubscriptions = subscriptionDao.getAllSubscriptionsByUser(user.getId());
            ArrayList<Subscription> subscriptions = subscriptionDBMapper.getList(RSsubscriptions);

            if (subscriptions == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            SubscriptionsUserResponse response = subscriptionResponseMapper.mapToCompactResponse(subscriptions);

            return Response.status(Response.Status.OK).entity(response).build();

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

            if (!validation.checkToken(token)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            Subscription subscription = subscriptionResponseMapper.mapToSubscription(request);
            subscriptionDao.addSubscription(subscription);

            ResultSet RSsubscriptions = subscriptionDao.getAll();
            ArrayList<Subscription> subscriptions = subscriptionDBMapper.getList(RSsubscriptions);

            if (subscriptions == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            SubscriptionsUserResponse response = subscriptionResponseMapper.mapToCompactResponse(subscriptions);

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
            if (!validation.checkToken(token) || id == 0) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            ResultSet RSuser = userDao.getUserByToken(token);
            User user = userDBMapper.getSingle(RSuser);

            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            ResultSet RSsubscription = subscriptionDao.getSubscription(id, user.getId());
            Subscription subscription = subscriptionDBMapper.getSingle(RSsubscription);

            if (subscription == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            SubscriptionResponse response = subscriptionResponseMapper.mapToResponse(subscription);

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
            if (!validation.checkToken(token) || id == 0) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            ResultSet RSuser = userDao.getUserByToken(token);
            User user = userDBMapper.getSingle(RSuser);

            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            ResultSet RSsubscription = subscriptionDao.getSubscription(id, user.getId());
            Subscription subscription = subscriptionDBMapper.getSingle(RSsubscription);

            if (subscription == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            subscriptionDao.terminateSubscription(subscription.getId());
            RSsubscription = subscriptionDao.getSubscription(id, user.getId());
            Subscription updatedSubscription = subscriptionDBMapper.getSingle(RSsubscription);

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
            if (!validation.checkToken(token) || id == 0) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            ResultSet RSuser = userDao.getUserByToken(token);
            User user = userDBMapper.getSingle(RSuser);

            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

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
            if (!validation.checkToken(token)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            ResultSet RSuser = userDao.getUserByToken(token);
            User user = userDBMapper.getSingle(RSuser);

            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            ResultSet RSsubscriptions = (filter != null) ? subscriptionDao.getAll(filter) : subscriptionDao.getAll();
            ArrayList<Subscription> subscriptions = subscriptionDBMapper.getList(RSsubscriptions);

            if (subscriptions == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            ArrayList<CompactSubscription> compactSubscriptions = subscriptionResponseMapper.mapToCompactList(subscriptions);

            return Response.status(Response.Status.OK).entity(compactSubscriptions).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}

package vodagone.service;

import vodagone.application.util.Validation;
import vodagone.domain.Subscription;
import vodagone.domain.User;
import vodagone.domain.compact.CompactUser;
import vodagone.dto.request.ShareSubscriptionRequest;
import vodagone.mapper.DB.SubscriptionDBMapper;
import vodagone.mapper.DB.UserDBMapper;
import vodagone.mapper.UserResponseMapper;
import vodagone.store.SubscriptionDao;
import vodagone.store.UserDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/abonnees")
public class SubscriberService {

    private UserDao userDao = new UserDao();
    private SubscriptionDao subscriptionDao = new SubscriptionDao();
    private UserResponseMapper userResponseMapper = new UserResponseMapper();
    private Validation validation = new Validation();
    private SubscriptionDBMapper subscriptionDBMapper = new SubscriptionDBMapper();
    private UserDBMapper userDBMapper = new UserDBMapper();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSubscribers(@QueryParam("token") String token) {
        try {
            if (!validation.checkToken(token)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            if (userDao.getUserByToken(token) == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            ResultSet RSusers = userDao.getAll();

            ArrayList<User> users = userDBMapper.getList(RSusers);

            if (users == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            ArrayList<CompactUser> response = userResponseMapper.mapToCompactList(users);

            return Response.status(Response.Status.OK).entity(response).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response shareSubscription(@PathParam("id") int id, @QueryParam("token") String token,
                                      ShareSubscriptionRequest request) {
        try {
            if (!validation.checkToken(token) || id == 0) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            ResultSet RSuser = userDao.getUserByToken(token);
            User user = userDBMapper.getSingle(RSuser);

            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            ResultSet RSsubscription = subscriptionDao.getSubscription(request.getId(), user.getId());
            Subscription subscription = subscriptionDBMapper.getSingle(RSsubscription);

            if (!subscription.isDeelbaar()) {
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }

            ResultSet RSsharedToUser = userDao.getUserByToken(token);
            User sharedToUser = userDBMapper.getSingle(RSsharedToUser);

            subscriptionDao.shareSubscription(sharedToUser.getId(), subscription.getId());

            return Response.status(Response.Status.CREATED).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}

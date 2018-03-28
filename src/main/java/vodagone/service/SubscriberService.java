package vodagone.service;

import vodagone.application.Authentication;
import vodagone.domain.Subscription;
import vodagone.domain.User;
import vodagone.dto.CompactUser;
import vodagone.dto.request.ShareSubscriptionRequest;
import vodagone.mapper.UserResponseMapper;
import vodagone.store.SubscriptionDao;
import vodagone.store.UserDao;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/abonnees")
public class SubscriberService {

    @Inject
    private UserDao userDao; //TODO: DI does not work
    @Inject
    private SubscriptionDao subscriptionDao; //TODO: DI does not work
    @Inject
    private UserResponseMapper userResponseMapper; //TODO: DI does not work
    @Inject
    private Authentication authentication; //TODO: DI does not work

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSubscribers(@QueryParam("token") String token) {
        try {
            Response.Status status = authentication.checkUserByToken(token);

            if (status != Response.Status.OK) {
                return Response.status(status).build();
            }

            ArrayList<User> users = userDao.getAllUsers();

            if (users == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            ArrayList<CompactUser> response = userResponseMapper.mapUserListToCompactList(users);

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
            Response.Status status = authentication.checkUserByToken(token);

            if (status != Response.Status.OK) {
                return Response.status(status).build();
            } else if (id == 0) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            User user = userDao.getUserByToken(token);

            Subscription subscription = subscriptionDao.getSubscription(request.getId(), user.getId());

            if (!subscription.isDeelbaar()) {
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }

            User sharedToUser = userDao.getUserById(id);
            subscriptionDao.shareSubscription(sharedToUser, subscription);

            return Response.status(Response.Status.CREATED).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}

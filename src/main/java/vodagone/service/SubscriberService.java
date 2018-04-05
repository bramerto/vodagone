package vodagone.service;

import vodagone.application.util.Validation;
import vodagone.controller.SubscriptionController;
import vodagone.controller.UserController;
import vodagone.domain.Subscription;
import vodagone.domain.User;
import vodagone.domain.compact.CompactUser;
import vodagone.dto.request.ShareSubscriptionRequest;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/abonnees")
public class SubscriberService {

    @Inject
    private Validation validation;
    @Inject
    private UserController userController;
    @Inject
    private SubscriptionController subscriptionController;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSubscribers(@QueryParam("token") String token) {
        try {
            if (!validation.checkToken(token)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            User user = userController.AuthenticateUser(token);

            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            ArrayList<CompactUser> response = userController.getAllUsers();

            return (response == null) ?
                    Response.status(Response.Status.NOT_FOUND).build() :
                    Response.status(Response.Status.OK).entity(response).build();

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
            if (!validation.checkToken(token) || !validation.checkId(id)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            User user = userController.AuthenticateUser(token);

            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            Subscription subscription = subscriptionController.checkIfShareable(request.getId(), user.getId());

            if (subscription == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            userController.shareSubscription(subscription.getId(), token);

            return Response.status(Response.Status.CREATED).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}

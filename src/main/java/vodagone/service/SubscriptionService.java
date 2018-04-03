package vodagone.service;

import vodagone.application.util.Validation;
import vodagone.controller.SubscriptionController;
import vodagone.controller.UserController;
import vodagone.domain.Subscription;
import vodagone.domain.User;
import vodagone.domain.compact.CompactSubscription;
import vodagone.dto.request.AddSubscriptionRequest;
import vodagone.dto.request.UpgradeSubscriptionRequest;
import vodagone.dto.response.SubscriptionResponse;
import vodagone.dto.response.SubscriptionsUserResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/abonnementen")
public class SubscriptionService {

    @Inject
    private Validation validation;
    @Inject
    private SubscriptionController subscriptionController;
    @Inject
    private UserController userController;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubscriptions(@QueryParam("token") String token) {
        try {
            if (!validation.checkToken(token)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            User user = userController.AuthenticateUser(token);

            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            SubscriptionsUserResponse response = subscriptionController.getSubscriptionsForUser(user);

            return (response == null) ?
                    Response.status(Response.Status.NOT_FOUND).build() :
                    Response.status(Response.Status.OK).entity(response).build();

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

            SubscriptionsUserResponse response = subscriptionController.addSubscription(request);

            return (response == null) ?
                    Response.status(Response.Status.NOT_FOUND).build() :
                    Response.status(Response.Status.CREATED).entity(response).build();

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

            User user = userController.AuthenticateUser(token);

            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            SubscriptionResponse response = subscriptionController.getSubscription(id, user);

            return (response == null) ?
                    Response.status(Response.Status.NOT_FOUND).build() :
                    Response.status(Response.Status.OK).entity(response).build();

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

            User user = userController.AuthenticateUser(token);

            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            Subscription response = subscriptionController.terminateSubscription(id, user);

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
    public Response upgradeSubscription(@PathParam("id") int id, @QueryParam("token") String token,
                                        UpgradeSubscriptionRequest request) {
        try {
            if (!validation.checkToken(token) || id == 0) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            User user = userController.AuthenticateUser(token);

            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            subscriptionController.upgradeSubscription(id, user.getId(), request.getVerdubbeling());
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

            User user = userController.AuthenticateUser(token);

            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            ArrayList<CompactSubscription> response = subscriptionController.getAllSubscriptions(filter);

            return (response == null) ?
                    Response.status(Response.Status.NOT_FOUND).build() :
                    Response.status(Response.Status.OK).entity(response).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}

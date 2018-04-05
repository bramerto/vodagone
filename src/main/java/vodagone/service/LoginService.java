package vodagone.service;

import vodagone.controller.UserController;
import vodagone.domain.User;
import vodagone.dto.request.LoginRequest;
import vodagone.dto.response.LoginResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/login")
public class LoginService {

    @Inject
    private UserController userController;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request) {

        try {
            User user = userController.AuthenticateUser(request.getUser(), request.getPassword());

            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            LoginResponse response = userController.getLogin(user);

            return Response.status(Response.Status.CREATED).entity(response).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}

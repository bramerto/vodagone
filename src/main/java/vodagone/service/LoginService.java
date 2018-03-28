package vodagone.service;

import vodagone.domain.User;
import vodagone.dto.request.LoginRequest;
import vodagone.dto.response.LoginResponse;
import vodagone.mapper.UserResponseMapper;
import vodagone.store.UserDao;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/login")
public class LoginService {

    @Inject
    private UserDao userDao; //TODO: DI does not work

    @Inject
    private UserResponseMapper userResponseMapper; //TODO: DI does not work

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request) {

        try {
            User user = userDao.getUserByLogin(request.getUser(), request.getPassword());

            if (user == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            LoginResponse response = userResponseMapper.mapSingleUserToLoginResponse(user);

            return Response.status(Response.Status.CREATED).entity(response).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}

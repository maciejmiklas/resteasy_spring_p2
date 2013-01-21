package org.mmiklas.resttutorial.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.mmiklas.resttutorial.exception.IncorrectLengthException;
import org.mmiklas.resttutorial.exception.MessageForbidenException;
import org.mmiklas.resttutorial.model.Headers;
import org.mmiklas.resttutorial.model.HelloMessage;
import org.mmiklas.resttutorial.model.HelloResponse;
import org.mmiklas.resttutorial.model.RespCodes;
import org.mmiklas.resttutorial.server.HelloSpringService;

@Named
@Path("/Hello/catch")
public class HelloRestServiceCatch {

    private final static Logger LOG = Logger.getAnonymousLogger();

    @Inject
    private HelloSpringService halloService;

    // curl http://localhost:8080/resteasy_spring_p2/rest/Hello/catch/text?msg=Hi%20There
    @GET
    @Path("text")
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public Response sayTextHello(@QueryParam("msg") String msg) {
        try {
            String resp = halloService.sayTextHello(msg);
            return Response.ok(resp).build();

        } catch (MessageForbidenException e) {
            return handleMessageForbidenException(e);

        } catch (IncorrectLengthException e) {
            return handleIncorrectLengthException(e);

        } catch (Exception e) {
            return handleException(e);
        }
    }

    // curl -X POST -H "Content-Type: application/json" -d '{"msg":"Hi There","gender":"MALE"}'
    // http://localhost:8080/resteasy_spring_p2/rest/Hello/catch/javabean
    @POST
    @Path("javabean")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response sayJavaBeanHello(HelloMessage msg) {
        try {
            HelloResponse resp = halloService.sayJavaBeanHello(msg);
            return Response.ok(resp).build();

        } catch (MessageForbidenException e) {
            return handleMessageForbidenException(e);

        } catch (IncorrectLengthException e) {
            return handleIncorrectLengthException(e);

        } catch (Exception e) {
            return handleException(e);
        }
    }

    private Response handleException(Exception e) {
        LOG.log(Level.WARNING, e.getMessage(), e);

        return Response.status(Status.INTERNAL_SERVER_ERROR).header(Headers.EX_CLASS.name(), e.getClass().getCanonicalName())
                .entity(e.getMessage() + " - " + getStackTrace(e)).build();
    }

    private Response handleIncorrectLengthException(IncorrectLengthException e) {
        return Response.status(Status.BAD_REQUEST).header(Headers.ERR_CODE.name(), RespCodes.INCORRECT_LENGTH.name())
                .entity(e.getMessage()).build();
    }

    private Response handleMessageForbidenException(MessageForbidenException e) {
        return Response.status(Status.BAD_REQUEST).header(Headers.ERR_CODE.name(), RespCodes.MESSAGE_FORBIDDEN.name())
                .entity(e.getMessage()).build();
    }

    private String getStackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        ex.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}

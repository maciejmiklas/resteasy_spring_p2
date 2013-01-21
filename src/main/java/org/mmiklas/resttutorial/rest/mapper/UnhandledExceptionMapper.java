package org.mmiklas.resttutorial.rest.mapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.mmiklas.resttutorial.model.Headers;

@Provider
@Named
public class UnhandledExceptionMapper implements ExceptionMapper<Exception> {

    private final static Logger LOG = Logger.getAnonymousLogger();

    public Response toResponse(Exception e) {
        LOG.log(Level.WARNING, e.getMessage(), e);

        return Response.status(Status.INTERNAL_SERVER_ERROR).header(Headers.EX_CLASS.name(), e.getClass().getCanonicalName())
                .entity(e.getMessage() + " - " + getStackTrace(e)).build();
    }

    private String getStackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        ex.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}

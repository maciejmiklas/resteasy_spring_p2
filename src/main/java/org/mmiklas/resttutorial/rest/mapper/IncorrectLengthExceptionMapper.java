package org.mmiklas.resttutorial.rest.mapper;

import javax.inject.Named;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.mmiklas.resttutorial.exception.IncorrectLengthException;
import org.mmiklas.resttutorial.model.Headers;
import org.mmiklas.resttutorial.model.RespCodes;

@Provider
@Named
public class IncorrectLengthExceptionMapper implements ExceptionMapper<IncorrectLengthException> {

    public Response toResponse(IncorrectLengthException e) {
        return Response.status(Status.BAD_REQUEST).header(Headers.ERR_CODE.name(), RespCodes.INCORRECT_LENGTH.name())
                .entity(e.getMessage()).build();
    }

}

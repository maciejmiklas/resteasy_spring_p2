package org.mmiklas.resttutorial.rest.mapper;

import javax.inject.Named;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.mmiklas.resttutorial.exception.MessageForbidenException;
import org.mmiklas.resttutorial.model.Headers;
import org.mmiklas.resttutorial.model.RespCodes;

@Provider
@Named
public class MessageForbidenExceptionMapper implements ExceptionMapper<MessageForbidenException> {

    public Response toResponse(MessageForbidenException e) {
        return Response.status(Status.BAD_REQUEST).header(Headers.ERR_CODE.name(), RespCodes.MESSAGE_FORBIDDEN.name())
                .entity(e.getMessage()).build();
    }

}

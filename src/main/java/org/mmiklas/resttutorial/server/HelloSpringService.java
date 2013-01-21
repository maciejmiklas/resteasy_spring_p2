package org.mmiklas.resttutorial.server;

import java.util.Date;

import javax.inject.Named;

import org.mmiklas.resttutorial.exception.IncorrectLengthException;
import org.mmiklas.resttutorial.exception.MessageForbidenException;
import org.mmiklas.resttutorial.model.Gender;
import org.mmiklas.resttutorial.model.HelloMessage;
import org.mmiklas.resttutorial.model.HelloResponse;

@Named
public class HelloSpringService {

    public String sayTextHello(String msg) throws MessageForbidenException, IncorrectLengthException {
        verifyIncommingMessage(msg);

        return msg + "--> Hello";
    }

    public HelloResponse sayJavaBeanHello(HelloMessage msg) throws MessageForbidenException, IncorrectLengthException {
        verifyIncommingMessage(msg.getMsg());

        return new HelloResponse(msg.getMsg() + "--> Hello " + (msg.getGender() == Gender.MALE ? "Sir" : "Madam"), new Date());
    }

    private void verifyIncommingMessage(String msg) throws MessageForbidenException, IncorrectLengthException {
        if (msg == null) {
            throw new IncorrectLengthException("Empty message not allowed");
        }
        msg = msg.trim();
        int msgLength = msg.length();
        if (msgLength < 3 || msgLength > 10) {
            throw new IncorrectLengthException("Message length not between 3 and 10 characters");
        }

        if (msg.trim().toLowerCase().startsWith("jo!")) {
            throw new MessageForbidenException("Jo! is not allowed");
        }
    }
}

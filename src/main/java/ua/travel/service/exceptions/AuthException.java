package ua.travel.service.exceptions;

import lombok.Getter;

/**
 * Created by yuuto on 5/26/17.
 */

@Getter
public class AuthException extends Exception {

    private String login;
    private String password;

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String login, String password){
        this.login = login;
        this.password = password;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " " + login + " " + password;
    }
}

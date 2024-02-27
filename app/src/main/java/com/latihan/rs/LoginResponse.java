package com.latihan.rs;

public class LoginResponse {

    private String token;
    private String message;
    private String success;

    public LoginResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }
    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

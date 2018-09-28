package jj.fly.user.response;

import java.io.Serializable;

/**
 * Author: jiangjun
 * Date: 2018/9/25
 * Time: 上午11:19
 * Description:
 */
public class Response implements Serializable {

    private String code;

    private String message;

    public static Response USERNAME_PASSWORD_INVALID = new Response("1001", "username or password is invalid!");

    public static Response MOBILE_OR_EMAIL_REQUIRED = new Response("1002", "mobile or emial is required");

    public static Response SEND_VERIFY_CODE_ERROR = new Response("1003", "send verify code error");

    public static Response VERIFY_CODE_INVALID = new Response("1004", "verify code is invalid!");


    public static Response SUCCESS = new Response();

    public static Response exception(Exception e){
        return new Response("9999", e.getMessage());
    }

    public Response(){
        this.code = "0";
        this.message = "success";
    }

    public Response(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

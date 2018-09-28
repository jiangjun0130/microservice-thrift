package jj.fly.user.response;

/**
 * Author: jiangjun
 * Date: 2018/9/25
 * Time: 下午3:43
 * Description:
 */
public class LoginResponse extends Response {

    private String token;

    public LoginResponse(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

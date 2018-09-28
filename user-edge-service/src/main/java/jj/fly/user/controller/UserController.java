package jj.fly.user.controller;

import com.jj.fly.thrift.user.UserInfo;
import com.jj.fly.thrift.user.dto.UserDTO;
import jj.fly.user.response.LoginResponse;
import jj.fly.user.response.Response;
import jj.fly.user.thrift.ServiceProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Author: jiangjun
 * Date: 2018/9/25
 * Time: 上午11:08
 * Description:
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ServiceProvider serviceProvider;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public Response login(@RequestParam("username") String username,
                          @RequestParam("password") String password){
        UserInfo userInfo = null;
        try {
            userInfo = serviceProvider.getUserService().getUserByUserName(username);
        } catch (TException e) {
            e.printStackTrace();
            return Response.USERNAME_PASSWORD_INVALID;
        }
        if (userInfo == null){
            return Response.USERNAME_PASSWORD_INVALID;
        }
        // 验证密码
        if(!userInfo.getPassword().equalsIgnoreCase(md5(password))){
            return Response.USERNAME_PASSWORD_INVALID;
        }
        // 生成token
        String token = genToken();

        // 缓存用户
        redisTemplate.opsForValue().set(token, obj2DTO(userInfo), 3600, TimeUnit.SECONDS);
        return new LoginResponse(token);
    }

    @PostMapping("/regeister")
    @ResponseBody
    public Response regeister(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam(value = "mobile", required = false) String mobile,
                              @RequestParam(value = "email", required = false) String email,
                              @RequestParam("verifyCode") String verifyCode){
        if(StringUtils.isBlank(mobile) && StringUtils.isBlank(email)){
            return Response.USERNAME_PASSWORD_INVALID;
        }
        if(StringUtils.isNotBlank(mobile)){
            String code = (String) redisTemplate.opsForValue().get(mobile);
            if(!verifyCode.equals(code)){
                return Response.VERIFY_CODE_INVALID;
            }
        }else if(StringUtils.isNotBlank(email)){
            String code = (String) redisTemplate.opsForValue().get(email);
            if(!verifyCode.equals(code)){
                return Response.VERIFY_CODE_INVALID;
            }
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(username);
        userInfo.setPassword(md5(password));
        userInfo.setMobile(mobile);
        userInfo.setEmail(email);
        try {
            serviceProvider.getUserService().registerUser(userInfo);
        } catch (TException e) {
            e.printStackTrace();
            return Response.exception(e);
        }
        return Response.SUCCESS;
    }

    @PostMapping("/sendVerifyCode")
    @ResponseBody
    public Response sendVerifyCode(@RequestParam(value = "mobile", required = false) String mobile,
                                   @RequestParam(value = "email", required = false) String email){
        if(StringUtils.isBlank(mobile) && StringUtils.isBlank(email)){
            return Response.USERNAME_PASSWORD_INVALID;
        }
        boolean result;
        String code = randomCode("0123456789", 6);
        String message = "Verify code is: " + code;
        try {
            if(StringUtils.isNotBlank(mobile)){
                result =serviceProvider.getMessageService().sendMobileMsg(mobile, message);
                redisTemplate.opsForValue().set(mobile, code);

            }else{
                result =serviceProvider.getMessageService().sendEmailMsg(mobile, message);

            }
            if(!result){
                return Response.SEND_VERIFY_CODE_ERROR;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.exception(e);
        }
        return Response.SUCCESS;
    }

    @PostMapping("/auth")
    @ResponseBody
    public UserDTO auth(@RequestHeader("token") String token){
        return (UserDTO) redisTemplate.opsForValue().get(token);
    }

    private UserDTO obj2DTO(UserInfo userInfo){
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(userInfo, dto);
        return dto;
    }

    private String genToken() {
        return randomCode("0123456789abcdefghijklmnopqrstuvwxyz", 32);
    }

    private String randomCode(String s, int size) {
        StringBuffer result = new StringBuffer(size);
        Random random = new Random();
        for(int i=0; i<size; i++){
            int index = random.nextInt(s.length());
            result.append(s.charAt(index));
        }
        return result.toString();
    }

    private String md5(String password) {

        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] bytes = md5.digest(password.getBytes("utf-8"));
            return HexUtils.toHexString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

package jj.fly.user;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jj.fly.thrift.user.dto.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Author: jiangjun
 * Date: 2018/9/25
 * Time: 下午6:54
 * Description:
 */
public abstract class LoginFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    private static Cache<String, UserDTO> cache = CacheBuilder.newBuilder().maximumSize(1000)
            .expireAfterAccess(3, TimeUnit.MINUTES).build();

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = request.getParameter("token");
        logger.info("$$$$$$$$$$$$$$$$$$$$$token:" + token);
        if(StringUtils.isBlank(token)){
            Cookie[] cookies = request.getCookies();
            if(cookies != null){
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals("token")){
                        token = cookie.getValue();
                    }
                }
            }
        }
        UserDTO userDTO = null;
        if(StringUtils.isNotBlank(token)){
            userDTO = cache.getIfPresent(token);
            if(userDTO == null) {
                userDTO = requestUserInfo(token);
                if(userDTO != null){
                    cache.put(token, userDTO);
                }
            }
        }
        if(userDTO == null){
            response.sendRedirect("http://user-edge-service:8082/user/login");
            //response.sendRedirect("http://127.0.0.1:8082/user/login");
            return;
        }
        login(request,response, userDTO);
        filterChain.doFilter(request, response);
    }

    protected abstract String userEdgeServiceAddr();

    protected abstract void login(HttpServletRequest request, HttpServletResponse response, UserDTO userDTO);

    private UserDTO requestUserInfo(String token) {
        String url = "http://"+userEdgeServiceAddr()+"/user/auth";
        //String url = "http://127.0.0.1:8082/user/auth";
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.addHeader("token", token);
        InputStream inputStream = null;
        try {
            HttpResponse response = client.execute(post);
            if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
                throw new RuntimeException("request user info error!statusLine:" + response.getStatusLine());
            }
            inputStream = response.getEntity().getContent();
            byte[] bytes = new byte[1024];
            StringBuilder sb = new StringBuilder();
            int len = 0;
            while ((len = inputStream.read(bytes)) > 0){
                sb.append(new String(bytes,0, len));
            }
            if(sb.length() <= 0){
                return null;
            }
            UserDTO userDTO = new ObjectMapper().readValue(sb.toString(), UserDTO.class);
            logger.info("$$$$$$$$$$$$$$$$$$$$userDTO:" + userDTO.toString());
            return userDTO;

        } catch (IOException e) {
            logger.error("获取userDTO异常！￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥4");
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void destroy() {

    }
}

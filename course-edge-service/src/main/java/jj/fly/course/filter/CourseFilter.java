package jj.fly.course.filter;

import com.jj.fly.thrift.user.dto.UserDTO;
import jj.fly.user.LoginFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: jiangjun
 * Date: 2018/9/26
 * Time: 下午2:24
 * Description:
 */
public class CourseFilter extends LoginFilter {

    @Override
    protected void login(HttpServletRequest request, HttpServletResponse response, UserDTO userDTO) {
        request.setAttribute("user", userDTO);
    }
}

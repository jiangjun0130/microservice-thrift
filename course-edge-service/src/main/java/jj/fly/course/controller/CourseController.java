package jj.fly.course.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jj.fly.thrift.user.dto.UserDTO;
import jj.fly.course.dto.CourseDTO;
import jj.fly.course.service.ICourseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Author: jiangjun
 * Date: 2018/9/26
 * Time: 上午11:28
 * Description:
 */
@Controller
@RequestMapping("/course")
public class CourseController {

    @Reference
    private ICourseService courseService;

    @GetMapping("/courseList")
    @ResponseBody
    public List<CourseDTO> courseList(HttpServletRequest request){
//        System.out.println(applicaitonName);
//        UserDTO userDTO = (UserDTO) request.getAttribute("user");
//        System.out.println(userDTO.toString());
        return courseService.courseList();
    }

}

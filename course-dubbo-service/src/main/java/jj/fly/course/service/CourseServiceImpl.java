package jj.fly.course.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.jj.fly.thrift.user.UserInfo;
import com.jj.fly.thrift.user.dto.TeacherDTO;
import jj.fly.course.dto.CourseDTO;
import jj.fly.course.mapper.CourseMapper;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Author: jiangjun
 * Date: 2018/9/26
 * Time: 上午10:06
 * Description:
 */
@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ServiceProvider serviceProvider;

    @Override
    public List<CourseDTO> courseList() {
        List<CourseDTO> list = courseMapper.listCourse();
        if(list != null){
            for(CourseDTO dto : list){
                Integer teacherId = courseMapper.getCourseTeacher(dto.getId());
                if(teacherId != null){
                    try {
                        UserInfo teacher = serviceProvider.getUserService().getTeacherById(teacherId);
                        dto.setTeacher(trans2Teacher(teacher));
                    } catch (TException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        }
        return list;
    }

    private TeacherDTO trans2Teacher(UserInfo teacher) {
        TeacherDTO teacherDTO = new TeacherDTO();
        BeanUtils.copyProperties(teacher, teacherDTO);
        return teacherDTO;
    }
}

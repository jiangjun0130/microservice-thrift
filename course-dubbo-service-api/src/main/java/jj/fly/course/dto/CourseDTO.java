package jj.fly.course.dto;

import com.jj.fly.thrift.user.dto.TeacherDTO;

import java.io.Serializable;

/**
 * Author: jiangjun
 * Date: 2018/9/26
 * Time: 上午9:53
 * Description:
 */
public class CourseDTO implements Serializable {

    private int id;

    private String title;

    private String description;

    private TeacherDTO teacher;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }
}

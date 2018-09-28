package com.jj.fly.thrift.user.dto;

import java.io.Serializable;

/**
 * Author: jiangjun
 * Date: 2018/9/25
 * Time: 下午3:41
 * Description:
 */
public class TeacherDTO extends UserDTO implements Serializable {

    private String intro;

    private Integer stats;

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Integer getStats() {
        return stats;
    }

    public void setStats(Integer stats) {
        this.stats = stats;
    }
}

package jj.fly.course;

import jj.fly.course.filter.CourseFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: jiangjun
 * Date: 2018/9/25
 * Time: 上午10:45
 * Description:
 */
@SpringBootApplication
//@EnableDubboConfiguration
//@EnableDubbo(scanBasePackages = "jj.fly.course")
public class CourseEdgeServiceApplication {

    public static void main(String[] args) {
        //System.setProperty("DUBBO_IP_TO_REGISTRY","192.168.0.104");

        SpringApplication.run(CourseEdgeServiceApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        CourseFilter courseFilter = new CourseFilter();
        filterRegistrationBean.setFilter(courseFilter);

        List<String> url = new ArrayList<>();
        url.add("/*");
        filterRegistrationBean.setUrlPatterns(url);
        return filterRegistrationBean;
    }
}

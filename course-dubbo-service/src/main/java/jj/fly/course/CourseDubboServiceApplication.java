package jj.fly.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * Author: jiangjun
 * Date: 2018/9/25
 * Time: 上午10:45
 * Description:
 */
@SpringBootApplication
//@EnableDubboConfiguration
//@EnableDubbo(scanBasePackages = "jj.fly.course")
public class CourseDubboServiceApplication {

    public static void main(String[] args) throws IOException {
        //这里手动设置本机ip，当本地是多网卡环境时，dubbo会默认取出第一块非localhost网卡的ip，但是有时候第一块网卡的ip可能不是正确的本机ip
        //System.setProperty("DUBBO_IP_TO_REGISTRY","192.168.0.103");
        SpringApplication.run(CourseDubboServiceApplication.class, args);
//        System.in.read();
    }
}

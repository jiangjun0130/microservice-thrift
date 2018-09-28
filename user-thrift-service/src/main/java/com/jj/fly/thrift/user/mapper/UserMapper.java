package com.jj.fly.thrift.user.mapper;

import com.jj.fly.thrift.user.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Author: jiangjun
 * Date: 2018/9/25
 * Time: 上午9:42
 * Description:
 */
@Mapper
public interface UserMapper {

    @Select("select id,username, password, real_name as realName, mobile, email " +
            "from pe_user where id=#{id}")
    UserInfo getUserById(@Param("id") int id);

    @Select("select id,username, password, real_name as realName, mobile, email " +
            "from pe_user where username=#{username}")
    UserInfo getUserByName(@Param("username") String username);

    @Insert("insert into pe_user (username, password, real_name, mobile, email) " +
            "values (#{u.userName}, #{u.password}, #{u.realName}, #{u.mobile}, #{u.email})")
    void registerUser(@Param("u") UserInfo userInfo);

    @Select("select u.id,u.username,u.password,u.real_name as realName," +
            "u.mobile, u.email, t.intro, t.stars " +
            "from pe_user u, pe_teacher t where t.user_id=u.id and u.id=#{id}")
    UserInfo getTeacherById(@Param("id") int id);
}

package com.jj.fly.thrift.user.service;

import com.jj.fly.thrift.user.UserInfo;
import com.jj.fly.thrift.user.UserService;
import com.jj.fly.thrift.user.mapper.UserMapper;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: jiangjun
 * Date: 2018/9/21
 * Time: 下午5:14
 * Description:
 */
@Service
public class UserServiceImpl implements UserService.Iface {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserInfo getUserById(int id) throws TException {
        return userMapper.getUserById(id);
    }

    @Override
    public UserInfo getTeacherById(int id) throws TException {
        return userMapper.getTeacherById(id);
    }

    @Override
    public UserInfo getUserByUserName(String userName) throws TException {
        return userMapper.getUserByName(userName);
    }

    @Override
    public void registerUser(UserInfo userInfo) throws TException {
        userMapper.registerUser(userInfo);
    }
}

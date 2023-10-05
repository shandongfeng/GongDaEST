package com.fengxiangkai.service.impl;

import com.fengxiangkai.mapper.UserInfoMapper;
import com.fengxiangkai.pojo.UserInfo;
import com.fengxiangkai.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public UserInfo selectUser(String userPhoneNumber) {
        return userInfoMapper.getByPhoneNumber(userPhoneNumber);
    }
    @Override
    public int addUser(String session_key,String openid,String name,String avatar,String phoneNumber,String token) {
        return userInfoMapper.addUserInfo(session_key,openid,name,avatar,phoneNumber,token);
    }
}

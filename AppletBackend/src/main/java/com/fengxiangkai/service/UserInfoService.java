package com.fengxiangkai.service;

import com.fengxiangkai.pojo.UserInfo;

public interface UserInfoService {
    //查询
    UserInfo selectUser(String userPhoneNumber);
    //添加
    int addUser(String session_key,String openid,String name,String avatar,String phoneNumber,String token);
}

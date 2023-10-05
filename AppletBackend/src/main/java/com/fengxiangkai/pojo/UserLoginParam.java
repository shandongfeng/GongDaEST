package com.fengxiangkai.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginParam {
    private int requestCode;
    private String code; //向微信后台发送请求获取openid等信息所需
    private String errMsg;
    private String name;
    private String avatar;
    private String phoneNumber;
    private String token;
}

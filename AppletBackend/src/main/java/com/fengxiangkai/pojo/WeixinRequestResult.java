package com.fengxiangkai.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeixinRequestResult {
    private String session_key;
    private String unionid;
    private String errmsg;
    private String openid;
    private int errcode;
}

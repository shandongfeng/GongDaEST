package com.fengxiangkai.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private int userId;
    private String name;
    private String avatar;
    private String phoneNumber;
    private String token;

}

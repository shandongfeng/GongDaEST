package com.fengxiangkai.mapper;

import com.fengxiangkai.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {
    @Select("select * from admin where username = #{username} and password = #{password}")
    Admin getByUsernameAndPassword(Admin admin);
}

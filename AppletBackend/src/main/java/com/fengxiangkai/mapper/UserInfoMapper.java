package com.fengxiangkai.mapper;

import com.fengxiangkai.pojo.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper {
    /**
     *
     * @param userPhoneNumber
     * @return LoginResult
     * 映射规则：
     *          1.返回类型中的成员名要与数据库字段名相同，才能正常映射
     *          2.mybatis采用驼峰命名法，如果字段名是user_id,那么java返回类型的成员名应该是userId
     */
    @Select("select user_id,name,avatar,phoneNumber,token from userInfo where phoneNumber = #{userPhoneNumber}")
    UserInfo getByPhoneNumber(String userPhoneNumber);
    @Insert("insert into userInfo(session_key,openid,name,avatar,phoneNumber,token) values(#{session_key},#{openid},#{name},#{avatar},#{phoneNumber},#{token})")
    @Options(useGeneratedKeys = true)
    int addUserInfo(String session_key,String openid,String name,String avatar,String phoneNumber,String token);
}

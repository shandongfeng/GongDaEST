package com.fengxiangkai.mapper;

import com.fengxiangkai.pojo.ProductInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductInfoMapper {
    /**
     * @param info imageInfo
     *             imageInfo是经过（保存在本地的图片的路径）拼接后的字符串
     * @return int 返回插入的商品信息在数据库的id
     * @error 本打算传送String[]到imagePath，可能因为数据库字段无法自动转换String[]为varchar导致失败
     */
    @Insert("insert into productInfo(user_id, description, type, fineness, originalPrice, currentPrice, address, service, time, imageInfo) " +
            "values(#{info.userId}, #{info.description}, #{info.type}, #{info.fineness}, #{info.originalPrice}, #{info.currentPrice}, #{info.address}, #{info.service}, #{info.time}, #{imageInfo})")
    @Options(useGeneratedKeys = true)
    int addInfo(@Param("info") ProductInfo info,@Param("imageInfo") String imageInfo);
}

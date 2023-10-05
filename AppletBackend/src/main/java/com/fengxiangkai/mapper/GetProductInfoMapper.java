package com.fengxiangkai.mapper;

import com.fengxiangkai.pojo.ProductItemResult;
import com.fengxiangkai.utils.StringHandleUtil;
import org.apache.ibatis.annotations.*;

/**
 * @Annotate
    * @Results 返回结果集处理,可以将其封装到一个类中，变成公共配置 https://blog.csdn.net/yangshangwei/article/details/79983495
    * @Results(字段名，要映射到返回类型的属性名，字段数据类型，映射处理成返回类型的属性的数据类型)
 * @key 字段映射只能对应返回类型的每个基本数据类型的属性，如果是自定义类型的属性，需要设置@Result进行特殊映射处理
 */
@Mapper
public interface GetProductInfoMapper {
    //获取数据库长度
    @Select("select count(*) from productinfo")
    int selectDBLength();
    //根据指定索引获取数据库信息
    @Select("SELECT userinfo.name, userinfo.avatar, productinfo.product_id,productinfo.user_id, productinfo.description, " +
    "productinfo.type, productinfo.fineness, productinfo.originalPrice, productinfo.currentPrice, " +
    "productinfo.address, productinfo.service, productinfo.time, productinfo.imageInfo " +
    "FROM productinfo JOIN userinfo ON productinfo.user_id = userinfo.user_id " +
    "WHERE productinfo.product_id>#{beginIndex} AND productinfo.product_id <= #{endIndex}")
    @Results({
        @Result(column="product_id", property = "productInfo.productId"),
        @Result(column="user_id", property = "productInfo.userId"),
        @Result(column="description", property = "productInfo.description"),
        @Result(column="type", property = "productInfo.type"),
        @Result(column="fineness", property = "productInfo.fineness"),
        @Result(column="originalPrice", property = "productInfo.originalPrice"),
        @Result(column="currentPrice", property = "productInfo.currentPrice"),
        @Result(column="address", property = "productInfo.address"),
        @Result(column="service", property = "productInfo.service"),
        @Result(column="time", property = "productInfo.time"),
        @Result(column = "imageInfo", property = "productInfo.imageInfo", javaType = String[].class, typeHandler = StringHandleUtil.class),
    })
    ProductItemResult[] getInfo(int beginIndex,int endIndex);
}

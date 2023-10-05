package com.fengxiangkai.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {
    private int productId;
    private int userId;                 //用户外键
    private String description;             //商品描述
    private String type;             //商品类型
    private String fineness;         //成色
    private double originalPrice;     //原价
    private double currentPrice;        //现价
    private String address;          //地址
    private String service;             //售后服务
    private Date time;                       //发布时间
    private String[] imageInfo;         //只接收客户端传来的图片data，当进行数据库mapping映射时不使用该参数

}

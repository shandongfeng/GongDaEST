package com.fengxiangkai.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductLoginParam {
    //phoneNumber和productInfoList的userId属性可以用于辨别身份，不存入product数据库
    private String phoneNumber;
    private ProductInfo productInfoList;
//    private String description;
//    private String type;
//    private String fineness;         //成色
//    private double originalPrice;     //原价
//    private double currentPrice;        //现价
//    private String address;          //地址
//    private String service;         //售后服务
//    private Date time;                       //发布时间
//    private String[] imageData;     //嫌它太大，最后发送

//    public static ProductInfo toProductInfo(ProductLoginParam param)
//    {
//        ProductInfo productInfo = new ProductInfo();
//        productInfo.setUserId(param.userId);
//        productInfo.setDescription(param.description);
//        productInfo.setType(param.type);
//        productInfo.setFineness(param.fineness);
//        productInfo.setOriginalPrice(param.originalPrice);
//        productInfo.setCurrentPrice(param.currentPrice);
//        productInfo.setAddress(param.address);
//        productInfo.setService(param.service);
//        productInfo.setTime(param.time);
//        //productInfo中不存储图片数据而是图片在本地的存储路径，所有调用toProductInfo方法之前需要先存储图片到本地
//        //这里虽然写getImageData但实际上是获取的图片路径
//        productInfo.setImagePath(param.getImageData()[0]);
//        return productInfo;
//    }
}

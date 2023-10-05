package com.fengxiangkai.service;

import com.fengxiangkai.pojo.ProductInfo;

public interface ProductInfoService {
    //插入信息，成功则返回商品在数据库的id
    int addInfo(ProductInfo info,String imageInfo);
}

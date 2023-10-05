package com.fengxiangkai.service;

import com.fengxiangkai.pojo.ProductItemResult;

public interface GetProductItemInfo {
    //查询长度
    int selectDBLength();
    //获取指定范围内的商品信息
    ProductItemResult[] getProductItemInfo(int beginIndex,int endIndex);
}

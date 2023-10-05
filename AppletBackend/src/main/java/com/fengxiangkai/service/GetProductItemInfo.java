package com.fengxiangkai.service;

import com.fengxiangkai.pojo.ProductItemResult;

public interface GetProductItemInfo {
    int selectDBLength();
    ProductItemResult[] getProductItemInfo(int beginIndex,int endIndex);
}

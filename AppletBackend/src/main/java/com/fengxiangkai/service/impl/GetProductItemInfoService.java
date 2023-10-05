package com.fengxiangkai.service.impl;

import com.fengxiangkai.mapper.GetProductInfoMapper;
import com.fengxiangkai.pojo.ProductItemResult;
import com.fengxiangkai.service.GetProductItemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetProductItemInfoService implements GetProductItemInfo {
    @Autowired
    private GetProductInfoMapper getProductInfoMapper;
    //获取数据库长度
    public int selectDBLength()
    {
        return getProductInfoMapper.selectDBLength();
    }
    //获取数据库信息
    public ProductItemResult[] getProductItemInfo(int beginIndex,int endIndex)
    {
        return getProductInfoMapper.getInfo(beginIndex,endIndex);
    }
}

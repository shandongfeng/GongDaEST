package com.fengxiangkai.service.impl;

import com.fengxiangkai.mapper.ProductInfoMapper;
import com.fengxiangkai.pojo.ProductInfo;
import com.fengxiangkai.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Override
    public int addInfo(ProductInfo info,String imageInfo)
    {
        return productInfoMapper.addInfo(info,imageInfo);
    }
}

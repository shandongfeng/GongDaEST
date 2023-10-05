package com.fengxiangkai.controller;

import com.fengxiangkai.pojo.ProductLoginParam;
import com.fengxiangkai.pojo.ResultType;
import com.fengxiangkai.service.ProductInfoService;
import com.fengxiangkai.utils.ImageHandleUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ProductLoginController {
    @Autowired
    private ProductInfoService productInfoService;
    @PostMapping("/register/product")
    @ResponseBody
    public ResultType<Integer> registerProduct(@RequestBody ProductLoginParam param)
    {
        /**
         * 待实现——验证上传商品信息的用户身份
         * 1.先根据param中的手机号selectDB判断用户是否已注册
         * 2.只要返回的user_id跟param中的userId一致，则证明用户存在，可以存储商品信息param
         */
        System.out.println("处理前的请求体 = " + param);
        //对请求体中的图片数据进行处理，先保存到本地，然后存储到数据库
        int len = param.getProductInfoList().getImageInfo().length;
        String linkedImagePath = "";
        for(int i=0;i<len;++i)
        {
            linkedImagePath += ImageHandleUtil.uploadImageHandle("image",param.getPhoneNumber(),param.getProductInfoList().getImageInfo()[i]);
            linkedImagePath += ",";
        }
        //param.getProductInfoList().getImagePath()[0] = linkedImagePath;
        //ProductInfo productInfo = ProductLoginParam.toProductInfo(param);
        //System.out.println("处理后的请求体 = " + productInfo);
        int product_id = productInfoService.addInfo(param.getProductInfoList(),linkedImagePath);
        //System.out.println("发布的新商品在数据库的第" +  + "行");
        return ResultType.success(product_id);
    }
}

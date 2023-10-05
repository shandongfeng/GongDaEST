package com.fengxiangkai.appletbackend;

import com.fengxiangkai.pojo.ProductItemResult;
import com.fengxiangkai.service.GetProductItemInfo;
import com.fengxiangkai.service.impl.GetProductItemInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest
class AppletBackendApplicationTests {

    //@Autowired
    private GetProductItemInfo getProductItemInfo;
    @Test
    void contextLoads() {
        getProductItemInfo = new GetProductItemInfoService();
        System.out.println(getProductItemInfo.selectDBLength());;

    }

}

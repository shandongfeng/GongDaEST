package com.fengxiangkai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan //开启了对servlet组件的支持
public class AppletBackend {
    public static void main(String[] args) {
        SpringApplication.run(AppletBackend.class, args);
    }
}

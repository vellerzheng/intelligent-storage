package com.mcloud.filelocalbk.conf;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 13:17 2018/6/4
 * @Modify By:
 */
@Configuration
public class FileConfig {
    @Bean
    MultipartConfigElement createMultipartConfigElement()
    {
        MultipartConfigFactory mcf = new MultipartConfigFactory();
        /**
         * 设置最大上传文件的大小，默认是10MB
         */
        mcf.setMaxFileSize("102400MB");
        mcf.setMaxRequestSize("102400MB");
        return mcf.createMultipartConfig();
    }
}

package com.club.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 项目启动类
 *
 * @author Tom
 * @date 2019-11-16
 */
@SpringBootApplication
public class BusinessApplication /*extends SpringBootServletInitializer*/ {

    public static void main(String[] args) {
        SpringApplication.run(BusinessApplication.class, args);
    }

    /**
     * 1.项目使用jsp页面需要将启动类添加如下方法
     * 2.启动类需要继承SpringBootServletInitializer并重写其configure方法
     * 2.然后将pom文件改为<packaging>war</packaging>
     * 3.项目正常打包mvn clean package  生成war包
     * 4.用java -jar 执行你的war包即可
     */
//    @Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(BusinessApplication.class);
//	}

    /**
     * 设置匹配*.do后缀请求
     * @param dispatcherServlet
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean<DispatcherServlet> servletServletRegistrationBean = new ServletRegistrationBean<>(dispatcherServlet);
        servletServletRegistrationBean.addUrlMappings(new String[]{"*.do","/business"});
        return servletServletRegistrationBean;
    }

    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        //resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setResolveLazily(true);
        resolver.setMaxInMemorySize(40960);
        //上传文件大小 50M 50*1024*1024
        resolver.setMaxUploadSize(50*1024*1024);
        return resolver;
    }
}

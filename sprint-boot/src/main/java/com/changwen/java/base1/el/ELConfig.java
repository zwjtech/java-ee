package com.changwen.java.base1.el;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * Spring EL-Spring表达语言，
 * @author changwen on 2017/7/18.
 */
@Configurable
@ComponentScan("com.changwen.java.base1.el")
@PropertySource("classpath:el/test.properties")  // 注入配置文件需要使用这个注解来指定文件地址
public class ELConfig {

    // 1.注入普通字符串
    @Value("I love you!")
    private String normal;

    // 2.注入操作系统属性
    @Value("#{systemProperties['os.name']}")
    private String osName;

    // 3.注入表达式结果
    @Value("#{T(java.lang.Math).random() * 100.0}")
    private double randomNumber;

    // 4.注入其他Bean属性
    @Value("#{demoService.another}")
    private String fromAnother;

    // 5.注入文件资源
    @Value("classpath:el/test.txt")
    private Resource testFile;

    // 6.注入网址资源
    @Value("http://www.baidu.com")
    private Resource testUrl;

    // 7.注入配置文件, 这个用的很多
    @Value("${book.name}")
    private String bookName;


    @Autowired
    private Environment environment;

    // 若使用@Value注入，则要配置一个PropertySourcesPlaceholderConfigurer类的Bean，实际也不一定会用
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigure() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public void outputResource() {
        try {
            System.out.println("normal-->  " + normal);
            System.out.println("osName-->  " + osName);
            System.out.println("randomNumber-->  " + randomNumber);
            System.out.println("fromAnother-->  " + fromAnother);

            System.out.println("testFile-->  " + IOUtils.toString(testFile.getInputStream()));
            System.out.println("testUrl-->  " + IOUtils.toString(testUrl.getInputStream()));
            System.out.println("bookName-->  " + bookName);

            System.out.println("environment-->  " + environment.getProperty("book.author"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.changwen.java.base1.spring_aware;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Spring Aware的目的是为了让Bean获得Spring容器的服务。
 * 因为ApplicationContext接口集成了MessageSource接口、ApplicationEventPublisher接口和ResourceLoader接口，
 * 所以Bean继承ApplicationContextAware可以获得Spring容器的所有服务，但原则上我们还是用到什么接口，就实现什么接口。
 * @author changwen on 2017/7/20.
 */
@Service
public class AwareService implements BeanNameAware, ResourceLoaderAware {
    private String beanName;
    private ResourceLoader resourceLoader;

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void outputResult() throws IOException {
        System.out.println("Bean 的名称：" + beanName);
        Resource resource = resourceLoader.getResource("classpath:spring_aware/test.txt");
        System.out.println("ResourceLoader加载的文件内容为：" + IOUtils.toString(resource.getInputStream()));
    }
}

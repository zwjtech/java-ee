package com.changwen.java.spring_cloud;

import org.junit.Test;

/**
 * 1.微服务的含义：使用定义好边界的小的独立组件来做好一件事情。微服务是相对于传统单块式架构而言的。
 * 单块式架构：是一份代码，部署和伸缩都是基于单个单元进行的。
 *      优点：易于部署
 *      缺点：可用性低、可伸缩性差、集中发布的生命周期以及违反单一功能原则
 * 微服务：解决了单块式架构的缺点，它以单个独立的服务来做一个功能，且要做好这个功能。
 *        但不可避免地将功能按照边界拆分为单个服务，体现出分布式的特征，这时每个微服务之间的通信将是我们要解决的问题。
 * Spring Cloud基于Spring Boot，特别适合在Docker或其他专业Paas(平台即服务)部署，
 *     它为我们提供了配置管理、服务发现、断路器、代理服务等我们在做分布式开发时常用问题的解决方案。
 *
 * 2.各种服务
 * 1.配置服务：提供了Config Server，它有在分页式系统开发中外部配置的功能。通过Config Server，我们可以集中存储所有应用的配置文件 。
 * 2.服务发现：通过Netflix OSS的Eureka来实现服务发现，目的是为了让每个服务之前可以互相通信。Eureka为微服务注解中心
 *            使用注解方式提供了Eureka服务端(@EnableEurekaServer)和客户端(@EnableEurekaClient)
 * 3.路由网关：主要目的是为了让所有的微服务对外只有一个接口，我们只需访问一个网关地址，即可由网关将我们的请求代理到不同的服务器中。
 *            通过Zuul来实现的，支持自动路由器映射到在Eureka Server上注册的服务。提供了注解@EnableZuulProxy来启用路由代理。
 * 4.负载均衡：提供了Ribbon和Feign作为客户端的负载均衡。
 *            在Spring Cloud下，使用Ribbon直接注入一个RestTemplate对象即可，此RestTemplate已做好负载均衡的配置；
 *            使用Feign只需定义个注解，有@FeignClient注解的接口，然后使用@RequestMapping注解在方法上映射远程的REST服务，此方法也是做好负载均衡配置的。
 * 5.断路器 ： (Circuit Breaker)，主要是为了解决当某个方法调用失败的时候，调用后备方法来替代失败的方法，以达到容错、阻止级联错误等功能。
 *           Spring Cloud使用@EnableCircuitBreaker来启用断路器支持，使用@HystrixCommand的fallbackMethod来指定后备方法。
 *           通过@EnableHystrixDashboard注解开启一个控制台来监控断路器的运行情况。
 * @author changwen on 2017/7/22.
 */
public class SpringCloudTest {
    @Test
    public void test() {

    }
}

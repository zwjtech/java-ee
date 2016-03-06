package Spring4_IOC.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * HelloWorldSpringIOC
 *
 * @author Lcw 2015/11/12
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans-generic.xml");
        UserService userService = (UserService) ctx.getBean("userService");
        System.out.println(userService);
        userService.add();
    }
}

package Spring4_IOC.annotation.controler;

import Spring4_IOC.annotation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author Administrator on 2016/3/6.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    public void execute() {
        System.out.println("UserController execute...");
        userService.add();
    }
}

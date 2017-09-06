package Spring4_IOC.di;

import Spring4_IOC.bean.User;
import org.springframework.stereotype.Service;

/**
 * UserServer
 *
 * @author Lcw 2015/11/12
 */
@Service("userServiceTest")
public class UserService extends BaseService<User> {
}

package Spring4_IOC.annotation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Spring4_IOC.annotation.repository.UserRepository;

/**
 * UserService
 *
 * @author Lcw 2015/11/12
 */
@Service("userService")
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    public void add() {
        System.out.println("UserService add...");
        userRepository.save();
    }
}

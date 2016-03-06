package Spring4_IOC.annotation.repository;

import org.springframework.stereotype.Repository;

/**
 * UserRepositoryImpl
 *
 * @author Lcw 2015/11/12
 */
//接口实现一般用接口名
@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {
    public void save() {
        System.out.println("UserRepository Save...");

    }
}

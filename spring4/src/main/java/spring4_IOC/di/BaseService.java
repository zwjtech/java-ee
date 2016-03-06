package Spring4_IOC.di;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * BaseService
 *
 * @author Lcw 2015/11/12
 */
public class BaseService<T> {

    @Autowired
    protected BaseRepository<T> repository;

    public void add() {
        System.out.println("add...");
        System.out.println(repository);
    }
}

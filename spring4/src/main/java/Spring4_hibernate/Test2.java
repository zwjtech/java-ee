package Spring4_hibernate;


import Spring4_hibernate.service.BookShopService;
import Spring4_hibernate.service.Cashier;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;


/**
 * Test2
 *
 * @author lcw 2015/11/14
 */
public class Test2 {
    private ApplicationContext ctx = null;
    private BookShopService bookShopService = null;
    private Cashier cashier = null;

    {
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        bookShopService = ctx.getBean(BookShopService.class);
        cashier = ctx.getBean(Cashier.class);
    }

    @Test
    public void testCashier(){
        //cashier.checkout("aa", Arrays.asList("1001", "1002"));
    }

    @Test
    public void testBookShopService(){
        bookShopService.purchase("aa", "1001");
    }

    @Test
    public void testDataSource() throws SQLException {
        DataSource dataSource = ctx.getBean(DataSource.class);
        System.out.println(dataSource.getConnection());
    }
}

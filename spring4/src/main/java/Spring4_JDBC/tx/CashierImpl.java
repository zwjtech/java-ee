package Spring4_JDBC.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * CashierImpl
 *
 * @author Lcw 2015/11/14
 */
@Service("cashier")
public class CashierImpl implements Cashier {

    @Autowired
    private BookShopService bookShopService;

    @Transactional
    @Override
    public void checkout(String username, List<String> isbns) {
        for(String isbn: isbns){
            bookShopService.purchase(username, isbn);
        }
    }

}
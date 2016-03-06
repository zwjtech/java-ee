package Spring4_hibernate.service.impl;


import Spring4_hibernate.service.BookShopService;
import Spring4_hibernate.service.Cashier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CashierImpl
 *
 * @author lcw 2015/11/16
 */
@Service
public class CashierImpl implements Cashier {

    @Autowired
    private BookShopService bookShopService;

    @Override
    public void checkout(String username, List<String> isbns) {
        for(String isbn:isbns){
            bookShopService.purchase(username, isbn);
        }
    }

}
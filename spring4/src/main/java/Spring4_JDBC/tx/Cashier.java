package Spring4_JDBC.tx;

import java.util.List;

/**
 * Cashier
 *
 * @author Lcw 2015/11/14
 */
public interface Cashier {

    public void checkout(String username, List<String> isbns);

}

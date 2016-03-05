package Spring4_IOC.bean;

import java.util.Properties;

/**
 * DataSource: 这个类模拟com.mchange.v2.c3p0.ComboPooledDataSource
 *
 * @author Lcw 2015/11/10
 */
public class DataSource {

    private Properties properties;

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "DataSource{" +
                "properties=" + properties +
                '}';
    }


}

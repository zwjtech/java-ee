package com.changwen.java.base1.Conditional;

import com.changwen.java.base1.Conditional.ConditionVO.LinuxCondition;
import com.changwen.java.base1.Conditional.ConditionVO.WindowsCondition;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;

/**
 * @author changwen on 2017/7/21.
 */
@Configurable
public class ConditionConfig {

    @Bean
    @Conditional(WindowsCondition.class)
    public ListService windowsListService() {
        return new WindowsListService();
    }

    @Bean
    @Conditional(LinuxCondition.class)
    public ListService linuxListService() {
        return new LinuxListService();
    }
}

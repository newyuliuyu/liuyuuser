package com.liuyu;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * ClassName: SpringContextUtil2 <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-3-26 下午3:56 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */

public class SpringContextUtil2 implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public SpringContextUtil2() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext = applicationContext;

    }

    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class clazz) {
        return (T) applicationContext.getBean(clazz);
    }
}

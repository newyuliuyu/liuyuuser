package com.liuyu.bs.business;

import org.springframework.beans.BeanUtils;

/**
 * ClassName: OrgHelper <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-11 下午2:20 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class OrgHelper {


    public static boolean isSchool(Org org) {
        return org.getDeep() == 4;
    }

    public static boolean isCounty(Org org) {
        return org.getDeep() == 3;
    }

    public static boolean isCity(Org org) {
        return org.getDeep() == 2;
    }

    public static boolean isProvince(Org org) {
        return org.getDeep() == 1;
    }

    public static <T> T converTo(Org org, Class<T> tClass) {
        T obj = BeanUtils.instantiateClass(tClass);
        BeanUtils.copyProperties(org, obj);
        return obj;
    }
}

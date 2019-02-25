package com.liuyu.bs.service;

import com.liuyu.bs.business.ClazzMaseter;

import java.util.List;

/**
 * ClassName: ClazzMasterService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-16 下午5:24 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface ClazzMasterService {
    ClazzMaseter get(String orgCode,String clazzCode);

    List<ClazzMaseter> queryClazzMasters(String orgCode);
}

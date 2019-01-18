package com.liuyu.bs.service;

import com.liuyu.bs.business.GradeMaseter;

import java.util.List;

/**
 * ClassName: LPGroupLeaderService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-18 上午10:54 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface GradeMaseterService {

    void adds(long roleId, List<GradeMaseter> gradeMaseters);

    void delete(long roleId, GradeMaseter gradeMaseter);

    List<GradeMaseter> queryGradeMaseter(String orgCode);
}

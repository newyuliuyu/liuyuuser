package com.liuyu.bs.service;

import com.liuyu.bs.business.TARS;

import java.util.List;

/**
 * ClassName: SchoolMaseterService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-17 下午3:20 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface TARSService {

    void adds(List<TARS> tarsList);

    void delete(TARS tars);

    List<TARS> queryTARS(String orgCode, long roleId);
}

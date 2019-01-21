package com.liuyu.bs.service;

import com.liuyu.bs.business.SFE;

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
public interface SFEService {

    void adds(List<SFE> sfes);

    void delete(SFE sfe);

    List<SFE> querySFE(String orgCode, long roleId);
}

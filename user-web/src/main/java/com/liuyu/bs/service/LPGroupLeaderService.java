package com.liuyu.bs.service;

import com.liuyu.bs.business.LPGroupLeader;

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
public interface LPGroupLeaderService {

    void adds(long roleId, List<LPGroupLeader> lpGroupLeaders);

    void delete(long roleId, LPGroupLeader lpGroupLeader);

    List<LPGroupLeader> queryLPGroupLeader(String orgCode);
}

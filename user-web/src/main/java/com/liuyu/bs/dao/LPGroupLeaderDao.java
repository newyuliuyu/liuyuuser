package com.liuyu.bs.dao;

import com.liuyu.bs.business.LPGroupLeader;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: TeachingTeacherDao <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-16 下午3:10 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Repository
public interface LPGroupLeaderDao {
    int add(@Param("lpGroupLeader") LPGroupLeader lpGroupLeader);

    int adds(@Param("lpGroupLeaders") List<LPGroupLeader> lpGroupLeaders);

    int delete(@Param("lpGroupLeader") LPGroupLeader lpGroupLeader);

    List<LPGroupLeader> queryLPGroupLeaders(@Param("orgCode") String orgCode);

    boolean teacheHasLPGroupLeader(@Param("teacherCode") String teacherCode);
}

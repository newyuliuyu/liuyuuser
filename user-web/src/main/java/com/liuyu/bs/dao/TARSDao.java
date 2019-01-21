package com.liuyu.bs.dao;

import com.liuyu.bs.business.TARS;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: ClazzMasterDao <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-16 下午2:51 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Repository
public interface TARSDao {
    int add(@Param("tars") TARS tars);

    int adds(@Param("tarsList") List<TARS> tarsList);

    int delete(@Param("tars") TARS tars);

    List<TARS> queryTARS(@Param("orgCode") String orgCode,
                          @Param("roleId") long roleId);

    boolean teacheHasTARS(@Param("teacherCode") String teacherCode, @Param("roleId") long roleId);


}

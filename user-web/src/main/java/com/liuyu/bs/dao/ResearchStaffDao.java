package com.liuyu.bs.dao;

import com.liuyu.bs.business.ResearchStaff;
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
public interface ResearchStaffDao {
    int add(@Param("researchStaff") ResearchStaff researchStaff);

    int adds(@Param("researchStaffs") List<ResearchStaff> researchStaffs);

    int delete(@Param("researchStaff") ResearchStaff researchStaff);

    List<ResearchStaff> queryResearchStaff(@Param("orgCode") String orgCode,
                                           @Param("roleId") long roleId);

    boolean teacheHasResearchStaff(@Param("teacherCode") String teacherCode,
                                   @Param("roleId") long roleId);

}

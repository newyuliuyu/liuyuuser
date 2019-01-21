package com.liuyu.bs.service;

import com.liuyu.bs.business.ResearchStaff;

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
public interface ResearchStaffService {

    void adds(List<ResearchStaff> researchStaffs);

    void delete(ResearchStaff researchStaff);

    List<ResearchStaff> queryResearchStaff(String orgCode, long roleId);
}

package com.liuyu.bs.service;

import com.liuyu.bs.business.ClazzMaseter;
import com.liuyu.bs.business.TeachingTeacher;

/**
 * ClassName: TeachingService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-16 下午4:45 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */

public interface TeachingService {

    void addClazzMaster(long roleId, ClazzMaseter old, ClazzMaseter now);

    void removeClazzMaster(long roleId, ClazzMaseter clazzMaster);

    void addTeachingTeacher(long roleId, TeachingTeacher old, TeachingTeacher now);

    void removeTeachingTeacher(long roleId, TeachingTeacher teachingTeacher);
}

package com.liuyu.bs.service;

import com.liuyu.bs.business.TeachingTeacher;

import java.util.List;

/**
 * ClassName: TeachingTeacherService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-17 上午11:19 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface TeachingTeacherService {
    List<TeachingTeacher> queryTeachingTeacher(String orgCode);

    List<TeachingTeacher> queryTeachingTeacherOfClazz(String orgCode, String clazzCode);
}

package com.liuyu.bs.service;

import com.liuyu.bs.business.Teacher;

import java.util.List;

/**
 * ClassName: TeacherService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-14 上午10:17 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface TeacherService {

    void add(Teacher teacher);

    void adds(List<Teacher> teachers);

    void update(Teacher teacher);

    void delete(String teacherCode);

    Teacher get(String teacherCode);

    List<Teacher> queryTeacher(String orgCode, String teacherName);
}

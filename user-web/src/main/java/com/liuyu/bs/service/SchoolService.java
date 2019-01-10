package com.liuyu.bs.service;

import com.liuyu.bs.business.Clazz;
import com.liuyu.bs.business.Grade;
import com.liuyu.bs.business.Subject;

import java.util.List;

/**
 * ClassName: SchoolService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-9 下午1:13 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface SchoolService {

    void addSubject(String code, Subject subject);

    void delSubject(long subjectId);

    List<Subject> querySubjects(String code);

    void addGrade(String code, Grade grade);

    void delGrade(long gradeId);

    List<Grade> queryGrade(String code);

    void addClazz(Clazz clazz);

    void delClazz(String clazzCode);

    List<Clazz> queryClazz(String code);


    void aKeyConfigSchool(String code,
                          boolean hasPrimarySchool,
                          boolean hasJuniorHighSchool,
                          boolean hasHighSchool);


}

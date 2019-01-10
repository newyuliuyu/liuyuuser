package com.liuyu.bs.dao;

import com.liuyu.bs.business.Subject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: SubjectDao <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-9 下午2:41 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Repository
public interface SchoolSubjectDao {

    int add(@Param("code") String code, @Param("subject") Subject subject);

    int adds(@Param("code") String code, @Param("subjects") List<Subject> subjects);

    int del(@Param("id") long subjectId);

    List<Subject> querySubjects(@Param("code") String code);
}

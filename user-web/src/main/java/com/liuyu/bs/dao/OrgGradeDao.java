package com.liuyu.bs.dao;

import com.liuyu.bs.business.Grade;
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
public interface OrgGradeDao {

    int add(@Param("code") String code, @Param("grade") Grade garde);

    int adds(@Param("code") String code, @Param("grades") List<Grade> gardes);

    int del(@Param("id") long gradeId);

    List<Grade> queryGrades(@Param("code") String code);
}

package com.liuyu.bs.dao;

import com.liuyu.bs.business.TeachingTeacher;
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
public interface TeachingTeacherDao {
    int add(@Param("teachingTeacher") TeachingTeacher teachingTeacher);

    int adds(@Param("teachingTeachers") List<TeachingTeacher> teachingTeachers);

    int delete(@Param("teachingTeacher") TeachingTeacher teachingTeacher);

    List<TeachingTeacher> queryTeachingTeacher(@Param("orgCode") String orgCode);
    List<TeachingTeacher> queryTeachingTeacherOfClazz(@Param("orgCode") String orgCode,
                                                      @Param("clazzCode") String clazzCode);

    boolean teacheHasTeachingTeacher(@Param("teacherCode") String teacherCode);
}

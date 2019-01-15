package com.liuyu.bs.dao;

import com.liuyu.bs.business.Teacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: TeacherDao <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-14 上午10:14 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Repository
public interface TeacherDao {
    int add(@Param("teacher") Teacher teacher);

    int adds(@Param("teachers") List<Teacher> teachers);

    int update(@Param("teacher") Teacher teacher);

    int delete(@Param("teacher") Teacher teacher);

    Teacher get(@Param("code") String code);

    List<Teacher> queryListWithOrgCode(@Param("orgCode") String orgCode,
                                       @Param("name") String teacherName);
}

package com.liuyu.bs.dao;

import com.liuyu.bs.business.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: StudentDao <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-23 上午9:11 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Repository
public interface StudentDao {
    int add(@Param("student") Student student);

    int adds(@Param("students") List<Student> students);

    int update(@Param("student") Student student);

    int delete(@Param("student") Student student);

    Student get(@Param("id") long id);

    List<Student> queryListWithClazz(@Param("orgCode") String orgCode,
                                     @Param("clazzCode") String clazzCode);

}

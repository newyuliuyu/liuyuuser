package com.liuyu.bs.service;

import com.liuyu.bs.business.Student;

import java.util.List;

/**
 * ClassName: StudentService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-23 上午9:33 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface StudentService {
    void add(Student student);

    void adds(List<Student> students);

    void update(Student student);

    void delete(Student student);

    Student get(long id);

    List<Student> queryListWithClazz(String schoolCode, String clazzCode);
}

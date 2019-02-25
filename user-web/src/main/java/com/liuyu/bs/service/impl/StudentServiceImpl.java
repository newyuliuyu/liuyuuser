package com.liuyu.bs.service.impl;

import com.liuyu.bs.business.Student;
import com.liuyu.bs.dao.StudentDao;
import com.liuyu.bs.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: StudentServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-23 上午9:34 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;


    @Override
    @Transactional
    public void add(Student student) {
        studentDao.add(student);
    }

    @Override
    @Transactional
    public void adds(List<Student> students) {
        studentDao.adds(students);
    }

    @Override
    @Transactional
    public void update(Student student) {
        studentDao.update(student);
    }

    @Override
    @Transactional
    public void delete(Student student) {
        studentDao.delete(student);
    }

    @Override
    public Student get(long id) {
        return studentDao.get(id);
    }

    @Override
    public List<Student> queryListWithClazz(String schoolCode, String clazzCode) {
        return studentDao.queryListWithClazz(schoolCode, clazzCode);
    }
}

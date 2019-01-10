package com.liuyu.bs.service.impl;

import com.google.common.collect.Lists;
import com.liuyu.bs.business.Clazz;
import com.liuyu.bs.business.Grade;
import com.liuyu.bs.business.Subject;
import com.liuyu.bs.dao.ClazzDao;
import com.liuyu.bs.dao.SchoolDao;
import com.liuyu.bs.dao.SchoolGradeDao;
import com.liuyu.bs.dao.SchoolSubjectDao;
import com.liuyu.bs.service.SchoolService;
import com.liuyu.common.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: SchoolServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-9 下午1:14 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private SchoolSubjectDao schoolSubjectDao;

    @Autowired
    private SchoolGradeDao schoolGradeDao;

    @Autowired
    private ClazzDao clazzDao;

    @Autowired
    private IdGenerator idGenerator;

    @Override
    @Transactional
    public void addSubject(String code, Subject subject) {
        schoolSubjectDao.add(code, subject);
    }

    @Override
    @Transactional
    public void delSubject(long subjectId) {
        schoolSubjectDao.del(subjectId);
    }

    @Override
    public List<Subject> querySubjects(String code) {
        return schoolSubjectDao.querySubjects(code);
    }

    @Override
    @Transactional
    public void addGrade(String code, Grade grade) {
        grade.setId(idGenerator.nextId());
        schoolGradeDao.add(code, grade);
    }

    @Override
    @Transactional
    public void delGrade(long gradeId) {
        schoolGradeDao.del(gradeId);
        clazzDao.delGradeClazz(gradeId);
    }

    @Override
    public List<Grade> queryGrade(String code) {
        return schoolGradeDao.queryGrades(code);
    }

    @Override
    @Transactional
    public void addClazz(Clazz clazz) {
        clazzDao.add(clazz);
    }

    @Override
    @Transactional
    public void delClazz(String clazzCode) {
        clazzDao.del(clazzCode);
    }

    @Override
    public List<Clazz> queryClazz(String code) {
        return clazzDao.queryClazzesWithSchoolCode(code);
    }

    @Override
    @Transactional
    public void aKeyConfigSchool(String code,
                                 boolean hasPrimarySchool,
                                 boolean hasJuniorHighSchool,
                                 boolean hasHighSchool) {

        List<Subject> subjects = Lists.newArrayList();
        subjects.add(Subject.builder().name("语文").build());
        subjects.add(Subject.builder().name("英语").build());
        subjects.add(Subject.builder().name("数学").build());
        subjects.add(Subject.builder().name("物理").build());
        subjects.add(Subject.builder().name("化学").build());
        subjects.add(Subject.builder().name("生物").build());
        subjects.add(Subject.builder().name("政治").build());
        subjects.add(Subject.builder().name("历史").build());
        subjects.add(Subject.builder().name("地理").build());

        List<Grade> grades = Lists.newArrayList();
        if (hasPrimarySchool) {
            grades.add(Grade.createGrade("一年级", idGenerator.nextId()));
            grades.add(Grade.createGrade("二年级", idGenerator.nextId()));
            grades.add(Grade.createGrade("三年级", idGenerator.nextId()));
            grades.add(Grade.createGrade("四年级", idGenerator.nextId()));
            grades.add(Grade.createGrade("五年级", idGenerator.nextId()));
            grades.add(Grade.createGrade("六年级", idGenerator.nextId()));

        }
        if (hasJuniorHighSchool) {
            grades.add(Grade.createGrade("初一", idGenerator.nextId()));
            grades.add(Grade.createGrade("初二", idGenerator.nextId()));
            grades.add(Grade.createGrade("初三", idGenerator.nextId()));
        }
        if (hasHighSchool) {
            grades.add(Grade.createGrade("高一", idGenerator.nextId()));
            grades.add(Grade.createGrade("高二", idGenerator.nextId()));
            grades.add(Grade.createGrade("高三", idGenerator.nextId()));
        }

        schoolSubjectDao.adds(code, subjects);
        schoolGradeDao.adds(code, grades);
    }
}

package com.liuyu.bs.service.impl;

import com.liuyu.bs.business.TeachingTeacher;
import com.liuyu.bs.dao.TeachingTeacherDao;
import com.liuyu.bs.service.TeachingTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: TeachingTeacherServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-17 上午11:19 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
public class TeachingTeacherServiceImpl implements TeachingTeacherService {

    @Autowired
    private TeachingTeacherDao teachingTeacherDao;

    @Override
    public List<TeachingTeacher> queryTeachingTeacher(String orgCode) {
        return teachingTeacherDao.queryTeachingTeacher(orgCode);
    }

    @Override
    public List<TeachingTeacher> queryTeachingTeacherOfClazz(String orgCode, String clazzCode) {
        return teachingTeacherDao.queryTeachingTeacherOfClazz(orgCode, clazzCode);
    }
}

package com.liuyu.bs.service.impl;

import com.liuyu.bs.business.ClazzMaseter;
import com.liuyu.bs.business.Teacher;
import com.liuyu.bs.business.TeachingTeacher;
import com.liuyu.bs.dao.ClazzMasterDao;
import com.liuyu.bs.dao.TeachingTeacherDao;
import com.liuyu.bs.service.TeachingService;
import com.liuyu.user.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClassName: TeachingServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-16 下午5:06 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
public class TeachingServiceImpl implements TeachingService {

    @Autowired
    private UserService userService;
    @Autowired
    private ClazzMasterDao clazzMasterDao;
    @Autowired
    private TeachingTeacherDao teachingTeacherDao;


    @Override
    @Transactional
    public void removeClazzMaster(long roleId, ClazzMaseter clazzMaster) {
        clazzMasterDao.delete(clazzMaster);
        if (!hasClazzMaster(clazzMaster.getTeacher())) {
            userService.deleteRoleWithAccount(clazzMaster.getTeacher().getAccount(), roleId);
        }
    }

    @Override
    @Transactional
    public void addClazzMaster(long roleId, ClazzMaseter old, ClazzMaseter now) {
        if (old != null) {
            clazzMasterDao.delete(old);
            if (!hasClazzMaster(old.getTeacher())) {
                userService.deleteRoleWithAccount(old.getTeacher().getAccount(), roleId);
            }
        }

        if (!hasClazzMaster(now.getTeacher())) {
            userService.addRoleWithAccount(now.getTeacher().getAccount(), roleId);
        }
        clazzMasterDao.add(now);
    }

    private boolean hasClazzMaster(Teacher teacher) {
        return clazzMasterDao.teacheHasClazzMaster(teacher.getCode());
    }

    @Override
    @Transactional
    public void addTeachingTeacher(long roleId, TeachingTeacher old, TeachingTeacher now) {
        if (old != null) {
            teachingTeacherDao.delete(old);
            if (!teachingTeacherDao.teacheHasTeachingTeacher(old.getTeacher().getCode())) {
                userService.deleteRoleWithAccount(old.getTeacher().getAccount(), roleId);
            }
        }

        if (!teachingTeacherDao.teacheHasTeachingTeacher(now.getTeacher().getCode())) {
            userService.addRoleWithAccount(now.getTeacher().getAccount(), roleId);
        }
        teachingTeacherDao.add(now);
    }

    @Override
    @Transactional
    public void removeTeachingTeacher(long roleId, TeachingTeacher teachingTeacher) {
        teachingTeacherDao.delete(teachingTeacher);
        if (!teachingTeacherDao.teacheHasTeachingTeacher(teachingTeacher.getTeacher().getCode())) {
            userService.deleteRoleWithAccount(teachingTeacher.getTeacher().getAccount(), roleId);
        }
    }
}

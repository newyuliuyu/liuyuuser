package com.liuyu.bs.service.impl;

import com.google.common.collect.Lists;
import com.liuyu.bs.business.Teacher;
import com.liuyu.bs.dao.TeacherDao;
import com.liuyu.bs.service.TeacherService;
import com.liuyu.common.util.IdGenerator;
import com.liuyu.user.web.domain.User;
import com.liuyu.user.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * ClassName: TeacherServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-14 上午10:18 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private UserService userService;
    @Autowired
    private IdGenerator idGenerator;

    @Override
    @Transactional
    public void add(Teacher teacher) {
        teacher.setCode(idGenerator.nextId() + "");
        teacherDao.add(teacher);
        User user = teacherConvertToUser(teacher);
        userService.add(user);
    }

    @Override
    @Transactional
    public void adds(List<Teacher> teachers) {
        teacherDao.adds(teachers);
        List<User> users = Lists.newArrayList();
        teachers.forEach(teacher -> {
            User user = teacherConvertToUser(teacher);
            users.add(user);
        });
        userService.adds(users);
    }

    private User teacherConvertToUser(Teacher teacher) {
        User user = User.builder()
                .account(teacher.getAccount())
                .phone(teacher.getPhone())
                .email(teacher.getEmail())
                .name(teacher.getName())
                .build();
        return user;
    }

    @Override
    @Transactional
    public void update(Teacher teacher) {
        teacherDao.update(teacher);
        User user = teacherConvertToUser(teacher);
        userService.updateWithAccount(user);
    }

    @Override
    @Transactional
    public void delete(String teacherCode) {
        Teacher teacher = get(teacherCode);
        teacherDao.delete(teacher);
        userService.delete(teacher.getAccount());
    }


    @Override
    public Teacher get(String teacherCode) {
        return teacherDao.get(teacherCode);
    }

    @Override
    public List<Teacher> queryTeacher(String orgCode, String teacherName) {
        if (StringUtils.isEmpty(teacherName)) {
            teacherName = null;
        } else {
            teacherName = "%" + teacherName + "%";
        }
        return teacherDao.queryListWithOrgCode(orgCode, teacherName);
    }
}

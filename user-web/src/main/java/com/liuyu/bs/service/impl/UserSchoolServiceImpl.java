package com.liuyu.bs.service.impl;

import com.github.pagehelper.PageHelper;
import com.liuyu.bs.business.School;
import com.liuyu.bs.dao.SchoolDao;
import com.liuyu.bs.service.UserSchoolService;
import com.liuyu.user.web.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * ClassName: UserSchoolServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-9 下午1:30 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
public class UserSchoolServiceImpl implements UserSchoolService {

    @Autowired
    private SchoolDao schoolDao;

    @Override
    public School queryUserSchool(User user) {
        School school = null;
        if (user.isSuperAdmin()) {
            PageHelper.startPage(0, 1);
            List<School> schools = schoolDao.querySchools(null, null, -1);
            if (!schools.isEmpty()) {
                school = schools.get(0);
            }
        } else {
            //TODO
        }

        return school;
    }

    @Override
    public List<School> queryUserSchools(User user, String schoolName) {
        if (StringUtils.isEmpty(schoolName)) {
            schoolName = null;
        } else {
            schoolName = "%" + schoolName + "%";
        }
        List<School> schools = null;
        if (user.isSuperAdmin()) {
            schools = schoolDao.querySchools(null, schoolName, -1);
        } else {
            //TODO
        }
        return schools;
    }
}

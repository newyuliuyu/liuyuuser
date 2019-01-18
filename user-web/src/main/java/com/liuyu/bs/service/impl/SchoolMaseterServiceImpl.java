package com.liuyu.bs.service.impl;

import com.liuyu.bs.business.SchoolMaseter;
import com.liuyu.bs.dao.SchoolMasterDao;
import com.liuyu.bs.service.SchoolMaseterService;
import com.liuyu.user.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: SchoolMaseterServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-17 下午3:21 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
public class SchoolMaseterServiceImpl implements SchoolMaseterService {

    @Autowired
    private SchoolMasterDao schoolMasterDao;
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void adds(List<SchoolMaseter> schoolMaseters) {
        for (SchoolMaseter schoolMaseter : schoolMaseters) {
            userService.addRoleWithAccount(schoolMaseter.getTeacher().getAccount(), schoolMaseter.getRoleId());
        }
        schoolMasterDao.adds(schoolMaseters);
    }

    @Override
    @Transactional
    public void delete(SchoolMaseter schoolMaseter) {
        userService.deleteRoleWithAccount(schoolMaseter.getTeacher().getAccount(), schoolMaseter.getRoleId());
        schoolMasterDao.delete(schoolMaseter);
    }

    @Override
    public List<SchoolMaseter> querySchoolMaseters(String orgCode, long roleId) {
        return schoolMasterDao.querySchoolMaster(orgCode, roleId);
    }
}

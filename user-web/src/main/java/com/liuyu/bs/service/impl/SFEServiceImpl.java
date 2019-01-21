package com.liuyu.bs.service.impl;

import com.liuyu.bs.business.SFE;
import com.liuyu.bs.dao.SFEDao;
import com.liuyu.bs.service.SFEService;
import com.liuyu.user.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: SEFServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-18 下午5:15 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
public class SFEServiceImpl implements SFEService {

    @Autowired
    private SFEDao sfeDao;
    @Autowired
    private UserService userService;

    @Override
    public void adds(List<SFE> sfes) {
        for (SFE sfe : sfes) {
            userService.addRoleWithAccount(sfe.getTeacher().getAccount(), sfe.getRoleId());
        }
        sfeDao.adds(sfes);
    }

    @Override
    public void delete(SFE sfe) {
        userService.deleteRoleWithAccount(sfe.getTeacher().getAccount(), sfe.getRoleId());
        sfeDao.delete(sfe);
    }

    @Override
    public List<SFE> querySFE(String orgCode, long roleId) {
        return sfeDao.querySFE(orgCode,roleId);
    }
}

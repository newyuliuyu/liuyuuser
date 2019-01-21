package com.liuyu.bs.service.impl;

import com.google.common.collect.Lists;
import com.liuyu.bs.business.ResearchStaff;
import com.liuyu.bs.dao.ResearchStaffDao;
import com.liuyu.bs.service.ResearchStaffService;
import com.liuyu.user.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: ResearchStaffServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-18 下午5:26 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
public class ResearchStaffServiceImpl implements ResearchStaffService {
    @Autowired
    private ResearchStaffDao researchStaffDao;
    @Autowired
    private UserService userService;

    @Override
    public void adds(List<ResearchStaff> researchStaffs) {
        List<String> accounts = Lists.newArrayList();
        researchStaffs.forEach(x -> {
            if (!researchStaffDao.teacheHasResearchStaff(x.getTeacher().getCode(), x.getRoleId())) {
                accounts.add(x.getTeacher().getAccount());
            }
        });

        researchStaffDao.adds(researchStaffs);
        if (!researchStaffs.isEmpty()) {
            long roleId = researchStaffs.get(0).getRoleId();
            userService.addRoleWithAccounts(accounts, roleId);
        }
    }

    @Override
    public void delete(ResearchStaff researchStaff) {
        researchStaffDao.delete(researchStaff);
        if (!researchStaffDao.teacheHasResearchStaff(researchStaff.getTeacher().getCode(), researchStaff.getRoleId())) {
            userService.deleteRoleWithAccount(researchStaff.getTeacher().getAccount(), researchStaff.getRoleId());
        }
    }

    @Override
    public List<ResearchStaff> queryResearchStaff(String orgCode, long roleId) {
        return researchStaffDao.queryResearchStaff(orgCode, roleId);
    }
}

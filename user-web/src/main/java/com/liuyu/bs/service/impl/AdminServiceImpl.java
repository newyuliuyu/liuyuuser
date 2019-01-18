package com.liuyu.bs.service.impl;

import com.liuyu.bs.business.Admin;
import com.liuyu.bs.dao.AdminDao;
import com.liuyu.bs.service.AdminService;
import com.liuyu.user.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: AdminServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-18 下午3:22 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;
    @Autowired
    private UserService userService;

    @Override
    public void adds(List<Admin> admins) {
        for (Admin admin : admins) {
            userService.addRoleWithAccount(admin.getTeacher().getAccount(), admin.getRoleId());
        }
        adminDao.adds(admins);

    }

    @Override
    public void delete(Admin admin) {
        userService.deleteRoleWithAccount(admin.getTeacher().getAccount(), admin.getRoleId());
        adminDao.delete(admin);
    }

    @Override
    public List<Admin> queryAdmins(String orgCode, long roleId) {
        return adminDao.queryAdmin(orgCode,roleId);
    }
}

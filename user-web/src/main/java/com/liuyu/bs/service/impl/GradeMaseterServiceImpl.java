package com.liuyu.bs.service.impl;

import com.google.common.collect.Lists;
import com.liuyu.bs.business.GradeMaseter;
import com.liuyu.bs.dao.GradeMaseterDao;
import com.liuyu.bs.service.GradeMaseterService;
import com.liuyu.user.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: GradeMaseterServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-18 下午1:59 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
public class GradeMaseterServiceImpl implements GradeMaseterService {

    @Autowired
    private GradeMaseterDao gradeMaseterDao;

    @Autowired
    private UserService userService;

    @Override
    public void adds(long roleId, List<GradeMaseter> gradeMaseters) {
        List<String> accounts = Lists.newArrayList();
        gradeMaseters.forEach(x -> {
            if (!gradeMaseterDao.teacheHasGradeMaseter(x.getTeacher().getCode())) {
                accounts.add(x.getTeacher().getAccount());
            }
        });
        gradeMaseterDao.adds(gradeMaseters);
        userService.addRoleWithAccounts(accounts, roleId);
    }

    @Override
    public void delete(long roleId, GradeMaseter gradeMaseter) {
        gradeMaseterDao.delete(gradeMaseter);
        if (!gradeMaseterDao.teacheHasGradeMaseter(gradeMaseter.getTeacher().getCode())) {
            userService.deleteRoleWithAccount(gradeMaseter.getTeacher().getAccount(), roleId);
        }
    }

    @Override
    public List<GradeMaseter> queryGradeMaseter(String orgCode) {
        return gradeMaseterDao.queryGradeMaseter(orgCode);
    }
}

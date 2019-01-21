package com.liuyu.bs.service.impl;

import com.google.common.collect.Lists;
import com.liuyu.bs.business.TARS;
import com.liuyu.bs.dao.TARSDao;
import com.liuyu.bs.service.TARSService;
import com.liuyu.user.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: TARSServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-18 下午5:18 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
public class TARSServiceImpl implements TARSService {
    @Autowired
    private TARSDao tarsDao;
    @Autowired
    private UserService userService;

    @Override
    public void adds(List<TARS> tarsList) {
        List<String> accounts = Lists.newArrayList();
        tarsList.forEach(x -> {
            if (!tarsDao.teacheHasTARS(x.getTeacher().getCode(), x.getRoleId())) {
                accounts.add(x.getTeacher().getAccount());
            }
        });

        tarsDao.adds(tarsList);
        if (!tarsList.isEmpty()) {
            long roleId = tarsList.get(0).getRoleId();
            userService.addRoleWithAccounts(accounts, roleId);
        }
    }

    @Override
    public void delete(TARS tars) {
        tarsDao.delete(tars);
        if (!tarsDao.teacheHasTARS(tars.getTeacher().getCode(), tars.getRoleId())) {
            userService.deleteRoleWithAccount(tars.getTeacher().getAccount(), tars.getRoleId());
        }
    }

    @Override
    public List<TARS> queryTARS(String orgCode, long roleId) {
        return tarsDao.queryTARS(orgCode, roleId);
    }
}

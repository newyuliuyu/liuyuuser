package com.liuyu.bs.service.impl;

import com.google.common.collect.Lists;
import com.liuyu.bs.business.LPGroupLeader;
import com.liuyu.bs.dao.LPGroupLeaderDao;
import com.liuyu.bs.service.LPGroupLeaderService;
import com.liuyu.user.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: LPGroupLeaderServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-18 上午10:57 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
public class LPGroupLeaderServiceImpl implements LPGroupLeaderService {

    @Autowired
    private LPGroupLeaderDao lpGroupLeaderDao;

    @Autowired
    private UserService userService;

    @Override
    public void adds(long roleId, List<LPGroupLeader> lpGroupLeaders) {
        List<String> accounts = Lists.newArrayList();
        lpGroupLeaders.forEach(x -> {
            if (!lpGroupLeaderDao.teacheHasLPGroupLeader(x.getTeacher().getCode())) {
                accounts.add(x.getTeacher().getAccount());
            }
        });
        lpGroupLeaderDao.adds(lpGroupLeaders);
        userService.addRoleWithAccounts(accounts, roleId);
    }

    @Override
    public void delete(long roleId, LPGroupLeader lpGroupLeader) {
        lpGroupLeaderDao.delete(lpGroupLeader);
        if (!lpGroupLeaderDao.teacheHasLPGroupLeader(lpGroupLeader.getTeacher().getCode())) {
            userService.deleteRoleWithAccount(lpGroupLeader.getTeacher().getAccount(), roleId);
        }
    }

    @Override
    public List<LPGroupLeader> queryLPGroupLeader(String orgCode) {
        return lpGroupLeaderDao.queryLPGroupLeaders(orgCode);
    }
}

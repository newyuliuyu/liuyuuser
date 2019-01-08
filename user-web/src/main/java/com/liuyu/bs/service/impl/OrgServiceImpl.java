package com.liuyu.bs.service.impl;

import com.liuyu.bs.business.Org;
import com.liuyu.bs.dao.OrgDao;
import com.liuyu.bs.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: OrgServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-7 上午10:04 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
public class OrgServiceImpl implements OrgService {

    @Autowired
    private OrgDao orgDao;

    @Override
    @Transactional
    public void add(Org org) {
        orgDao.add(org);
    }

    @Override
    @Transactional
    public void adds(List<Org> orgs) {
        orgDao.adds(orgs);
    }

    @Override
    @Transactional
    public void update(Org org) {
        orgDao.update(org);
    }

    @Override
    @Transactional
    public void delete(Org org) {
        orgDao.delete(org);
    }

    @Override
    public Org get(String code) {
        return orgDao.get(code);
    }

    @Override
    public List<Org> queryOrgWithNotSchool() {
        return orgDao.queryOrgWithNotSchool();
    }

    @Override
    public List<Org> queryChild(String parentCode) {
        return orgDao.queryChild(parentCode);
    }
}

package com.liuyu.user.web.service.impl;

import com.liuyu.common.util.IdGenerator;
import com.liuyu.user.web.dao.ResourceDao;
import com.liuyu.user.web.dao.RoleDao;
import com.liuyu.user.web.domain.Resource;
import com.liuyu.user.web.domain.Role;
import com.liuyu.user.web.domain.RoleType;
import com.liuyu.user.web.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: RoleServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-2 下午3:10 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ResourceDao resDao;

    @Autowired
    private IdGenerator idGenerator;

    @Override
    @Transactional
    public void saveRoleResources(long roleId, List<Resource> saveReses, List<Resource> delReses) {
        if (!delReses.isEmpty()) {
            resDao.delRoleResourceWithResIds(roleId, delReses);
        }
        if (!saveReses.isEmpty()) {
            resDao.addRoleResources(roleId, saveReses);
        }
    }

    @Override
    @Transactional
    public void add(Role role) {
        role.setId(idGenerator.nextId());
        roleDao.add(role);
    }

    @Override
    @Transactional
    public void udpate(Role role) {
        roleDao.update(role);
    }

    @Override
    @Transactional
    public void delete(Role role) {
        resDao.delRoleResources(role.getId());
        roleDao.delRoleUser(role.getId());
        roleDao.delete(role.getId());
    }

    @Override
    public List<Role> queryRoles() {
        return roleDao.queryRoles();
    }

    @Override
    public List<Role> queryRoles(List<RoleType> roleTypes) {
        if (roleTypes == null || roleTypes.isEmpty()) {
            return roleDao.queryRoles();
        }
        return roleDao.queryRolesWithLevel(roleTypes);
    }

}

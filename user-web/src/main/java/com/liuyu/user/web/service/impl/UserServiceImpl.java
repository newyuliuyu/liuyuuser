package com.liuyu.user.web.service.impl;

import com.google.common.collect.Lists;
import com.liuyu.common.util.IdGenerator;
import com.liuyu.user.web.dao.ResourceDao;
import com.liuyu.user.web.dao.RoleDao;
import com.liuyu.user.web.dao.UserDao;
import com.liuyu.user.web.domain.Resource;
import com.liuyu.user.web.domain.Role;
import com.liuyu.user.web.domain.User;
import com.liuyu.user.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ClassName: UserServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-26 下午5:37 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ResourceDao resDao;

    @Autowired
    private IdGenerator idGenerator;

    @Override
    @Transactional
    public void saveUserRoles(User user, List<Role> roles) {
        roleDao.delUserRoles(user.getId());
        if (!roles.isEmpty()) {
            roleDao.addUserRoles(user.getId(), roles);
        }
    }

    @Override
    @Transactional
    public void saveUserResources(long userId, List<Resource> saveReses, List<Resource> delReses) {
        if (!delReses.isEmpty()) {
            resDao.delUserResourceWithResIds(userId, delReses);
        }
        if (!saveReses.isEmpty()) {
            resDao.addUserResources(userId, saveReses);
        }
    }

    @Override
    @Transactional
    public void add(User user) {
        user.setId(idGenerator.nextId());
        user.setPassword("12345678");
        userDao.add(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        userDao.updateUser(user);
    }

    @Override
    @Transactional
    public void updatePwd(User user) {
        userDao.updatePassword(user);
    }

    @Override
    @Transactional
    public void resetPwd(User user) {
        user.setPassword("12345678");
        updatePwd(user);
    }

    @Override
    @Transactional
    public void delete(User user) {
        roleDao.delUserRoles(user.getId());
        resDao.delUserResources(user.getId());
        userDao.delUser(user);
    }

    @Override
    public User get(String username) {

        User user = null;
        user = userDao.queryWithUserName(username);
        if (user == null) {
            user = userDao.queryWithPhone(username);
        }
        if (user == null) {
            user = userDao.queryWithPhone(username);
        }
        if (user == null) {
            log.warn("使用{}查找用户没有", username);
        }
        return user;
    }

    @Override
    public User queryWithUserName(String userName) {
        return userDao.queryWithUserName(userName);
    }

    @Override
    public User queryWithPhone(String phone) {
        return userDao.queryWithPhone(phone);
    }

    @Override
    public User queryWithEmail(String email) {
        return userDao.queryWithEmail(email);
    }

    @Override
    public User get(long id) {
        return userDao.get(id);
    }

    @Override
    public List<User> queryUsers(String searchText) {
        if (!StringUtils.isEmpty(searchText)) {
            searchText = "%" + searchText + "%";
        }
        return userDao.queryUsers(searchText);
    }

    @Override
    public List<Role> queryRoles(long userId) {
        return roleDao.queryUserRoles(userId);
    }

    @Override
    public List<Resource> queryUserResourcesWithGroup(User user, String group) {
        List<Resource> userReses = resDao.queryUserResourcesWtihGroup(user.getId(), group);
        Set<Resource> resourceSet = userReses.stream().collect(Collectors.toSet());
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            List<Resource> roleReses = resDao.queryRolesResourcesWtihGroup(user.getRoles(), group);
            roleReses.stream().forEach(res -> {
                resourceSet.add(res);
            });
        }

        List<Resource> result = Lists.newArrayList(resourceSet);
        result.sort(Comparator.comparingInt(res -> res.getOrderNum()));
        return result;
    }

    @Override
    public List<Resource> queryUserResources(User user) {
        return resDao.queryUserResources(user.getId());
    }


}

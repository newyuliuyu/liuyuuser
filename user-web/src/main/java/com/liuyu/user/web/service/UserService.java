package com.liuyu.user.web.service;

import com.liuyu.user.web.domain.Resource;
import com.liuyu.user.web.domain.Role;
import com.liuyu.user.web.domain.User;

import java.util.List;

/**
 * ClassName: UserService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-26 下午5:36 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface UserService {

    void saveUserRoles(User user, List<Role> roles);

    void saveUserResources(long userId, List<Resource> saveReses, List<Resource> delReses);

    void add(User user);

    void adds(List<User> users);

    void update(User user);

    void updateWithAccount(User user);

    void updatePwd(User user);

    void resetPwd(User user);

    void delete(User user);

    void delete(String account);


    User get(String account);

    User queryWithAccount(String account);

    User queryWithPhone(String phone);

    User queryWithEmail(String email);

    User get(long id);


    List<User> queryUsers(String searchText);

    List<Role> queryRoles(long userId);

    List<Resource> queryUserResourcesWithGroup(User user, String group);

    List<Resource> queryUserResources(User user);
}

package com.liuyu.user.web.service;

import com.liuyu.user.web.domain.Resource;
import com.liuyu.user.web.domain.Role;
import com.liuyu.user.web.domain.RoleType;

import java.util.List;

/**
 * ClassName: RoleService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-2 下午3:09 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface RoleService {

    void saveRoleResources(long roleId,List<Resource> saveReses, List<Resource> delReses);

    void add(Role role);

    void udpate(Role role);

    void delete(Role role);



    List<Role> queryRoles();

    List<Role> queryRoles(int level);

    List<RoleType> queryRoleTypes();

    List<RoleType> queryRoleTypes(int level);
}

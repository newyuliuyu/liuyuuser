package com.liuyu.user.web.dao;

import com.liuyu.user.web.domain.Role;
import com.liuyu.user.web.domain.RoleType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: RoleDao <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-26 上午11:23 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Repository
public interface RoleDao {


    int add(@Param("role") Role role);

    int update(@Param("role") Role role);

    int delete(@Param("roleId") long roleId);

    int addUserRoles(@Param("userId") long userId, @Param("roles") List<Role> roles);

    int addUserRole(@Param("userId") long userId, @Param("role") Role role);

    int delUserRoles(@Param("userId") long userId);

    int delUserRole(@Param("userId") long userId, @Param("roleId") long roleId);

    int delRoleUser(@Param("roleId") long roleId);

    Role get(@Param("roleId") long roleId);

    List<Role> queryUserRoles(@Param("userId") long userId);

    List<Role> queryRoles();

    List<Role> queryRolesWithLevel(@Param("roleTypes") List<RoleType> roleTypes);

}

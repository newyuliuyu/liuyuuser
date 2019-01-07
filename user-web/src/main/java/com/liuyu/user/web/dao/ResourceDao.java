package com.liuyu.user.web.dao;

import com.liuyu.user.web.domain.Resource;
import com.liuyu.user.web.domain.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: ResourceDao <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-26 下午4:05 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Repository
public interface ResourceDao {
    int add(@Param("res") Resource res);

    int addResources(@Param("reses") List<Resource> reses);

    int addRoleResources(@Param("roleId") long roleId, @Param("reses") List<Resource> reses);

    int addUserResources(@Param("userId") long urserId, @Param("reses") List<Resource> reses);

    int updateResource(@Param("res") Resource res);

    int delResource(@Param("res") Resource res);

    int delRoleResources(@Param("roleId") long roleId);

    int delRoleResource(@Param("roleId") long roleId, @Param("resId") long resId);

    int delRoleResourceWithResIds(@Param("roleId") long roleId, @Param("reses") List<Resource> reses);

    int delResourceRole(@Param("resId") long resId);

    int delUserResources(@Param("userId") long userId);

    int delUserResource(@Param("userId") long userId, @Param("resId") long resId);

    int delUserResourceWithResIds(@Param("userId") long userId, @Param("reses") List<Resource> reses);

    int delResourceUser(@Param("resId") long resId);

    List<Resource> queryResources();

    List<Resource> queryRoleResources(@Param("roleId") long roleId);

    List<Resource> queryRoleResourcesWtihGroup(@Param("roleId") long roleId, @Param("group") String group);

    List<Resource> queryRolesResourcesWtihGroup(@Param("roles") List<Role> roles, @Param("group") String group);

    List<Resource> queryUserResources(@Param("userId") long userId);

    List<Resource> queryUserResourcesWtihGroup(@Param("userId") long userId, @Param("group") String group);


}

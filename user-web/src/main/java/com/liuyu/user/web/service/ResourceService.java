package com.liuyu.user.web.service;

import com.liuyu.user.web.domain.Resource;

import java.util.List;

/**
 * ClassName: ResourceService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-27 下午1:16 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface ResourceService {

    void add(Resource resource);

    void update(Resource resource);

    void delete(Resource resource);


    List<Resource> queryResources();

    List<Resource> querRoleResources(long roleId);
}

package com.liuyu.user.web.service.impl;

import com.liuyu.common.util.IdGenerator;
import com.liuyu.user.web.dao.ResourceDao;
import com.liuyu.user.web.domain.Resource;
import com.liuyu.user.web.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: ResourceServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-27 下午1:17 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceDao resDao;


    @Autowired
    private IdGenerator idGenerator;

    @Override
    @Transactional
    public void add(Resource resource) {
        resource.setId(idGenerator.nextId());
        resDao.add(resource);
    }

    @Override
    @Transactional
    public void update(Resource resource) {
        resDao.updateResource(resource);
    }

    @Override
    @Transactional
    public void delete(Resource resource) {
        resDao.delResourceRole(resource.getId());
        resDao.delResourceUser(resource.getId());
        resDao.delResource(resource);
    }

    @Override
    public List<Resource> queryResources() {
        return resDao.queryResources();
    }

    @Override
    public List<Resource> querRoleResources(long roleId) {
        return resDao.queryRoleResources(roleId);
    }
}

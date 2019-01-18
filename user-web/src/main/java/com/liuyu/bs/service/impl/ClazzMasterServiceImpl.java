package com.liuyu.bs.service.impl;

import com.liuyu.bs.business.ClazzMaseter;
import com.liuyu.bs.dao.ClazzMasterDao;
import com.liuyu.bs.service.ClazzMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: ClazzMasterServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-16 下午5:24 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
public class ClazzMasterServiceImpl implements ClazzMasterService {

    @Autowired
    private ClazzMasterDao clazzMasterDao;

    @Override
    public List<ClazzMaseter> queryClazzMasters(String orgCode) {
        return clazzMasterDao.queryClazzMaster(orgCode);
    }
}

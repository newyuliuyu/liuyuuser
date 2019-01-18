package com.liuyu.bs.dao;

import com.liuyu.bs.business.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: ClazzMasterDao <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-16 下午2:51 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Repository
public interface AdminDao {
    int add(@Param("admin") Admin admin);

    int adds(@Param("admins") List<Admin> admins);

    int delete(@Param("admin") Admin admin);

    List<Admin> queryAdmin(@Param("orgCode") String orgCode,
                           @Param("roleId") long roleId);


}

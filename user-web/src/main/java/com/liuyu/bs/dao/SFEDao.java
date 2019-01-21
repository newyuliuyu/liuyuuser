package com.liuyu.bs.dao;

import com.liuyu.bs.business.SFE;
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
public interface SFEDao {
    int add(@Param("sfe") SFE sfe);

    int adds(@Param("sfes") List<SFE> sfes);

    int delete(@Param("sfe") SFE sfe);

    List<SFE> querySFE(@Param("orgCode") String orgCode,
                           @Param("roleId") long roleId);


}

package com.liuyu.bs.dao;

import com.liuyu.bs.business.Org;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: OrgDao <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-7 上午10:04 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Repository
public interface OrgDao {

    int add(@Param("org") Org org);

    int adds(@Param("orgs") List<Org> orgs);

    int update(@Param("org") Org org);

    int delete(@Param("org") Org org);

    Org get(@Param("code") String code);

    Org getWithName(@Param("name") String name);

    List<Org> queryChild(@Param("parentCode") String parentCode);

    List<Org> queryOrgWithNotSchool();

}

package com.liuyu.bs.service;

import com.liuyu.bs.business.Org;

import java.util.List;

/**
 * ClassName: OrgService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-7 上午10:04 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface OrgService {

    void add(Org org);

    void adds(List<Org> orgs);

    void update(Org org);

    void delete(Org org);

    Org get(String code);

    Org getWithName(String name);

    List<Org> queryOrgWithNotSchool();

    List<Org> queryChild(String parentCode);


}

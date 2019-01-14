package com.liuyu.bs.service;

import com.liuyu.bs.business.*;
import com.liuyu.user.web.domain.User;

import java.util.List;

/**
 * ClassName: UserOrgService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-11 上午11:26 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface UserOrgService {


    Org getOrg(User user);

    List<Org> queryUserOrg(User user, String searchName);

    Province getProvince(User user);

    List<Province> queryUserProvince(User user, String searchName);

    City getCity(User user);

    List<City> queryUserCity(User user, String searchName);

    County getCounty(User user);

    List<County> queryUserCounty(User user, String searchName);

    School getSchool(User user);

    List<School> queryUserSchools(User user, String searchName);

    List<Teacher> getTeacher(User user);
}

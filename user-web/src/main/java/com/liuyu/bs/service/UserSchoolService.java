package com.liuyu.bs.service;

import com.liuyu.bs.business.School;
import com.liuyu.user.web.domain.User;

import java.util.List;

/**
 * ClassName: UserSchoolService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-9 下午1:29 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface UserSchoolService {
    School queryUserSchool(User user);

    List<School> queryUserSchools(User user, String schoolName);
}

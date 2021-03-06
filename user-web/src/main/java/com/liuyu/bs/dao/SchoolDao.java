package com.liuyu.bs.dao;

import com.liuyu.bs.business.City;
import com.liuyu.bs.business.County;
import com.liuyu.bs.business.Province;
import com.liuyu.bs.business.School;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: SchoolDao <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-9 上午9:39 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Repository
public interface SchoolDao {

    List<School> querySchools(@Param("code") String code,
                              @Param("schoolName") String schoolName,
                              @Param("codeOwnerDeep") int codeOwnerDeep);

    County getSchoolOwnerCounty(@Param("code") String code);

    City getSchoolOwnerCity(@Param("code") String code);

    Province getSchoolOwnerProvince(@Param("code") String code);

}

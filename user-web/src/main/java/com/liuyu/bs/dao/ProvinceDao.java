package com.liuyu.bs.dao;

import com.liuyu.bs.business.Province;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: CountyDao <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-11 下午1:56 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Repository
public interface ProvinceDao {
    List<Province> queryProvinces(@Param("code") String code,
                              @Param("name") String countyName,
                              @Param("codeOwnerDeep") int codeOwnerDeep);
}

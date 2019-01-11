package com.liuyu.bs.dao;

import com.liuyu.bs.business.Clazz;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: ClazzDao <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-9 下午3:48 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Repository
public interface ClazzDao {

    void add(@Param("clazz") Clazz clazz);

    void adds(@Param("clazzes") List<Clazz> clazzes);

    void del(@Param("code") String code);

    void delGradeClazz(@Param("gradeId") long gradeId);

    Clazz get(@Param("code") String clazzCode);

    List<Clazz> queryClazzesWithSchoolCode(@Param("schoolCode") String schoolCode);


}

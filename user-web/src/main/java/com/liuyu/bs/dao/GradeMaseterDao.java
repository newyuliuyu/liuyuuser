package com.liuyu.bs.dao;

import com.liuyu.bs.business.GradeMaseter;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: TeachingTeacherDao <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-16 下午3:10 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Repository
public interface GradeMaseterDao {
    int add(@Param("gradeMaseter") GradeMaseter gradeMaseter);

    int adds(@Param("gradeMaseters") List<GradeMaseter> gradeMaseters);

    int delete(@Param("gradeMaseter") GradeMaseter gradeMaseter);

    List<GradeMaseter> queryGradeMaseter(@Param("orgCode") String orgCode);

    boolean teacheHasGradeMaseter(@Param("teacherCode") String teacherCode);
}

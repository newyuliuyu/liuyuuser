package com.liuyu.bs.dao;

import com.liuyu.bs.business.GradeMaster;
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
public interface GradeMasterDao {
    int add(@Param("gradeMaster") GradeMaster gradeMaster);

    int adds(@Param("gradeMasters") List<GradeMaster> gradeMasters);

    int delete(@Param("gradeMaster") GradeMaster gradeMaster);

    List<GradeMaster> queryGradeMaster(@Param("orgCode") String orgCode);

}

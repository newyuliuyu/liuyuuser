package com.liuyu.bs.dao;

import com.liuyu.bs.business.ClazzMaseter;
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
public interface ClazzMasterDao {
    int add(@Param("clazzMaster") ClazzMaseter clazzMaster);

    int adds(@Param("clazzMasters") List<ClazzMaseter> clazzMasters);

    int delete(@Param("clazzMaster") ClazzMaseter clazzMaster);

    List<ClazzMaseter> queryClazzMaster(@Param("orgCode") String orgCode);

    boolean teacheHasClazzMaster(@Param("teacherCode") String teacherCode);


}

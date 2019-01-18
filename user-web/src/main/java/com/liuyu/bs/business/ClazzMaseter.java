package com.liuyu.bs.business;

import lombok.*;

/**
 * ClassName: ClazzMaster <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-16 下午2:29 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClazzMaseter {
    private int id;
    private Teacher teacher;
    private Org org;
    private Grade grade;
    private Clazz clazz;
}

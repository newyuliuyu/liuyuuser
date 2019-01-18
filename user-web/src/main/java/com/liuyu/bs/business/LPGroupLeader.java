package com.liuyu.bs.business;

import lombok.*;

/**
 * ClassName: LPGroupLeader <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-16 下午2:39 <br/>
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
public class LPGroupLeader {
    private int id;
    private Teacher teacher;
    private Org org;
    private Grade grade;
    private String subjectName;
}

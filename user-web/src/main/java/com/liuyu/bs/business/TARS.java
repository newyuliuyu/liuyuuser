package com.liuyu.bs.business;

import lombok.*;

/**
 * ClassName: TARS <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-16 下午2:33 <br/>
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
public class TARS {
    private int id;
    private Teacher teacher;
    private Org org;
    private int learnSegment;
}

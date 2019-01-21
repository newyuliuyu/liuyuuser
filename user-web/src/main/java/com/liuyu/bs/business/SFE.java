package com.liuyu.bs.business;

import lombok.*;

/**
 * ClassName: SFE <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-16 下午2:37 <br/>
 * 教育局局长
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
public class SFE {
    private int id;
    private Teacher teacher;
    private Org org;
    private Long roleId;
}

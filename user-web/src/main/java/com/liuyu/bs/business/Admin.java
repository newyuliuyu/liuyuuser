package com.liuyu.bs.business;

import lombok.*;

/**
 * ClassName: Admin <br/>
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
public class Admin {
    private int id;
    private Teacher teacher;
    private Org org;
}

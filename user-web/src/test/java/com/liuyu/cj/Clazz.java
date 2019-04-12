package com.liuyu.cj;

import lombok.*;

/**
 * ClassName: Clazz <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-3-7 下午5:18 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"code"})
public class Clazz {
    private String code;
    private String name;
}

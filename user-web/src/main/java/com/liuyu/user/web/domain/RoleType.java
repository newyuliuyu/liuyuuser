package com.liuyu.user.web.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: RoleType <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-19 下午1:44 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"code"})
public class RoleType {
    private String code;
    private String name;
    private int level;
}

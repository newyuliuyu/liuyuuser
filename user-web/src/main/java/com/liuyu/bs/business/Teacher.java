package com.liuyu.bs.business;

import lombok.*;

/**
 * ClassName: Teacher <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-17 下午1:53 <br/>
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
public class Teacher {
    private Long id;
    private String code;
    private String name;
    private String account;
    private String phone;
    private String email;
    private Org org;
}

package com.liuyu.bs.business;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: Organization <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-17 下午1:45 <br/>
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
public class Org {
    private int id;
    private String code;
    private String name;
    private String parentCode;
    private int deep;
}

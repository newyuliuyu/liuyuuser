package com.liuyu.user.web.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: Resource <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-14 上午9:51 <br/>
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
public class Resource {
    private Long id;
    private String name;
    private CategoryType categoryType;
    private String group;
    private String smallGroup;
    private int orderNum;

}

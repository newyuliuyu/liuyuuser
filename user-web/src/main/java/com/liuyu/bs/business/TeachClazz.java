package com.liuyu.bs.business;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: TeachClazz <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-17 下午1:51 <br/>
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
public class TeachClazz {
    private Clazz clazz;
    private String subjectName;
}

package com.liuyu.user.web.business;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * ClassName: Student <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-17 下午1:59 <br/>
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
public class Student {
    private String owenId;
    private String code;
    private String name;
    private School school;
    private Clazz clazz;
    private List<TeachClazz> teachClazzes;
}

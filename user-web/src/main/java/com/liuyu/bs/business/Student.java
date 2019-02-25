package com.liuyu.bs.business;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

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
    private Long id;
    private School school;
    private Grade grade;
    private Clazz clazz;
    private String studentId;//学号
    private String zkzh;//准考证号
    private String name;//姓名
    private String account;//帐号
}

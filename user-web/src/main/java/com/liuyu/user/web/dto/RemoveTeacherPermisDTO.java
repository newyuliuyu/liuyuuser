package com.liuyu.user.web.dto;

import com.liuyu.bs.business.*;
import com.liuyu.user.web.domain.RoleType;
import lombok.*;

/**
 * ClassName: RemoveTeacherPermisDTO <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-17 上午10:29 <br/>
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
public class RemoveTeacherPermisDTO {

    private RoleType roleType;
    private long roleId;
    private Teacher teacher;
    private String orgCode;
    private long gradeId;
    private String clazzCode;
    private String subjectName;

    private Clazz getClazz() {
        return Clazz.builder().code(clazzCode).build();
    }

    private Grade getGrade() {
        return Grade.builder().id(gradeId).build();
    }

    private Org getOrg() {
        return Org.builder().code(orgCode).build();
    }

    public ClazzMaseter converToClazzMaster() {
        return ClazzMaseter.builder()
                .clazz(getClazz())
                .grade(getGrade())
                .org(getOrg())
                .teacher(teacher)
                .build();
    }

    public TeachingTeacher converToTeachingTeacher() {
        return TeachingTeacher.builder()
                .clazz(getClazz())
                .grade(getGrade())
                .org(getOrg())
                .teacher(teacher)
                .subjectName(subjectName)
                .build();
    }
}

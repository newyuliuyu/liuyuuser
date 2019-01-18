package com.liuyu.user.web.dto;

import com.liuyu.bs.business.*;
import lombok.*;

/**
 * ClassName: AddClazzMasterAndTeacher <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-16 下午1:32 <br/>
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
public class AddClazzMasterAndTeacherDTO {
    private boolean clazzmaster;
    private Teacher oldTeacher;
    private Teacher newTeacher;
    private String subjectName;
    private long gradeId;
    private String clazzCode;
    private long roleId;
    private String orgCode;

    private Clazz getClazz() {
        return Clazz.builder().code(clazzCode).build();
    }

    private Grade getGrade() {
        return Grade.builder().id(gradeId).build();
    }

    private Org getOrg() {
        return Org.builder().code(orgCode).build();
    }

    public ClazzMaseter converToClazzMasterForOld() {
        if (oldTeacher == null) {
            return null;
        }
        return ClazzMaseter.builder()
                .clazz(getClazz())
                .grade(getGrade())
                .org(getOrg())
                .teacher(oldTeacher)
                .build();
    }

    public ClazzMaseter converToClazzMasterForNew() {
        return ClazzMaseter.builder()
                .clazz(getClazz())
                .grade(getGrade())
                .org(getOrg())
                .teacher(newTeacher)
                .build();
    }

    public TeachingTeacher converToTeachingTeacherForOld() {
        if (oldTeacher == null) {
            return null;
        }
        return TeachingTeacher.builder()
                .clazz(getClazz())
                .grade(getGrade())
                .org(getOrg())
                .teacher(oldTeacher)
                .subjectName(subjectName)
                .build();
    }

    public TeachingTeacher converToTeachingTeacherForNew() {
        return TeachingTeacher.builder()
                .clazz(getClazz())
                .grade(getGrade())
                .org(getOrg())
                .teacher(newTeacher)
                .subjectName(subjectName)
                .build();
    }
}

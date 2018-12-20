package com.liuyu.user.web.business;

import com.liuyu.common.util.Dateutil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * ClassName: Grade <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-17 上午10:28 <br/>
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
public class Grade {
    private Long id;
    private String name;
    private int enterSchoolYear;//入学年份
    private int graduationYear;//毕业年份
    private int learnSegment;//学段

    public static Grade createGrade(String name) {
        return createGrade(name, new Date());
    }

    public static Grade createGrade(String name, Date date) {
        Grade grade = new Grade();
        int month = Integer.parseInt(Dateutil.getMonth(date));
        int year = Integer.parseInt(Dateutil.getYear(date));
        int semester = 1;
        if (month >= 2 && month < 9) {
            semester = 2;
        } else {
            semester = 1;
        }
        if (name.equals("一年级")) {
            grade.setLearnSegment(1);
        } else if (name.equals("二年级")) {
            grade.setLearnSegment(1);
            year = year - 1;
        } else if (name.equals("三年级")) {
            grade.setLearnSegment(1);
            year = year - 2;
        } else if (name.equals("四年级")) {
            grade.setLearnSegment(1);
            year = year - 3;
        } else if (name.equals("五年级")) {
            grade.setLearnSegment(1);
            year = year - 4;
        } else if (name.equals("六年级")) {
            grade.setLearnSegment(1);
            year = year - 5;
        } else if (name.equals("七年级") || name.equals("初一")) {
            grade.setLearnSegment(2);
        } else if (name.equals("八年级") || name.equals("初二")) {
            grade.setLearnSegment(2);
            year = year - 1;
        } else if (name.equals("九年级") || name.equals("初三")) {
            grade.setLearnSegment(2);
            year = year - 2;
        } else if (name.equals("高一")) {
            grade.setLearnSegment(3);
        } else if (name.equals("高二")) {
            grade.setLearnSegment(3);
            year = year - 1;
        } else if (name.equals("高三")) {
            grade.setLearnSegment(3);
            year = year - 2;
        }
        if (semester == 1) {
            grade.setEnterSchoolYear(year);
        } else {
            grade.setEnterSchoolYear(year - 1);
        }
        if (grade.getLearnSegment() == 1) {
            grade.setGraduationYear(grade.getEnterSchoolYear() + 6);
        } else {
            grade.setGraduationYear(grade.getEnterSchoolYear() + 3);
        }
        return grade;
    }
}

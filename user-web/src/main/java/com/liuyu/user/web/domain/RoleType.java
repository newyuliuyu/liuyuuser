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
public enum RoleType {
    Root("超级管理员", 1000000),
    operator("运营人员", 10000),
    Admin("管理员", 1000),
    schoolMaseter("校长", 1),
    gradeMaseter("年级主任", 1),
    LPGroupLeader("备课组长", 1),
    clazzMaseter("班主任", 1),
    teacher("教师", 1),
    SFE("教育局局长", 2),
    TARS("教研室主任", 2),
    researchStaff("教研员", 2),
    parents("家长", 100),
    student("学生", 100);
    private String name;
    private int level;

    private RoleType(String name, int level) {
        this.name = name;
        this.level = level;
    }
}

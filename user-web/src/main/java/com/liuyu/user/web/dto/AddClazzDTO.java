package com.liuyu.user.web.dto;

import com.google.common.collect.Lists;
import com.liuyu.bs.business.Clazz;
import com.liuyu.bs.business.Grade;
import com.liuyu.bs.business.School;
import com.liuyu.common.spring.SpringContextUtil;
import com.liuyu.common.util.IdGenerator;
import lombok.*;

import java.util.List;

/**
 * ClassName: AddClazzDTO <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-10 下午3:54 <br/>
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
public class AddClazzDTO {
    private boolean batchCreate = false;
    private int num = 0;
    private String name;
    private int wl;
    private boolean teachClazz;
    private String subjectName = "";
    private String schoolCode;
    private long gradeId;

    public List<Clazz> converToClazz() {
        School school = new School();
        school.setCode(schoolCode);
        Grade grade = Grade.builder().id(gradeId).build();

        IdGenerator idGenerator = SpringContextUtil.getBean(IdGenerator.class);
        List<Clazz> clazzes = Lists.newArrayList();
        if (batchCreate) {
            for (int i = 1; i <= num; i++) {
                Clazz clazz = Clazz.builder().code(idGenerator.nextId() + "")
                        .name(i + "班")
                        .wl(wl)
                        .teachClazz(teachClazz)
                        .subjectName(subjectName)
                        .school(school)
                        .grade(grade).build();
                clazzes.add(clazz);
            }
        } else {
            Clazz clazz = Clazz.builder().code(idGenerator.nextId() + "")
                    .name(name)
                    .wl(wl)
                    .teachClazz(teachClazz)
                    .subjectName(subjectName)
                    .school(school)
                    .grade(grade).build();
            clazzes.add(clazz);
        }
        return clazzes;
    }
}

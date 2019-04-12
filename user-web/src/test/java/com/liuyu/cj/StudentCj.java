package com.liuyu.cj;

import com.google.common.collect.Lists;
import lombok.*;

import java.util.List;

/**
 * ClassName: Student <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-3-7 下午5:15 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"zkzh"})
public class StudentCj {
    private String name;
    private String zkzh;
    private String code;
    private int examId;
    private int wl;
    private School school;
    private Clazz clazz;
    private List<SubjectCj> subjectCjs;

    public void appendSubjectCj(SubjectCj subjectCj) {
        if (subjectCjs == null) {
            subjectCjs = Lists.newArrayList();
        }
        subjectCjs.add(subjectCj);
    }
}

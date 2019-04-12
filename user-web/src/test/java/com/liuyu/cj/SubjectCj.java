package com.liuyu.cj;

import lombok.*;

import java.util.List;

/**
 * ClassName: SubjectCj <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-3-7 下午5:19 <br/>
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
public class SubjectCj {
    private String subjectName;
    private double score;
    private double zgScore;
    private double kgScore;
    private boolean qk;
    private List<ItemCj> itemCjs;

}

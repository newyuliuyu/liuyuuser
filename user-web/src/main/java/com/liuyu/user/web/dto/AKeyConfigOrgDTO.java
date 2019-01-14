package com.liuyu.user.web.dto;

import lombok.*;

/**
 * ClassName: AKeyConfigSchoolDTO <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-9 下午4:48 <br/>
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
public class AKeyConfigOrgDTO {
    private String orgCode;
    private boolean hasPrimarySchool;
    private boolean hasJuniorHighSchool;
    private boolean hasHighSchool;
}

package com.liuyu.user.web.dto;

import com.liuyu.user.web.domain.RoleType;
import lombok.*;

/**
 * ClassName: RoleTypeDTO <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-9 上午10:59 <br/>
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
public class RoleTypeDTO {
    private String code;
    private String name;
    private int level;

    public static RoleTypeDTO converFor(RoleType roleType) {
        return RoleTypeDTO.builder().code(roleType.name()).name(roleType.getName()).level(roleType.getLevel()).build();
    }

    public RoleType converToRoleType() {
        RoleType[] roleTypes = RoleType.values();
        for (RoleType roleType : roleTypes) {
            if (code.equals(roleType.name())) {
                return roleType;
            }
        }
        return null;
    }
}

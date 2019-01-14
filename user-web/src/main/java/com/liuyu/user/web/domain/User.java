package com.liuyu.user.web.domain;

import com.liuyu.bs.business.Org;
import com.liuyu.bs.business.Teacher;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * ClassName: User <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-14 上午9:51 <br/>
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
@EqualsAndHashCode(of = {"id"})
public class User {
    private Long id;
    private String userName;
    private String realName = "";
    private String phone = "";
    private String email = "";
    private String password;

    private List<Role> roles;
    private Teacher teacher;

    public String getRealName() {
        if (StringUtils.isEmpty(realName)) {
            return userName;
        }
        return realName;
    }

    public boolean isSuperAdmin() {
        if (roles != null) {
            for (Role role : roles) {
                if (role.getRoleType().equals(RoleType.Root)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Org queryOrg() {
        if (teacher != null) {
            return teacher.getOrg();
        }
        return Org.builder().code("-00000").name("空").deep(-2).parentCode("-00000").build();
    }
}

package com.liuyu.user.web.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

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

    public String getRealName() {
        if (StringUtils.isEmpty(realName)) {
            return userName;
        }
        return realName;
    }
}

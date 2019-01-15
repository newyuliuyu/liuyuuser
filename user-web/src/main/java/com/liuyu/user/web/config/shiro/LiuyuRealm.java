package com.liuyu.user.web.config.shiro;

import com.liuyu.user.web.domain.User;
import com.liuyu.user.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

/**
 * ClassName: LiuyuReaml <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-24 下午5:48 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class LiuyuRealm extends AuthorizingRealm {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        if (token.getPrincipal() == null
                || token.getCredentials() == null) {
            log.error("用户和密码不能为空");
            throw new UnknownAccountException("用户和密码不能为空");
        }
        
        String username = token.getPrincipal().toString();
        String pwd = token.getCredentials().toString();
        if (StringUtils.isEmpty(username)
                || StringUtils.isEmpty(pwd)) {
            log.error("用户和密码不能为空");
            throw new UnknownAccountException("用户和密码不能为空");
        }

        User user = userService.get(username);
        if (user == null) {
            log.error("用户不存在");
            throw new UnknownAccountException("用户不存在");
        }
        String realName = "流域";
        Object principal = "liuyu";
        // 参数 ：credentials ---> 密码
        Object credentials = "123456";
//        principal, credentials, realName
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), user.getName());
//        simpleAuthenticationInfo.setCredentials(userId);
//        simpleAuthenticationInfo.setPrincipals(principalCollection);
        return simpleAuthenticationInfo;
    }
}

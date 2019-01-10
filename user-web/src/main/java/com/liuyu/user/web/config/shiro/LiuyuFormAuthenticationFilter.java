package com.liuyu.user.web.config.shiro;

import com.liuyu.common.json.Json2;
import com.liuyu.common.mvc.ModelAndViewFactory;
import com.liuyu.common.mvc.Responser;
import com.liuyu.common.util.ReqUtil;
import com.liuyu.common.util.ThrowableToString;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * ClassName: LiuyuFormAuthenticationFilter <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-24 下午5:43 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class LiuyuFormAuthenticationFilter extends FormAuthenticationFilter {
    private static Logger logger = LoggerFactory.getLogger(LiuyuFormAuthenticationFilter.class);

    private boolean isScanningProgramLogin(HttpServletRequest httpServletRequest) {
        return "yes".equalsIgnoreCase(httpServletRequest.getHeader("EZ-Scanning-Program"));
    }

    private boolean isAjax(HttpServletRequest httpServletRequest) {
        return ReqUtil.isAjax(httpServletRequest);
    }


    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        return createToken(username, password, request, response);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        return super.onAccessDenied(request, response);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        // return super.onLoginSuccess(token, subject, request, response);
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        logger.info("Scanner Loggin:{} -- {}", isScanningProgramLogin(httpServletRequest),
                httpServletRequest.getHeader("EZ-Scanning-Program"));

        if (!isScanningProgramLogin(httpServletRequest) && !isAjax(httpServletRequest)) {// 不是ajax请求
            return super.onLoginSuccess(token, subject, request, response);
        } else {

//            UserPrincipalCollection userPrincipalCollection = (UserPrincipalCollection) subject.getPrincipals();
//            User user = userPrincipalCollection.getUserFace().getUser();


            String redirectURL = null;
            SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
            if (savedRequest != null && savedRequest.getMethod().equalsIgnoreCase(AccessControlFilter.GET_METHOD)) {
                redirectURL = savedRequest.getRequestUrl();
            }


            httpServletResponse.setCharacterEncoding("UTF-8");
            PrintWriter out = httpServletResponse.getWriter();
            ModelAndView modelAndView = ModelAndViewFactory.instance()
                    .with("result", "登入成功")
                    .with("sessionId", httpServletRequest.getSession().getId())
                    .with("redirectURL", redirectURL)
                    .build();
            out.println(Json2.toJson(modelAndView.getModel()));
            out.flush();
            out.close();
        }
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
                                     ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        boolean isScan = isScanningProgramLogin(httpServletRequest);
        boolean isAjax = isAjax(httpServletRequest);
        if (!isScan && !isAjax) {// 不是ajax请求
            return super.onLoginFailure(token, e, request, response);
        }
        try {
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            String message = e.getClass().getSimpleName();
            Responser rs = null;
            if (isScan && "AuthenticationException".equals(message)) {
                rs = new Responser.Builder().failure().code("00001").msg("用户名错误").detail(ThrowableToString.toString(e)).create();
            } else if ("AuthenticationException".equals(message)) {
                rs = new Responser.Builder().failure().code("00001").msg("用户名或密码错误").detail(ThrowableToString.toString(e)).create();
            } else if (isScan && "IncorrectCredentialsException".equals(message)) {
                rs = new Responser.Builder().failure().code("00001").msg("用户名或密码错误").detail(ThrowableToString.toString(e)).create();
            } else if ("IncorrectCredentialsException".equals(message)) {
                rs = new Responser.Builder().failure().code("00001").msg("用户名或密码错误").detail(ThrowableToString.toString(e)).create();
            } else if ("UnknownAccountException".equals(message)) {
                rs = new Responser.Builder().failure().code("00001").msg("用户不存在").detail(ThrowableToString.toString(e)).create();
            } else if ("LockedAccountException".equals(message)) {
                rs = new Responser.Builder().failure().code("00001").msg("账号被锁定").detail(ThrowableToString.toString(e)).create();
            } else {
                rs = new Responser.Builder().failure().code("00000").msg("未知错误").detail(ThrowableToString.toString(e)).create();
            }
            ModelAndView modelAndView = ModelAndViewFactory.instance()
                    .with(Responser.ModelName, rs)
                    .build();
            out.println(Json2.toJson(modelAndView.getModel()));
            out.flush();
            out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return false;
    }
}

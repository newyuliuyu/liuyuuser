package com.liuyu.user.web.config.shiro;

import com.liuyu.common.json.Json2;
import com.liuyu.common.mvc.ModelAndViewFactory;
import com.liuyu.common.mvc.Responser;
import com.liuyu.common.util.ReqUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * ClassName: AccessFilter <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-25 下午2:19 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class AccessFilter extends AccessControlFilter {

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginRequest(request, response)) {
            return true;
        } else {
            Subject subject = getSubject(request, response);
            Session session = subject.getSession();
            // If principal is not null, then the user is known and should be allowed access.
            boolean isAcess = false;
            try {
                isAcess = subject.getPrincipal() != null;
            } catch (ExpiredSessionException e) {
                log.error("session过期", e);
                isAcess = false;
            }
            return isAcess;
        }
    }

    /**
     * This default implementation simply calls
     * {@link #saveRequestAndRedirectToLogin(javax.servlet.ServletRequest, javax.servlet.ServletResponse) saveRequestAndRedirectToLogin}
     * and then immediately returns <code>false</code>, thereby preventing the chain from continuing so the redirect may
     * execute.
     */
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        boolean isExpiredSession = false;
        try {
            saveRequest(request);
        } catch (ExpiredSessionException e) {
            isExpiredSession = true;
        }

        res.setStatus(500);
        if (ReqUtil.isAjax((HttpServletRequest) request)) {
            String msg = "未登录";
            if (isExpiredSession) {
                msg = "session超时";
            }

            Responser rs = new Responser.Builder().failure().msg(msg).create();
            ModelAndView modelAndView = ModelAndViewFactory.instance().with(Responser.ModelName, rs).build();
            res.setCharacterEncoding("UTF-8");
            PrintWriter out = res.getWriter();
            out.println(Json2.toJson(modelAndView.getModel()));
            out.flush();
            out.close();
        } else {
            redirectToLogin(request, response);
        }
//        saveRequestAndRedirectToLogin(request, response);
        return false;
    }
}

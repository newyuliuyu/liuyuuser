package com.liuyu.user.web.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.liuyu.user.web.config.shiro.AccessFilter;
import com.liuyu.user.web.config.shiro.LiuyuFormAuthenticationFilter;
import com.liuyu.user.web.config.shiro.LiuyuRealm;
import com.liuyu.user.web.config.shiro.QuartzSessionValidationScheduler2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.AbstractValidatingSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ShiroConfig <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-24 下午2:24 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setSuccessUrl("/index.html");
        shiroFilter.setLoginUrl("/login.html");
        shiroFilter.setUnauthorizedUrl("/unauthorized");


        Map<String, Filter> filters = Maps.newHashMap();
        filters.put("formfilter", new LiuyuFormAuthenticationFilter());
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/login.html");
        filters.put("logout", logoutFilter);
        filters.put("accessFilter", new AccessFilter());


        shiroFilter.setFilters(filters);


        Map<String, String> filterChainDefinitionMap = Maps.newHashMap();
        filterChainDefinitionMap.put("/resources/**", "anon");
        filterChainDefinitionMap.put("/login.html", "formfilter");
        filterChainDefinitionMap.put("/logout", "logout");
//        filterChainDefinitionMap.put("/**", "user");
        filterChainDefinitionMap.put("/**", "accessFilter");
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilter;
    }

    //DefaultWebSecurityManager======================
    @Bean
    public ModularRealmAuthenticator authenticator() {
        AtLeastOneSuccessfulStrategy authenticationStrategy = new AtLeastOneSuccessfulStrategy();
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(authenticationStrategy);
        return authenticator;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(SessionManager sessionManager,
                                                     Authenticator authenticator) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
//        securityManager.setCacheManager();
//        securityManager.setRememberMeManager();
        securityManager.setAuthenticator(authenticator);

        List<Realm> realms = Lists.newArrayList();
        realms.add(new LiuyuRealm());
        securityManager.setRealms(realms);


        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }
//    <!-- 相当于调用SecurityUtils.setSecurityManager(securityManager) -->
//    <bean class="org.springframework.beans.factory.config.MethodInvokingFactoryBean">
//        <property name="staticMethod" value="org.apache.shiro.SecurityUtils.setSecurityManager"/>
//        <property name="arguments" ref="securityManager"/>
//    </bean>

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    //session配置=============================================================
    @Bean
    public Cookie sessionIdCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("user");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(-1);
        simpleCookie.setDomain("");
        simpleCookie.setPath("/");
        return simpleCookie;
    }

    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    @Bean
    public SessionDAO sessionDao(SessionIdGenerator sessionIdGenerator) {
        EnterpriseCacheSessionDAO sessionDao = new EnterpriseCacheSessionDAO();
        sessionDao.setActiveSessionsCacheName("shiro-activeSessionCache");
        sessionDao.setSessionIdGenerator(sessionIdGenerator);
        return sessionDao;
    }

    @Bean
    public SessionValidationScheduler sessionValidationScheduler(ValidatingSessionManager sessionManager) {
        QuartzSessionValidationScheduler2 sessionValidationScheduler = new QuartzSessionValidationScheduler2();
//        sessionValidationScheduler.setSessionValidationInterval(1800000);
        sessionValidationScheduler.setSessionValidationInterval(5000);
        if (sessionManager instanceof AbstractValidatingSessionManager) {
            ((AbstractValidatingSessionManager) sessionManager).setSessionValidationScheduler(sessionValidationScheduler);
        }
        sessionValidationScheduler.setSessionManager(sessionManager);
        return sessionValidationScheduler;

//        ExecutorServiceSessionValidationScheduler sessionValidationScheduler = new ExecutorServiceSessionValidationScheduler();
//        sessionValidationScheduler.setInterval(1800000);
////        sessionValidationScheduler.setSessionManager(sessionManager);
//        return sessionValidationScheduler;
    }


    @Bean
    public ValidatingSessionManager sessionManager(SessionDAO sessionDao,
                                                   Cookie sessionIdCookie) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        sessionManager.setGlobalSessionTimeout(1800000);
        sessionManager.setGlobalSessionTimeout(5000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
//        sessionManager.setSessionValidationScheduler(sessionValidationScheduler);
        sessionManager.setSessionDAO(sessionDao);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie);
        return sessionManager;
    }
}

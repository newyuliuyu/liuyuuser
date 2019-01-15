package com.liuyu.user.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuyu.common.mvc.ModelAndViewFactory;
import com.liuyu.common.util.HttpReqUtils;
import com.liuyu.user.web.domain.Resource;
import com.liuyu.user.web.domain.Role;
import com.liuyu.user.web.domain.User;
import com.liuyu.user.web.dto.SaveXResourceDTO;
import com.liuyu.user.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ClassName: UserController <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-27 上午9:39 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;


    @RequestMapping("/add")
    public ModelAndView add(@RequestBody User user,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        log.debug("获取资源...." + this.getClass().getSimpleName() + ".add");
        userService.add(user);
        return ModelAndViewFactory.instance().build();
    }

    @RequestMapping("/update")
    public ModelAndView update(@RequestBody User user,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        log.debug("获取资源...." + this.getClass().getSimpleName() + ".update");
        userService.update(user);
        return ModelAndViewFactory.instance().build();
    }

    @RequestMapping("/delete")
    public ModelAndView delete(@RequestBody User user,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        log.debug("获取资源...." + this.getClass().getSimpleName() + ".delete");
        userService.delete(user);
        return ModelAndViewFactory.instance().build();
    }

    @RequestMapping("/get/{userId}")
    public ModelAndView get(@PathVariable long userId,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        log.debug("获取资源...." + this.getClass().getSimpleName() + ".get");
        User user = userService.get(userId);
        return ModelAndViewFactory.instance().with("user", user).build();
    }

    @RequestMapping("/info")
    public ModelAndView userInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.debug("获取用户信息....UserController.info");
        User user = getUser();
        user.setPassword("");
        return ModelAndViewFactory.instance().with("user", user).build();
    }

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.debug("获取用户信息....UserController.info");
        int pageNum = HttpReqUtils.getParamInt(request, "pageNum");
        if (pageNum == 0) {
            pageNum = 1;
        }
        int pageSize = HttpReqUtils.getParamInt(request, "pageSize");
        if (pageSize == 0) {
            pageSize = 10;
        }
        String searchText = HttpReqUtils.getParamString(request, "search");
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userService.queryUsers(searchText);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return ModelAndViewFactory.instance().with("users", users).with("pageInfo", pageInfo).build();
    }

    @RequestMapping("/resetpwd/{userId}")
    public ModelAndView resetpwd(@PathVariable long userId,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        log.debug("获取用户信息....UserController.resetpwd");
        User user = User.builder().id(userId).build();
        userService.resetPwd(user);
        return ModelAndViewFactory.instance().build();
    }

    @RequestMapping("/res/{userId}")
    public ModelAndView userRes(@PathVariable long userId,
                                HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        log.debug("获取用户信息....UserController.resetpwd");
        User user = User.builder().id(userId).build();
        List<Resource> reses = userService.queryUserResources(user);
        return ModelAndViewFactory.instance().with("reses", reses).build();
    }

    @RequestMapping("/saveres/{userId}")
    public ModelAndView saveres(@PathVariable long userId,
                                @RequestBody SaveXResourceDTO saveXResourceDTO,
                                HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        log.debug("获取资源....UserController.saveres");
        userService.saveUserResources(userId, saveXResourceDTO.getSaveResource(), saveXResourceDTO.getDelResource());
        return ModelAndViewFactory.instance().build();
    }

    @RequestMapping("/role/{userId}")
    public ModelAndView getRole(@PathVariable long userId,
                                HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        log.debug("获取资源....UserController.saveres");
        List<Role> roles = userService.queryRoles(userId);
        return ModelAndViewFactory.instance().with("roles", roles).build();
    }

    @RequestMapping("/save/role/{userId}")
    public ModelAndView saveUserRoles(@PathVariable long userId,
                                      @RequestBody List<Role> roles,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        log.debug("获取资源....UserController.saveres");
        User user = User.builder().id(userId).build();
        userService.saveUserRoles(user, roles);
        return ModelAndViewFactory.instance().build();
    }

    @RequestMapping("/validate/{type}")
    public ModelAndView validate(@PathVariable int type,
                                 HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        log.debug("获取资源....UserController.validate");
        String value = HttpReqUtils.getParamString(request, "value");
        User user = null;
        if (type == 1) {
            user = userService.queryWithAccount(value);
        } else if (type == 2) {
            user = userService.queryWithPhone(value);
        } else if (type == 3) {
            user = userService.queryWithEmail(value);
        }
        boolean exist = user != null;
        return ModelAndViewFactory.instance().with("exist", exist).build();
    }


}

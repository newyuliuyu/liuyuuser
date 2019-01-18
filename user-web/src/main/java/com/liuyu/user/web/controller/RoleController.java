package com.liuyu.user.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.liuyu.common.mvc.ModelAndViewFactory;
import com.liuyu.common.util.HttpReqUtils;
import com.liuyu.user.web.domain.Resource;
import com.liuyu.user.web.domain.Role;
import com.liuyu.user.web.domain.RoleType;
import com.liuyu.user.web.dto.RoleTypeDTO;
import com.liuyu.user.web.dto.SaveXResourceDTO;
import com.liuyu.user.web.service.ResourceService;
import com.liuyu.user.web.service.RoleService;
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
import java.util.Map;

/**
 * ClassName: ResourceController <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-12-27 下午1:12 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RestController
@Slf4j
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourceService resService;

    @RequestMapping("/add")
    public ModelAndView add(@RequestBody Role role,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        log.debug("获取资源....RoleController.add");
        roleService.add(role);
        return ModelAndViewFactory.instance().build();
    }

    @RequestMapping("/update")
    public ModelAndView update(@RequestBody Role role,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        log.debug("获取资源....RoleController.update");
        roleService.udpate(role);
        return ModelAndViewFactory.instance().build();
    }

    @RequestMapping("/delete")
    public ModelAndView delete(@RequestBody Role role,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        log.debug("获取资源....RoleController.delete");
        roleService.delete(role);
        return ModelAndViewFactory.instance().build();
    }

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        log.debug("获取资源....RoleController.list");

        int pageNum = HttpReqUtils.getParamInt(request, "pageNum");
        if (pageNum == 0) {
            pageNum = 1;
        }
        int pageSize = HttpReqUtils.getParamInt(request, "pageSize");
        if (pageSize == 0) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roles = roleService.queryRoles();
        PageInfo<Role> pageInfo = new PageInfo<>(roles);
        RoleType[] roleTypes = RoleType.values();
        Map<String, RoleTypeDTO> roleTypeMap = Maps.newHashMap();
        for (RoleType roleType : roleTypes) {
            RoleTypeDTO roleTypeDTO = RoleTypeDTO.converFor(roleType);
            roleTypeMap.put(roleTypeDTO.getCode(), roleTypeDTO);
        }
        return ModelAndViewFactory.instance()
                .with("pageInfo", pageInfo)
                .with("roles", roles)
                .with("roleTypeMap", roleTypeMap)
                .build();
    }

    @RequestMapping("/list/{level}")
    public ModelAndView listWithLevel(@PathVariable int level,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        log.debug("获取资源....RoleController.listWithLevel");
        List<RoleType> roleTypeList = Lists.newArrayList();
        if (level != -1) {
            RoleType[] roleTypes = RoleType.values();
            for (RoleType roleType : roleTypes) {
                if (level == roleType.getLevel()) {
                    roleTypeList.add(roleType);
                }
            }
            roleTypeList.add(RoleType.Admin);
        }
        List<Role> roles = roleService.queryRoles(roleTypeList);
        return ModelAndViewFactory.instance()
                .with("roles", roles)
                .build();
    }

    @RequestMapping("/roletype/{level}")
    public ModelAndView queryRoleTypes(@PathVariable int level,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        log.debug("获取资源....RoleController.queryRoleTypes");
        RoleType[] roleTypes = RoleType.values();
        List<RoleTypeDTO> roleTypeDTOs = Lists.newArrayList();
        for (RoleType roleType : roleTypes) {
            if (level == -1 || level == roleType.getLevel()) {
                roleTypeDTOs.add(RoleTypeDTO.converFor(roleType));
            }
        }
        if (level != -1) {
            roleTypeDTOs.add(RoleTypeDTO.converFor(RoleType.Admin));
        }
        return ModelAndViewFactory.instance().with("roleTypes", roleTypeDTOs).build();
    }

    @RequestMapping("/res/{roleId}")
    public ModelAndView roleRes(@PathVariable long roleId,
                                HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        log.debug("获取资源....RoleController.roleRes");
        List<Resource> reses = resService.querRoleResources(roleId);
        return ModelAndViewFactory.instance().with("reses", reses).build();
    }

    @RequestMapping("/saveres/{roleId}")
    public ModelAndView saveres(@PathVariable long roleId,
                                @RequestBody SaveXResourceDTO saveXResourceDTO,
                                HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        log.debug("获取资源....RoleController.roleRes");
        roleService.saveRoleResources(roleId, saveXResourceDTO.getSaveResource(), saveXResourceDTO.getDelResource());
        return ModelAndViewFactory.instance().build();
    }


}

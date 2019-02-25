package com.liuyu.bs.controller;

import com.liuyu.bs.business.ClazzMaseter;
import com.liuyu.bs.business.TeachingTeacher;
import com.liuyu.bs.service.ClazzMasterService;
import com.liuyu.bs.service.TeachingService;
import com.liuyu.bs.service.TeachingTeacherService;
import com.liuyu.common.mvc.ModelAndViewFactory;
import com.liuyu.user.web.controller.BaseController;
import com.liuyu.user.web.domain.RoleType;
import com.liuyu.user.web.dto.AddClazzMasterAndTeacherDTO;
import com.liuyu.user.web.dto.RemoveTeacherPermisDTO;
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
 * ClassName: OrgController <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-7 上午10:28 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RestController
@Slf4j
@RequestMapping("/teaching")
public class TeachingController extends BaseController {

    @Autowired
    private TeachingService teachingService;

    @Autowired
    private ClazzMasterService clazzMasterService;

    @Autowired
    private TeachingTeacherService teachingTeacherService;

    @RequestMapping("/add-clazz-master-and-teacher")
    public ModelAndView addClazzMasterAndTeacher(@RequestBody AddClazzMasterAndTeacherDTO addClazzMasterAndTeacherDTO,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".addClazzMasterAndTeacher");
        if (addClazzMasterAndTeacherDTO.isClazzmaster()) {
            teachingService.addClazzMaster(addClazzMasterAndTeacherDTO.getRoleId(),
                    addClazzMasterAndTeacherDTO.converToClazzMasterForOld(),
                    addClazzMasterAndTeacherDTO.converToClazzMasterForNew());
        } else {
            teachingService.addTeachingTeacher(addClazzMasterAndTeacherDTO.getRoleId(),
                    addClazzMasterAndTeacherDTO.converToTeachingTeacherForOld(),
                    addClazzMasterAndTeacherDTO.converToTeachingTeacherForNew());
        }
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/rmove-teacher-permis")
    public ModelAndView removeTeacherPermis(@RequestBody RemoveTeacherPermisDTO removeTeacherPermisDTO,
                                            HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".removeTeacherPermis");

        if (removeTeacherPermisDTO.getRoleType().equals(RoleType.clazzMaseter)) {
            teachingService.removeClazzMaster(removeTeacherPermisDTO.getRoleId(),
                    removeTeacherPermisDTO.converToClazzMaster());
        } else if (removeTeacherPermisDTO.getRoleType().equals(RoleType.teacher)) {
            teachingService.removeTeachingTeacher(removeTeacherPermisDTO.getRoleId(),
                    removeTeacherPermisDTO.converToTeachingTeacher());
        }

        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/clazz-master/{orgCode}")
    public ModelAndView queryClazzMaster(@PathVariable String orgCode,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".queryClazzMaster");
        List<ClazzMaseter> clazzMasters = clazzMasterService.queryClazzMasters(orgCode);
        return ModelAndViewFactory.instance()
                .with("clazzMasters", clazzMasters)
                .build();
    }

    @RequestMapping("/teaching-teacher/{orgCode}")
    public ModelAndView queryTeachingTeacher(@PathVariable String orgCode,
                                             HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".queryTeachingTeacher");
        List<TeachingTeacher> teachingTeachers = teachingTeacherService.queryTeachingTeacher(orgCode);
        return ModelAndViewFactory.instance()
                .with("teachingTeachers", teachingTeachers)
                .build();
    }

    @RequestMapping("/get/clazz/clazzmaseter/{orgCode}/{clazzCode}")
    public ModelAndView getClazzMaster(@PathVariable String orgCode,
                                       @PathVariable String clazzCode,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".getClazzMaster");
        ClazzMaseter clazzMaster = clazzMasterService.get(orgCode, clazzCode);
        return ModelAndViewFactory.instance()
                .with("clazzMaster", clazzMaster)
                .build();
    }

    @RequestMapping("/get/clazz/teachingteacher/{orgCode}/{clazzCode}")
    public ModelAndView getClazzTeachingteacher(@PathVariable String orgCode,
                                                @PathVariable String clazzCode,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".getClazzTeachingteacher");
        List<TeachingTeacher> teachingTeachers = teachingTeacherService.queryTeachingTeacherOfClazz(orgCode, clazzCode);
        return ModelAndViewFactory.instance()
                .with("teachingTeachers", teachingTeachers)
                .build();
    }


}

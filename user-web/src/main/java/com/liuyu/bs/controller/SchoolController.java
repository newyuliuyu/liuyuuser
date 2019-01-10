package com.liuyu.bs.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liuyu.bs.business.Clazz;
import com.liuyu.bs.business.Grade;
import com.liuyu.bs.business.School;
import com.liuyu.bs.business.Subject;
import com.liuyu.bs.service.SchoolService;
import com.liuyu.bs.service.UserSchoolService;
import com.liuyu.common.mvc.ModelAndViewFactory;
import com.liuyu.common.util.HttpReqUtils;
import com.liuyu.user.web.controller.BaseController;
import com.liuyu.user.web.domain.User;
import com.liuyu.user.web.dto.AKeyConfigSchoolDTO;
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
@RequestMapping("/school")
public class SchoolController extends BaseController {

    @Autowired
    private UserSchoolService userSchoolService;

    @Autowired
    private SchoolService schoolService;

    @RequestMapping("/user/school")
    public ModelAndView querySchool(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".querySchool");

        User user = getUser();
        School school = userSchoolService.queryUserSchool(user);
        return ModelAndViewFactory.instance()
                .with("school", school)
                .build();
    }

    @RequestMapping("/schools")
    public ModelAndView querySchools(HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".schools");
        User user = getUser();
        int pageNum = HttpReqUtils.getParamInt(request, "pageNum");
        if (pageNum == 0) {
            pageNum = 1;
        }
        int pageSize = HttpReqUtils.getParamInt(request, "pageSize");
        if (pageSize == 0) {
            pageSize = 10;
        }

        String schoolName = HttpReqUtils.getParamString(request, "schoolName");

        PageHelper.startPage(pageNum, pageSize);
        List<School> schools = userSchoolService.queryUserSchools(user, schoolName);
        PageInfo<School> pageInfo = new PageInfo<>(schools);
        return ModelAndViewFactory.instance()
                .with("schools", schools)
                .with("pageInfo", pageInfo)
                .build();
    }


    @RequestMapping("/subjects/{schoolCode}")
    public ModelAndView schoolSubjects(@PathVariable String schoolCode,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".schoolSubjects");
        List<Subject> subjects = schoolService.querySubjects(schoolCode);
        return ModelAndViewFactory.instance()
                .with("subjects", subjects)
                .build();
    }

    @RequestMapping("/subject/add/{schoolCode}")
    public ModelAndView schoolSubjectAdd(@PathVariable String schoolCode,
                                         @RequestBody Subject subject,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".schoolSubjectAdd");
        schoolService.addSubject(schoolCode, subject);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/subject/del/{schoolCode}/{subjectId}")
    public ModelAndView schoolSubjectDel(@PathVariable String schoolCode,
                                         @PathVariable long subjectId,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".schoolSubjectDel");
        schoolService.delSubject(subjectId);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/grades/{schoolCode}")
    public ModelAndView schoolGrades(@PathVariable String schoolCode,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".schoolGrades");
        List<Grade> grades = schoolService.queryGrade(schoolCode);
        return ModelAndViewFactory.instance()
                .with("grades", grades)
                .build();
    }

    @RequestMapping("/grade/add/{schoolCode}")
    public ModelAndView schoolGradeAdd(@PathVariable String schoolCode,
                                       @RequestBody Grade grade,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".schoolGradeAdd");
        schoolService.addGrade(schoolCode, grade);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/grade/del/{schoolCode}/{gradeId}")
    public ModelAndView schoolGradeDel(@PathVariable String schoolCode,
                                       @PathVariable long gradeId,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".schoolGradeDel");
        schoolService.delGrade(gradeId);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/clazzes/{schoolCode}")
    public ModelAndView schoolClazzes(@PathVariable String schoolCode,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".schoolGrades");
        List<Clazz> clazzes = schoolService.queryClazz(schoolCode);
        return ModelAndViewFactory.instance()
                .with("clazzes", clazzes)
                .build();
    }

    @RequestMapping("/clazz/add/{schoolCode}")
    public ModelAndView schoolClazzAdd(@PathVariable String schoolCode,
                                       @RequestBody Clazz clazz,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".schoolClazzAdd");

        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/clazz/del/{schoolCode}/{clazzCode}")
    public ModelAndView schoolClazzDel(@PathVariable String schoolCode,
                                       @PathVariable String clazzCode,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".schoolClazzDel");

        schoolService.delClazz(clazzCode);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/a-key-config-school")
    public ModelAndView aKeyConfigSchool(@RequestBody AKeyConfigSchoolDTO aKeyConfigSchoolDTO,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".aKeyConfigSchool");
        schoolService.aKeyConfigSchool(aKeyConfigSchoolDTO.getSchoolCode(),
                aKeyConfigSchoolDTO.isHasPrimarySchool(),
                aKeyConfigSchoolDTO.isHasJuniorHighSchool(),
                aKeyConfigSchoolDTO.isHasHighSchool());
        return ModelAndViewFactory.instance()
                .build();
    }


}

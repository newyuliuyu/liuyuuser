package com.liuyu.bs.controller;

import com.liuyu.bs.business.Clazz;
import com.liuyu.bs.business.Grade;
import com.liuyu.bs.business.Subject;
import com.liuyu.bs.service.OrgConfigService;
import com.liuyu.bs.service.UserOrgService;
import com.liuyu.common.mvc.ModelAndViewFactory;
import com.liuyu.common.util.ClazzNameToNumber;
import com.liuyu.user.web.controller.BaseController;
import com.liuyu.user.web.dto.AKeyConfigOrgDTO;
import com.liuyu.user.web.dto.AddClazzDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
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
@RequestMapping("/orgconfig")
public class OrgConfigController extends BaseController {

    @Autowired
    private UserOrgService userOrgService;

    @Autowired
    private OrgConfigService orgConfigService;


    @RequestMapping("/subjects/{orgCode}")
    public ModelAndView schoolSubjects(@PathVariable String orgCode,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".schoolSubjects");
        List<Subject> subjects = orgConfigService.querySubjects(orgCode);
        return ModelAndViewFactory.instance()
                .with("subjects", subjects)
                .build();
    }

    @RequestMapping("/subject/add/{orgCode}")
    public ModelAndView schoolSubjectAdd(@PathVariable String orgCode,
                                         @RequestBody Subject subject,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".schoolSubjectAdd");
        subject = orgConfigService.addSubject(orgCode, subject);
        return ModelAndViewFactory.instance()
                .with("subject", subject)
                .build();
    }

    @RequestMapping("/subject/del/{orgCode}/{subjectId}")
    public ModelAndView schoolSubjectDel(@PathVariable String orgCode,
                                         @PathVariable long subjectId,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".schoolSubjectDel");
        orgConfigService.delSubject(subjectId);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/grades/{orgCode}")
    public ModelAndView schoolGrades(@PathVariable String orgCode,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".schoolGrades");
        List<Grade> grades = orgConfigService.queryGrade(orgCode);

        grades.sort(Comparator.comparingInt(Grade::getLearnSegment).thenComparingInt(g -> ClazzNameToNumber.toNum(g.getName())));
        return ModelAndViewFactory.instance()
                .with("grades", grades)
                .build();
    }

    @RequestMapping("/grade/add/{orgCode}")
    public ModelAndView schoolGradeAdd(@PathVariable String orgCode,
                                       @RequestBody List<String> gradeNames,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".schoolGradeAdd");
        List<Grade> grades = orgConfigService.addGrade(orgCode, gradeNames);
        return ModelAndViewFactory.instance()
                .with("grades", grades)
                .build();
    }

    @RequestMapping("/grade/del/{orgCode}/{gradeId}")
    public ModelAndView schoolGradeDel(@PathVariable String orgCode,
                                       @PathVariable long gradeId,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".schoolGradeDel");
        orgConfigService.delGrade(gradeId);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/clazzes/{schoolCode}")
    public ModelAndView schoolClazzes(@PathVariable String schoolCode,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".schoolGrades");
        List<Clazz> clazzes = orgConfigService.queryClazz(schoolCode);
        clazzes.sort(Comparator.comparingInt((Clazz c) -> c.isTeachClazz() ? 1 : 0).thenComparingInt(c -> ClazzNameToNumber.toNum(c.getName())));
        return ModelAndViewFactory.instance()
                .with("clazzes", clazzes)
                .build();
    }

    @RequestMapping("/clazz/add")
    public ModelAndView schoolClazzAdd(@RequestBody AddClazzDTO addClazzDTO,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".schoolClazzAdd");
        List<Clazz> clazzes = addClazzDTO.converToClazz();
        orgConfigService.addClazzes(clazzes);
        return ModelAndViewFactory.instance()
                .with("clazzes", clazzes)
                .build();
    }

    @RequestMapping("/clazz/del/{schoolCode}/{clazzCode}")
    public ModelAndView schoolClazzDel(@PathVariable String schoolCode,
                                       @PathVariable String clazzCode,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".schoolClazzDel");

        orgConfigService.delClazz(clazzCode);
        return ModelAndViewFactory.instance()
                .build();
    }

    @RequestMapping("/a-key-config-org")
    public ModelAndView aKeyConfigOrg(@RequestBody AKeyConfigOrgDTO aKeyConfigOrgDTO,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        log.debug(this.getClass().getSimpleName() + ".aKeyConfigOrg");
        orgConfigService.aKeyConfigOrg(aKeyConfigOrgDTO.getOrgCode(),
                aKeyConfigOrgDTO.isHasPrimarySchool(),
                aKeyConfigOrgDTO.isHasJuniorHighSchool(),
                aKeyConfigOrgDTO.isHasHighSchool());
        return ModelAndViewFactory.instance()
                .build();
    }


}

package com.liuyu.user.web.controller;

import com.liuyu.common.mvc.ModelAndViewFactory;
import com.liuyu.user.web.domain.Resource;
import com.liuyu.user.web.service.ResourceService;
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
@RequestMapping("/res")
public class ResourceController extends BaseController {

    @Autowired
    private ResourceService resService;


    @RequestMapping("/add")
    public ModelAndView add(@RequestBody Resource res,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        log.debug("获取资源....ResourceController.add");
        resService.add(res);
        return ModelAndViewFactory.instance().build();
    }

    @RequestMapping("/update")
    public ModelAndView update(@RequestBody Resource res,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        log.debug("获取资源....ResourceController.update");
        resService.update(res);
        return ModelAndViewFactory.instance().build();
    }

    @RequestMapping("/delete/{resId}")
    public ModelAndView update(@PathVariable long resId,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        log.debug("获取资源....ResourceController.update");
        Resource res = Resource.builder().id(resId).build();
        resService.delete(res);
        return ModelAndViewFactory.instance().build();
    }

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        log.debug("获取资源....ResourceController.list");
        List<Resource> reses = resService.queryResources();
        return ModelAndViewFactory.instance().with("reses", reses).build();
    }

}

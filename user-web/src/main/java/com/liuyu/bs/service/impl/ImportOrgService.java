package com.liuyu.bs.service.impl;

import com.google.common.collect.Lists;
import com.liuyu.bs.business.Org;
import com.liuyu.bs.service.OrgService;
import com.liuyu.common.file.reader.FileProcess;
import com.liuyu.common.file.reader.FileProcessUtil;
import com.liuyu.common.file.reader.Rowdata;
import com.liuyu.common.progress.WebProgress;
import com.liuyu.common.spring.SpringContextUtil;
import com.liuyu.common.util.IdGenerator;
import com.liuyu.common.util.ThrowableToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * ClassName: ImportOrg <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-7 下午5:27 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class ImportOrgService implements Runnable {
    private String importFile;
    private WebProgress webProgress;
    private String userCode;
    private long timestamp;
    private String key;

    public ImportOrgService(String importFile, String userCode) {
        this(importFile, userCode, false);
    }

    public ImportOrgService(String importFile, String userCode, boolean isDebug) {
        this.importFile = importFile;
        this.userCode = userCode;
        this.timestamp = System.currentTimeMillis();
        IdGenerator idGenerator = SpringContextUtil.getBean(IdGenerator.class);

        key = idGenerator.nextId() + "";
        webProgress = new WebProgress(key, 100, isDebug);
        webProgress.start("开始进入导入");
    }

    public String getKey() {
        return key;
    }

    @Override
    public void run() {
        try {
            process();
            webProgress.info(90, "导入成功");
        } catch (Exception e) {
            log.error("导入机构数据失败", e);
            webProgress.error(90, "导入失败" + ThrowableToString.formatHtml(e));
        } finally {
            webProgress.over("导入完成");
        }
    }

    public void process() {
        webProgress.info(30, "正在导入...");
        FileProcess fileProcess = FileProcessUtil.getFileProcess(importFile);
        try {
            int num = 0;
            List<Org> orgs = Lists.newArrayList();
            while (fileProcess.next()) {
                Rowdata rowdata = fileProcess.getRowdata();
                orgs.add(createOrg(rowdata));
                num++;
                if (num % 100 == 0) {
                    webProgress.info(30, "正在了" + num + "条");
                }
            }
            webProgress.info(90, "一共导入" + num + "条");
            webProgress.info(90, "正在保存导入数据库中");
            OrgService orgService = SpringContextUtil.getBean(OrgService.class);
            orgService.adds(orgs);
            webProgress.info(90, "保存成功");
        } finally {
            fileProcess.close();
        }
    }

//    private ImportOrg createImportOrg(Rowdata rowdata) {
//        Org org = createOrg(rowdata);
//        ImportOrg importOrg = ImportOrg.builder().org(org).timestamp(timestamp).userCode(userCode).msg("").build();
//        return importOrg;
//    }

    private Org createOrg(Rowdata rowdata) {
        Org org = new Org();
        org.setCode(getStringValue(rowdata, "机构代码"));
        org.setName(getStringValue(rowdata, "机构名称"));
        org.setParentCode(getStringValue(rowdata, "上级机构代码"));
        org.setDeep(getDeep(rowdata));

        if (StringUtils.isEmpty(org.getCode())) {
            throw new RuntimeException("机构代码为空");
        }
        if (StringUtils.isEmpty(org.getName())) {
            throw new RuntimeException("机构代码[" + org.getCode() + "]机构名字为空");
        }
        if (StringUtils.isEmpty(org.getParentCode())) {
            throw new RuntimeException("机构名字[" + org.getName() + "]的上级机构代码为空");
        }
        return org;
    }

    private String getStringValue(Rowdata rowdata, String name) {
        String value = rowdata.getData(name);
        value = value == null ? "" : value;
        return value;
    }

    private int getDeep(Rowdata rowdata) {
        String value = rowdata.getData("类型");
        int deep = 0;
        if ("省".equalsIgnoreCase(value)) {
            deep = 1;
        } else if ("地市".equalsIgnoreCase(value)) {
            deep = 2;
        } else if ("区县".equalsIgnoreCase(value)) {
            deep = 3;
        } else {
            deep = 4;
        }
        return deep;
    }


}

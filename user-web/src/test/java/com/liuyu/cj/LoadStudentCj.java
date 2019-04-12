package com.liuyu.cj;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.liuyu.common.json.Json2;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.util.Assert;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ClassName: LoadStudentCj <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-3-7 下午5:23 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class LoadStudentCj {

    @Test
    public void test() throws Exception {
        String mainDir = "/home/liuyu/test/data/xixiancj";
        Map<String, StudentCj> studentCjMap = createStudent(mainDir);
        readSubjectCj(studentCjMap, mainDir);
        List<StudentCj> studentCjs = calculateAllSubjectScore(studentCjMap.values());

        Map<String, Object> indexMapData = Maps.newHashMap();
        indexMapData.put("_index", "student2");
        indexMapData.put("_type", "cj");
        Map<String, Object> indexMap = Maps.newHashMap();
        indexMap.put("index", indexMapData);
        String indexJsonStr = Json2.toJson(indexMap);
        System.out.println(indexJsonStr);
        Path path = Paths.get(mainDir, "cj.json");
        File jsonFile = path.toFile();

        studentCjs.stream().forEach(x -> {
            String json = Json2.toJson(x);
            System.out.println(json);
            try {
                FileUtils.write(jsonFile, indexJsonStr + "\n", Charsets.UTF_8, true);
                FileUtils.write(jsonFile, json + "\n", Charsets.UTF_8, true);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        System.out.println();
    }

    @Test
    public void test6() throws Exception {
        String mainDir = "/home/liuyu/test/data/xixiancj";
        Map<String, StudentCj> studentCjMap = createStudent(mainDir);
        readSubjectCj(studentCjMap, mainDir);
        List<StudentCj> studentCjs = calculateAllSubjectScore(studentCjMap.values());

        Map<String, Object> indexMapData = Maps.newHashMap();
        indexMapData.put("_index", "student2");
        indexMapData.put("_type", "cj");
        Map<String, Object> indexMap = Maps.newHashMap();
        indexMap.put("index", indexMapData);
        String indexJsonStr = Json2.toJson(indexMap);
        System.out.println(indexJsonStr);
        Path path = Paths.get(mainDir, "cj.json");
        File jsonFile = path.toFile();
        Path filePath = Paths.get("/home/liuyu/test/data", "mysqldata2.txt");
        File file = filePath.toFile();
        studentCjs.stream().forEach(x -> {
            List<SubjectCj> subjectCjs = x.getSubjectCjs();
            subjectCjs.forEach(subjectCj -> {
                List<ItemCj> itemCjs = subjectCj.getItemCjs();
                if(itemCjs==null){
                    return;
                }
                itemCjs.forEach(itemCj -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append("1").append(",")
                            .append("1").append(",")
                            .append(subjectCj.getSubjectName()).append(",")
                            .append(x.getZkzh()).append(",")
                            .append(itemCj.getItemName()).append(",")
                            .append("1").append(",")
                            .append("0").append(",")
                            .append(itemCj.getScore()).append(",")
                            .append(itemCj.getSelectItem()).append("\n");
                    try {
                        FileUtils.write(file, sb.toString(), Charsets.UTF_8, true);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                });


                //            `examId` INT(11) NOT NULL COMMENT '考试ID',
//                    `subjectId` INT(11) NOT NULL COMMENT '科目ID',
//                    `subjectName` VARCHAR(30) DEFAULT '' COMMENT '科目ID',
//                    `zkzh` VARCHAR(30) DEFAULT '' COMMENT '准考证号',
//                    `itemName` VARCHAR(30) NOT NULL COMMENT '题目名称',
//                    `versionNum` INT(11) DEFAULT '0' COMMENT '数据版本号',
//                    `qk` TINYINT(1) DEFAULT '0' COMMENT '学生是否缺考',
//                    `score` DOUBLE DEFAULT NULL,
//                    `selectedItem` VARCHAR(10) DEFAULT NULL,
            });
        });
        System.out.println();
    }



    private List<StudentCj> calculateAllSubjectScore(Collection<StudentCj> studentCjs) {
        studentCjs.stream().forEach(x -> {
            double score = 0;
            double kgScore = 0;
            double zgScore = 0;
            for (SubjectCj s : x.getSubjectCjs()) {
                score += s.getScore();
                kgScore += s.getKgScore();
                zgScore += s.getZgScore();
            }
            SubjectCj subjectCj = SubjectCj.builder()
                    .score(score)
                    .kgScore(kgScore)
                    .zgScore(zgScore)
                    .qk(false)
                    .subjectName("总分")
                    .build();
            x.appendSubjectCj(subjectCj);
        });
        return Lists.newArrayList(studentCjs);
    }


    @Test
    public void test2() throws Exception {
        System.out.println(Boolean.parseBoolean("0"));
        System.out.println(Boolean.parseBoolean("1"));
    }

    private Map<String, StudentCj> createStudent(String bmkFileDir) throws Exception {
        File bmkFile = getBMKFile(bmkFileDir);
        List<String> bmkDataset = FileUtils.readLines(bmkFile, Charsets.UTF_8);
        //name,code,zkzh,schoolCode,schoolName,classCode,className,wl,cityCode,cityName,countyCode,countyName
        Map<School, School> schoolMap = Maps.newHashMap();
        Map<Clazz, Clazz> clazzMap = Maps.newHashMap();

        Map<String, StudentCj> studentCjMap = bmkDataset.stream().skip(1)
                .map(studentData -> {
                    StudentCj studentCj = createStudentCj(studentData);
                    School school = schoolMap.get(studentCj.getSchool());
                    if (school == null) {
                        schoolMap.put(studentCj.getSchool(), studentCj.getSchool());
                    } else {
                        studentCj.setSchool(school);
                    }
                    Clazz clazz = clazzMap.get(studentCj.getClazz());
                    if (clazz == null) {
                        clazzMap.put(studentCj.getClazz(), studentCj.getClazz());
                    } else {
                        studentCj.setClazz(clazz);
                    }
                    return studentCj;
                }).collect(Collectors.toMap(x -> x.getZkzh(), y -> y));
        return studentCjMap;
    }

    private StudentCj createStudentCj(String studentData) {
        String[] data = studentData.split(",");
        School school = School.builder().code(data[3]).name(data[4]).build();
        Clazz clazz = Clazz.builder().code(data[5]).name(data[6]).build();
        StudentCj studentCj = StudentCj.builder()
                .name(data[0])
                .code(data[1])
                .zkzh(data[2])
                .examId(1)
                .wl(Integer.parseInt(data[7]))
                .school(school)
                .clazz(clazz)
                .build();
        return studentCj;
    }

    private File getBMKFile(String dir) {
        Path bmkPath = Paths.get(dir, "bmk.csv");
        File bmkFile = bmkPath.toFile();
        Assert.isTrue(bmkFile.exists(), "报名库文件不存在");
        return bmkFile;
    }


    private void readSubjectCj(Map<String, StudentCj> studentCjMap, String cjDir) throws Exception {
        File[] cjFiles = getCjFile(cjDir);
        for (File file : cjFiles) {
            readSubjectCj(studentCjMap, file);
        }
    }

    private void readSubjectCj(Map<String, StudentCj> studentCjMap, File cjFile) throws Exception {
        List<String> cjDataset = FileUtils.readLines(cjFile, Charsets.UTF_8);
        //zkzh,teachClazzCode,teachClazzName,qk,score,kgscore,zgscore,1得分,1选项
        String subjectName = cjFile.getName().replaceAll("成绩.csv", "");
        String[] filed = cjDataset.get(0).split(",");
        long count = cjDataset.stream().skip(1).map(cjData -> createSubjectCj(cjData, subjectName, filed, studentCjMap)).count();
        System.out.println(subjectName + "===============" + count);
    }

    private SubjectCj createSubjectCj(String cjData, String subjectName, String[] filed, Map<String, StudentCj> studentCjMap) {
        String[] data = cjData.split(",");
        String zkzh = data[0];

        List<ItemCj> itemCjs = createItemCjs(filed, data);
        SubjectCj subjectCj = SubjectCj.builder()
                .subjectName(subjectName)
                .qk(data[3].equalsIgnoreCase("0") ? false : true)
                .score(Double.parseDouble(data[4]))
                .kgScore(Double.parseDouble(data[5]))
                .zgScore(Double.parseDouble(data[6]))
                .itemCjs(itemCjs)
                .build();

        StudentCj studentCj = studentCjMap.get(zkzh);
        if (studentCj != null) {
            studentCj.appendSubjectCj(subjectCj);
        }
        return subjectCj;
    }

    private List<ItemCj> createItemCjs(String[] filed, String[] data) {
        List<ItemCj> itemCjs = Lists.newArrayList();
        int filedLength = filed.length;
        for (int i = 7; i < filedLength; i++) {
            ItemCj itemCj = ItemCj.builder()
                    .itemName(filed[i].replaceAll("得分", ""))
                    .score(Double.parseDouble(data[i]))
                    .build();
            if (i + 1 < filedLength && filed[i + 1].contains("选项")) {
                itemCj.setSelectItem(data[i + 1]);
                i++;
            }
            itemCjs.add(itemCj);
        }
        return itemCjs;
    }

    private File[] getCjFile(String dir) {
        Path cjDir = Paths.get(dir);
        File cjDirFile = cjDir.toFile();
        Assert.isTrue(cjDirFile.exists(), "成绩目录文件不存在");
        File[] cjFiles = cjDirFile.listFiles((dir1, name) -> {
            return name.contains("成绩.csv");
        });
        return cjFiles;
    }


}

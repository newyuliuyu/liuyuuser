package com.liuyu.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.base.Charsets;
import com.liuyu.user.web.UserWebApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ClassName: JdbcTemplateTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-3-19 下午2:14 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserWebApplication.class)
@Slf4j
public class JdbcTemplateTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource ds;

    @Test
    public void test03() throws Exception {

        DruidDataSource ds2 = (DruidDataSource) ds;
        System.out.println(ds2.getUrl());
        System.out.println(ds2.getUsername());
        System.out.println(ds2.getPassword());
        System.out.println(ds2.getDriverClassName());


        Connection con = ds.getConnection();

        String file = "/home/liuyu/test/data/mysqldata.txt";
        String sql = "LOAD DATA LOCAL INFILE '" + file + "' INTO TABLE fact_itemscore " +
                "FIELDS TERMINATED BY ',' " +
                "LINES TERMINATED BY '\n' " +
                "(examId,subjectId,subjectName,zkzh,itemName,versionNum,qk,score,selectedItem)";
//
        PreparedStatement pst = con.prepareStatement(sql);
        System.out.println();
    }

    @Test
    public void test01() throws Exception {

        jdbcTemplate.execute(new ConnectionCallback<Boolean>() {
            @Override
            public Boolean doInConnection(Connection con) throws SQLException, DataAccessException {
                String file = "/home/liuyu/test/data/mysqldata.txt";
                String sql = "LOAD DATA LOCAL INFILE '" + file + "' INTO TABLE fact_itemscore " +
                        "FIELDS TERMINATED BY ',' " +
                        "LINES TERMINATED BY '\n' " +
                        "(examId,subjectId,subjectName,zkzh,itemName,versionNum,qk,score,selectedItem)";

                PreparedStatement pst = con.prepareStatement(sql);
//                pst.execute();
//                pst.close();
                return true;
            }
        });
    }

    @Test
    public void test02() throws Exception {
        long b = System.nanoTime();
//        String url = "jdbc:mysql://192.168.1.251:3306/liuyu_user?autoReconnectForPools=true&allowMultiQueries=true&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&allowMultiQueries=true&rewriteBatchedStatements=true";
//        String user = "root";
//        String pwd = "newa_newc";
        String url = "jdbc:mysql://rm-wz9cwern4k7l056r4o.mysql.rds.aliyuncs.com:3306/test?autoReconnectForPools=true&allowMultiQueries=true&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&allowMultiQueries=true&rewriteBatchedStatements=true";
        String user = "easytnt";
        String pwd = "Easytnt001@#";
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, user, pwd);

        String file = "/home/liuyu/test/data/mysqldata2.txt";
        String sql = "LOAD DATA LOCAL INFILE '" + file + "' INTO TABLE fact_itemscore " +
                "FIELDS TERMINATED BY ',' " +
                "LINES TERMINATED BY '\n' " +
                "(examId,subjectId,subjectName,zkzh,itemName,versionNum,qk,score,selectedItem)";
//
        PreparedStatement pst = con.prepareStatement(sql);
        pst.execute();
        pst.close();
        con.close();
        long e = System.nanoTime();
        System.out.println((e-b)*1.0/1000000);
    }

    @Test
    public void writeData() throws Exception {
        Path filePath = Paths.get("/home/liuyu/test/data", "mysqldata.txt");
        File file = filePath.toFile();
        for (int i = 0; i < 1000; i++) {
            StringBuilder sb = new StringBuilder();

            sb.append("1").append(",")
                    .append("1").append(",")
                    .append("1").append(",")
                    .append(i).append(",")
                    .append(i).append(",")
                    .append("1").append(",")
                    .append("0").append(",")
                    .append(i).append(",")
                    .append("A").append("\n");

//            `examId` INT(11) NOT NULL COMMENT '考试ID',
//                    `subjectId` INT(11) NOT NULL COMMENT '科目ID',
//                    `subjectName` VARCHAR(30) DEFAULT '' COMMENT '科目ID',
//                    `zkzh` VARCHAR(30) DEFAULT '' COMMENT '准考证号',
//                    `itemName` VARCHAR(30) NOT NULL COMMENT '题目名称',
//                    `versionNum` INT(11) DEFAULT '0' COMMENT '数据版本号',
//                    `qk` TINYINT(1) DEFAULT '0' COMMENT '学生是否缺考',
//                    `score` DOUBLE DEFAULT NULL,
//                    `selectedItem` VARCHAR(10) DEFAULT NULL,

            FileUtils.write(file, sb.toString(), Charsets.UTF_8, true);
        }

    }
}

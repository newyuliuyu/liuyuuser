package com.liuyu.user.web;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.liuyu.common.json.Json2;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

/**
 * ClassName: AtomicOperateDBTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-2-25 下午3:48 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class AtomicOperateDBTest {

    @Test
    public void singleThreadTest() throws Exception {
        new MyRanable("").run();
    }

    @Test
    public void multiThreadTest() throws Exception {
        int threadNum = 20;
        Thread[] threads = new Thread[threadNum];

        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(new MyRanable("name---" + i));
            threads[i].start();
        }

        for (int i = 0; i < threadNum; i++) {
            threads[i].join();
        }
        log.debug("运行完毕");
    }

    class MyRanable implements Runnable {
        private String name;

        public MyRanable(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                int status = execut();
                while (status != 0) {
                    status = execut();
                }
                log.debug("[线程={}]==线程退出程序", name);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        /**
         * @return 0 表示没有任务 1 表示获取任务成功 2 表示获取任务失败
         * @throws Exception
         */
        private int execut() throws Exception {
            Connection con = getConnection();
            Map<String, Integer> task = getTask(con);
            if (task == null) {
                return 0;
            }

            int id = task.get("id");
            int updateNumber = updateTaskStat(con, id);
            if (updateNumber == 1) {
                return 1;
            } else {
                return 2;
            }
        }

        private int updateTaskStat(Connection con, int id) throws Exception {
            String sql = "update t_test set v=v+1 where v=1 and id=" + id;
            PreparedStatement pst = con.prepareStatement(sql);
            int updateNumber = pst.executeUpdate();
            pst.close();
            con.close();
            log.debug("[线程={}]==修改数据ID为{}，修改了{}条数据", name, id, updateNumber);
            return updateNumber;
        }

        private Map<String, Integer> getTask(Connection con) throws Exception {
            String sql = "select id,v from t_test where v=1 limit 1";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            Map<String, Integer> result = null;
            if (rs.next()) {
                result = Maps.newHashMap();
                result.put("id", rs.getInt(1));
                result.put("v", rs.getInt(2));
            } else {
                log.debug("[线程={}]==没有数据不进行修改", name);
            }
            rs.close();
            pst.close();
            return result;
        }


        private Connection getConnection() throws Exception {
            String url = "jdbc:mysql://192.168.1.251:3306/liuyu_user?autoReconnectForPools=true&allowMultiQueries=true&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&allowMultiQueries=true&rewriteBatchedStatements=true";
            String user = "root";
            String pwd = "newa_newc";
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, pwd);
            return conn;
        }
    }

    @Test
    public void loadDataToFile() throws Exception {
        String url = "jdbc:mysql://192.168.1.251:3306/ezdata5?autoReconnectForPools=true&allowMultiQueries=true&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&allowMultiQueries=true&rewriteBatchedStatements=true";
        String user = "root";
        String pwd = "newa_newc";
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, user, pwd);

        String sql = "SELECT examId,subjectName,CODE,NAME,schoolCode,schoolName,classCode,className,score FROM fact_score a WHERE a.examId=5";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        Map<String, Object> indexMapData = Maps.newHashMap();
        indexMapData.put("_index", "student");
        indexMapData.put("_type", "cj");
        Map<String, Object> indexMap = Maps.newHashMap();
        indexMap.put("index", indexMapData);
        String indexJsonStr = Json2.toJson(indexMap);
        System.out.println(indexJsonStr);
        File jsonFile = new File("/home/liuyu/test/data/cj1.json");
        while (rs.next()) {
            Map<String, Object> data = Maps.newHashMap();
            data.put("examId", rs.getLong(1));
            data.put("subjectName", rs.getString(2));
            data.put("code", rs.getString(3));
            data.put("name", rs.getString(4));
            data.put("schoolCode", rs.getString(5));
            data.put("schoolName", rs.getString(6));
            data.put("clazzCode", rs.getString(7));
            data.put("clazzName", rs.getString(8));
            data.put("score", rs.getDouble(9));
            String json = Json2.toJson(data);
            System.out.println(json);
            FileUtils.write(jsonFile, indexJsonStr + "\n", Charsets.UTF_8, true);
            FileUtils.write(jsonFile, json + "\n", Charsets.UTF_8, true);
        }
        rs.close();
        pst.close();
        con.close();
    }

}

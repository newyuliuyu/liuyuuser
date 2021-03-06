
DROP TABLE IF EXISTS b_org;
CREATE TABLE b_org(
  id INT NOT NULL AUTO_INCREMENT COMMENT'ID',
  CODE VARCHAR(32) NOT NULL COMMENT'机构代码',
  NAME VARCHAR(20) NOT NULL COMMENT'机构名称',
  deep TINYINT NOT NULL COMMENT'机构类别 1 省 2地市 3 区县 4 学校',
  parentCode VARCHAR(32) NOT NULL COMMENT'机构的父级代码',
  PRIMARY KEY id(id),
  UNIQUE KEY CODE(CODE),
  KEY parentCode(parentCode)
) ENGINE=INNODB COMMENT'机构表';

-- DROP TABLE IF EXISTS b_import_org_cache;
-- CREATE TABLE b_import_org_cache(
--   id INT NOT NULL AUTO_INCREMENT COMMENT'ID',
--   CODE VARCHAR(32) NOT NULL COMMENT'机构代码',
--   NAME VARCHAR(20) NOT NULL COMMENT'机构名称',
--   deep TINYINT NOT NULL COMMENT'机构类别 1 省 2地市 3 区县 4 学校',
--   parentCode VARCHAR(32) NOT NULL COMMENT'机构的父级代码',
--   userCode VARCHAR(32) NOT NULL COMMENT'导入者id',
--   TIMESTAMP BIGINT NOT NULL COMMENT'时间戳',
--   PRIMARY KEY id(id),
--   UNIQUE KEY codeAndTimeStamp(userCode,TIMESTAMP)
-- --   key parentCode(parentCode)
-- ) ENGINE=INNODB COMMENT'机构导入数据临时表';

DROP TABLE IF EXISTS b_org_subject;
CREATE TABLE b_org_subject(
  id INT NOT NULL AUTO_INCREMENT COMMENT'ID',
  orgCode VARCHAR(32) NOT NULL COMMENT'学校代码',
  subjectName VARCHAR(20) NOT NULL COMMENT'机构名称',
  PRIMARY KEY id(id)
) ENGINE=INNODB COMMENT'组织(包含学校，区县，地市，省市)的科目表';

DROP TABLE IF EXISTS b_org_grade;
CREATE TABLE b_org_grade(
  id BIGINT NOT NULL COMMENT'ID',
  orgCode VARCHAR(32) NOT NULL COMMENT'学校代码',
  NAME VARCHAR(20) NOT NULL COMMENT'年级名字',
  enterSchoolYear INT NOT NULL COMMENT'入学年份',
  learnSegment TINYINT NOT NULL COMMENT'学段 1 小学 2 初中 3 高中',
  PRIMARY KEY id(id)
) ENGINE=INNODB COMMENT'组织(包含学校，区县，地市，省市)的年级表';


DROP TABLE IF EXISTS b_clazz;
CREATE TABLE b_clazz(
  id INT NOT NULL AUTO_INCREMENT COMMENT'ID',
  schoolCode VARCHAR(32) NOT NULL COMMENT'学校代码',
  CODE VARCHAR(32) NOT NULL COMMENT'班级代码',
  NAME VARCHAR(20) NOT NULL COMMENT'班级名称',
  wl TINYINT NOT NULL COMMENT'文理标志 0 不文文理 1 理科 2 文科',
  teachClazz BOOLEAN DEFAULT FALSE COMMENT'是否教学班',
  subjectName VARCHAR(20) DEFAULT '' COMMENT'什么科目的教学班',
  grade BIGINT NOT NULL COMMENT'年级',
  PRIMARY KEY id(id),
  UNIQUE KEY CODE(CODE)
) ENGINE=INNODB COMMENT'班级';


DROP TABLE IF EXISTS b_teacher;
CREATE TABLE b_teacher(
  id INT NOT NULL AUTO_INCREMENT COMMENT'ID',
  orgCode VARCHAR(32) NOT NULL COMMENT'机构代码',
  `name` VARCHAR(32) NOT NULL COMMENT'教师名称',
  `code` VARCHAR(32) NOT NULL COMMENT'教师唯一编码',
  account VARCHAR(32) NOT NULL COMMENT'教师帐号',
  phone VARCHAR(11) DEFAULT '' COMMENT'电话',
	email VARCHAR(30) DEFAULT '' COMMENT'邮箱',
  PRIMARY KEY id(id),
  KEY CODE(CODE)
) ENGINE=INNODB COMMENT'教师表';


DROP TABLE IF EXISTS b_teaching_teacher;
CREATE TABLE b_teaching_teacher(
  id INT NOT NULL AUTO_INCREMENT COMMENT'ID',
  orgCode VARCHAR(32) NOT NULL COMMENT'所属机构代码',
  teacherCode VARCHAR(32) NOT NULL COMMENT'老师代码',
  gradeId BIGINT NOT NULL COMMENT'年级代码',
  clazzCode VARCHAR(32) NOT NULL COMMENT'班级代码',
  subjectName VARCHAR(32) NOT NULL COMMENT'科目名称',
  PRIMARY KEY id(id)
) ENGINE=INNODB COMMENT'老师任课情况表';

DROP TABLE IF EXISTS b_clazzmaster;
CREATE TABLE b_clazzmaster(
  id INT NOT NULL AUTO_INCREMENT COMMENT'ID',
  orgCode VARCHAR(32) NOT NULL COMMENT'所属机构代码',
  teacherCode VARCHAR(32) NOT NULL COMMENT'老师代码',
  gradeId BIGINT NOT NULL COMMENT'年级代码',
  clazzCode VARCHAR(32) NOT NULL COMMENT'班级代码',
  PRIMARY KEY id(id)
) ENGINE=INNODB COMMENT'班主任';

DROP TABLE IF EXISTS b_schoolmaster;
CREATE TABLE b_schoolmaster(
  id INT NOT NULL AUTO_INCREMENT COMMENT'ID',
  orgCode VARCHAR(32) NOT NULL COMMENT'所属机构代码',
  teacherCode VARCHAR(32) NOT NULL COMMENT'老师代码',
  roleId BIGINT NOT NULL COMMENT'角色ID',
  PRIMARY KEY id(id)
) ENGINE=INNODB COMMENT'校长';

DROP TABLE IF EXISTS b_gradeMaster;
CREATE TABLE b_gradeMaster(
  id INT NOT NULL AUTO_INCREMENT COMMENT'ID',
  orgCode VARCHAR(32) NOT NULL COMMENT'所属机构代码',
  teacherCode VARCHAR(32) NOT NULL COMMENT'老师代码',
  gradeId BIGINT NOT NULL COMMENT'年级代码',
  PRIMARY KEY id(id)
) ENGINE=INNODB COMMENT'年级主任';

DROP TABLE IF EXISTS b_LPGroupLeader;
CREATE TABLE b_LPGroupLeader(
  id INT NOT NULL AUTO_INCREMENT COMMENT'ID',
  orgCode VARCHAR(32) NOT NULL COMMENT'所属机构代码',
  teacherCode VARCHAR(32) NOT NULL COMMENT'老师代码',
  gradeId BIGINT NOT NULL COMMENT'年级代码',
  subjectName VARCHAR(32) NOT NULL COMMENT'科目名字',
  PRIMARY KEY id(id)
) ENGINE=INNODB COMMENT'备课组长';


DROP TABLE IF EXISTS b_researchStaff;
CREATE TABLE b_researchStaff(
  id INT NOT NULL AUTO_INCREMENT COMMENT'ID',
  orgCode VARCHAR(32) NOT NULL COMMENT'所属机构代码',
  teacherCode VARCHAR(32) NOT NULL COMMENT'老师代码',
  gradeId BIGINT NOT NULL COMMENT'年级代码',
  subjectName VARCHAR(32) NOT NULL COMMENT'科目名称',
  roleId BIGINT NOT NULL COMMENT'角色ID',
  PRIMARY KEY id(id)
) ENGINE=INNODB COMMENT'教研员';

DROP TABLE IF EXISTS b_TARS;
CREATE TABLE b_TARS(
  id INT NOT NULL AUTO_INCREMENT COMMENT'ID',
  orgCode VARCHAR(32) NOT NULL COMMENT'所属机构代码',
  teacherCode VARCHAR(32) NOT NULL COMMENT'老师代码',
  learnSegment TINYINT NOT NULL COMMENT'学段 1 小学 2 初中 3 高中',
  roleId BIGINT NOT NULL COMMENT'角色ID',
  PRIMARY KEY id(id)
) ENGINE=INNODB COMMENT'教研室主任';

DROP TABLE IF EXISTS b_SFE;
CREATE TABLE b_SFE(
  id INT NOT NULL AUTO_INCREMENT COMMENT'ID',
  orgCode VARCHAR(32) NOT NULL COMMENT'所属机构代码',
  teacherCode VARCHAR(32) NOT NULL COMMENT'老师代码',
  roleId BIGINT NOT NULL COMMENT'角色ID',
  PRIMARY KEY id(id)
) ENGINE=INNODB COMMENT'教育局领导';

DROP TABLE IF EXISTS b_teacher_admin;
CREATE TABLE b_teacher_admin(
  id INT NOT NULL AUTO_INCREMENT COMMENT'ID',
  orgCode VARCHAR(32) NOT NULL COMMENT'所属机构代码',
  teacherCode VARCHAR(32) NOT NULL COMMENT'老师代码',
  roleId BIGINT NOT NULL COMMENT'角色ID',
  PRIMARY KEY id(id)
) ENGINE=INNODB COMMENT'管理员';


DROP TABLE IF EXISTS b_student;
CREATE TABLE b_student(
  id INT NOT NULL AUTO_INCREMENT COMMENT'ID',
  schoolCode VARCHAR(32) NOT NULL COMMENT'所属机构代码',
  clazzCode VARCHAR(32) NOT NULL COMMENT'老师代码',
  gradeId BIGINT NOT NULL COMMENT'年级id',
  NAME VARCHAR(50) NOT NULL COMMENT'学生名称',
  studentId VARCHAR(32) default '' COMMENT'学号',
  account VARCHAR(32) NOT NULL COMMENT'学生帐号',
  zkzh VARCHAR(32) DEFAULT '' COMMENT'准考证号',
  PRIMARY KEY id(id)
) ENGINE=INNODB COMMENT'学生信息';
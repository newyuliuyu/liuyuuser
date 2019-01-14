
DROP TABLE IF EXISTS u_user;
CREATE TABLE u_user(
	id BIGINT NOT NULL COMMENT'用户ID，由程序来维护ID的生成',
	username VARCHAR(20) NOT NULL COMMENT'用户名',
	phone VARCHAR(11) DEFAULT '' COMMENT'电话',
	email VARCHAR(30) DEFAULT '' COMMENT'邮箱',
	pwd VARCHAR(20) NOT NULL COMMENT'密码 md5加密',
	realname VARCHAR(50) DEFAULT '' COMMENT'用户的真实名字',
	PRIMARY KEY id(id),
	KEY username(username),
	KEY phone(phone),
	KEY email(email)
) ENGINE=INNODB COMMENT'用户表';

DROP TABLE IF EXISTS u_role;
CREATE TABLE u_role(
	id BIGINT NOT NULL COMMENT'角色ID',
	NAME VARCHAR(20) NOT NULL COMMENT'角色名字',
	roletype VARCHAR(20) DEFAULT '' COMMENT'角色类型',
	systembuiltin TINYINT(1) DEFAULT 0 COMMENT'是否为系统内置角色',
	PRIMARY KEY id(id)
) ENGINE=INNODB COMMENT'角色表';

-- DROP TABLE IF EXISTS u_role_type;
-- CREATE TABLE u_role_type(
-- 	CODE VARCHAR(20) NOT NULL COMMENT'角色类型代码',
-- 	NAME VARCHAR(20) NOT NULL COMMENT'角色类型名字',
-- 	LEVEL INT NOT NULL COMMENT'角色类型级别',
-- 	PRIMARY KEY CODE(CODE)
-- ) ENGINE=INNODB COMMENT'角色类型';
--
-- INSERT INTO u_role_type(CODE,NAME,LEVEL) VALUES('parents','家长',100);
-- INSERT INTO u_role_type(CODE,NAME,LEVEL) VALUES('student','学生',100);
--
-- INSERT INTO u_role_type(CODE,NAME,LEVEL) VALUES('teacher','教师',1);
-- INSERT INTO u_role_type(CODE,NAME,LEVEL) VALUES('cLazzMaseter','班主任',1);
-- INSERT INTO u_role_type(CODE,NAME,LEVEL) VALUES('LPGroupLeader','备课组长',1);
-- INSERT INTO u_role_type(CODE,NAME,LEVEL) VALUES('gradeMaseter','年级主任',1);
-- INSERT INTO u_role_type(CODE,NAME,LEVEL) VALUES('schoolMaseter','校长',1);
-- INSERT INTO u_role_type(CODE,NAME,LEVEL) VALUES('Admin','管理员',1);
--
-- INSERT INTO u_role_type(CODE,NAME,LEVEL) VALUES('researchStaff','教研员',2);
-- INSERT INTO u_role_type(CODE,NAME,LEVEL) VALUES('TARS','教研室主任',2);
-- INSERT INTO u_role_type(CODE,NAME,LEVEL) VALUES('SFE','教育局局长',2);
--
--
--
-- INSERT INTO u_role_type(CODE,NAME,LEVEL) VALUES('Root','超级管理员',1000);




DROP TABLE IF EXISTS u_user_x_role;
CREATE TABLE u_user_x_role(
	id INT NOT NULL AUTO_INCREMENT COMMENT'资源ID',
	userId BIGINT  NOT NULL COMMENT'资源名字',
	roleId BIGINT NOT NULL COMMENT'角色ID',
	PRIMARY KEY id(id),
	KEY userId(userId),
	KEY roleId(roleId)
) ENGINE=INNODB COMMENT'用户角色关联表';



DROP TABLE IF EXISTS u_resource;
CREATE TABLE u_resource(
	id BIGINT NOT NULL COMMENT'资源ID',
	NAME VARCHAR(20) NOT NULL COMMENT'资源名字',
	displayText TEXT  COMMENT'资源显示的html内容',
	elementId VARCHAR(30) DEFAULT '' COMMENT'页面显示元素ID',
  groups VARCHAR(20) DEFAULT '' COMMENT'资源分组，不同页面显示的内容',
  smallGroup VARCHAR(20) DEFAULT '' COMMENT'小组用于显示再同一个页面不同位置',
  parentId BIGINT NOT NULL COMMENT'父级ID',
  orderNum INT DEFAULT 0 COMMENT'显示顺序',
  resType VARCHAR(20) DEFAULT '' COMMENT'资源类型',
	PRIMARY KEY id(id)
) ENGINE=INNODB COMMENT'资源表';

DROP TABLE IF EXISTS u_role_x_resource;
CREATE TABLE u_role_x_resource(
	id INT NOT NULL AUTO_INCREMENT COMMENT'资源ID',
	roleId BIGINT  COMMENT'角色ID',
	resId BIGINT  COMMENT'资源ID',
	PRIMARY KEY id(id),
	KEY roleId(roleId),
	KEY resId(resId)
) ENGINE=INNODB COMMENT'角色资源关联表';

DROP TABLE IF EXISTS u_user_x_resource;
CREATE TABLE u_user_x_resource(
	id INT NOT NULL AUTO_INCREMENT COMMENT'资源ID',
	userId BIGINT  NOT NULL COMMENT'资源名字',
	resId BIGINT  COMMENT'资源ID',
	roleId BIGINT DEFAULT 0 COMMENT'角色ID,用户资源继承与角色 0表示用户直接添加资源的',
	PRIMARY KEY id(id),
	KEY userId(userId),
	KEY resId(resId),
	KEY roleId(roleId)
) ENGINE=INNODB COMMENT'用户资源关联表';


INSERT INTO `u_resource`(id,NAME,displayText,elementId,groups,smallGroup,parentId,orderNum,resType) VALUES(1,'主页','主页','home','user-main-menu','',0,1,'Menu');
INSERT INTO `u_resource`(id,NAME,displayText,elementId,groups,smallGroup,parentId,orderNum,resType) VALUES(2,'机构','机构','org','user-main-menu','',0,2,'Menu');
INSERT INTO `u_resource`(id,NAME,displayText,elementId,groups,smallGroup,parentId,orderNum,resType) VALUES(3,'结构配置','结构配置','orgConfig','user-main-menu','',0,3,'Menu');
INSERT INTO `u_resource`(id,NAME,displayText,elementId,groups,smallGroup,parentId,orderNum,resType) VALUES(5,'教师','教师','teacher','user-main-menu','',0,5,'Menu');
-- INSERT INTO `u_resource`(id,NAME,displayText,elementId,groups,smallGroup,parentId,orderNum,resType) VALUES(4,'年级','年级','grade','user-main-menu','',0,4,'Menu');
-- INSERT INTO `u_resource`(id,NAME,displayText,elementId,groups,smallGroup,parentId,orderNum,resType) VALUES(5,'班级','班级','clazz','user-main-menu','',0,5,'Menu');
INSERT INTO `u_resource`(id,NAME,displayText,elementId,groups,smallGroup,parentId,orderNum,resType) VALUES(6,'学生','学生','student','user-main-menu','',0,6,'Menu');
INSERT INTO `u_resource`(id,NAME,displayText,elementId,groups,smallGroup,parentId,orderNum,resType) VALUES(7,'教学','教学','teaching','user-main-menu','',0,7,'Menu');

INSERT INTO `u_resource`(id,NAME,displayText,elementId,groups,smallGroup,parentId,orderNum,resType) VALUES(8,'用户-角色-资源','<div class="menu-item-text"><i class="icon-cogs">&nbsp;用户-角色-资源</i><i class="icon-chevron-down float-right-u"></i></div>','','home-menu','',0,1,'Menu');
INSERT INTO `u_resource`(id,NAME,displayText,elementId,groups,smallGroup,parentId,orderNum,resType) VALUES(9,'资源管理','<div class="menu-item-text"><i class="icon-th-large">&nbsp;资源管理</i></div>','resourceMenu','home-menu','',8,1,'Menu');
INSERT INTO `u_resource`(id,NAME,displayText,elementId,groups,smallGroup,parentId,orderNum,resType) VALUES(10,'角色管理','<div class="menu-item-text"><i class="icon-group">&nbsp;角色管理</i></div>','roleMenu','home-menu','',8,2,'Menu');
INSERT INTO `u_resource`(id,NAME,displayText,elementId,groups,smallGroup,parentId,orderNum,resType) VALUES(11,'用户管理','<div class="menu-item-text"><i class="icon-user">&nbsp;用户管理</i></div>','userMenu','home-menu','',8,3,'Menu');

INSERT INTO `u_resource`(id,NAME,displayText,elementId,groups,smallGroup,parentId,orderNum,resType) VALUES(100,'组织菜单管理','组织菜单管理','','','',0,0,'Menu');
INSERT INTO `u_resource`(id,NAME,displayText,elementId,groups,smallGroup,parentId,orderNum,resType) VALUES(101,'省市','省市','province','org-menu','',100,1,'Menu');
INSERT INTO `u_resource`(id,NAME,displayText,elementId,groups,smallGroup,parentId,orderNum,resType) VALUES(102,'地市','地市','city','org-menu','',100,2,'Menu');
INSERT INTO `u_resource`(id,NAME,displayText,elementId,groups,smallGroup,parentId,orderNum,resType) VALUES(103,'区县','区县','county','org-menu','',100,3,'Menu');
INSERT INTO `u_resource`(id,NAME,displayText,elementId,groups,smallGroup,parentId,orderNum,resType) VALUES(104,'学校','学校','school','org-menu','',100,4,'Menu');






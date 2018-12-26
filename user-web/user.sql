
DROP TABLE IF EXISTS u_user;
CREATE TABLE u_user(
	id BIGINT NOT NULL COMMENT'用户ID，由程序来维护ID的生成',
	username VARCHAR(20) NOT NULL COMMENT'用户名',
	phone VARCHAR(11) DEFAULT '' COMMENT'电话',
	email VARCHAR(30) DEFAULT '' COMMENT'邮箱',
	pwd VARCHAR(20) NOT NULL COMMENT'密码 md5加密',
	realname VARCHAR(50) DEFAULT '' COMMENT'用户的真实名字',
	PRIMARY KEY id(id),
	key username(username),
	key phone(phone),
	key email(email)
) ENGINE=INNODB COMMENT'用户表';

DROP TABLE IF EXISTS u_role;
CREATE TABLE u_role(
	id BIGINT NOT NULL COMMENT'角色ID',
	NAME VARCHAR(20) NOT NULL COMMENT'角色名字',
	roletype VARCHAR(20) DEFAULT '' COMMENT'角色类型',
	systembuiltin TINYINT(1) DEFAULT 0 COMMENT'是否为系统内置角色',
	PRIMARY KEY id(id)
) ENGINE=INNODB COMMENT'角色表';



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
	elementId varchar(30) default '' comment'页面显示元素ID',
  groups VARCHAR(20) DEFAULT '' COMMENT'资源分组，不同页面显示的内容',
  smallGroup VARCHAR(20) NOT NULL COMMENT'小组用于显示再同一个页面不同位置',
  parentId BIGINT NOT NULL COMMENT'父级ID',
  orderNum int default 0 COMMENT'显示顺序',
  resType varchar(20) default '' comment'资源类型',
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
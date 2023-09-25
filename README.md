https://www.bilibili.com/video/BV1dt4y1x7rT/?p=4&spm_id_from=pageDriver&vd_source=d654eb5e9948ab0888203d1b66c15364

# 创建数据库和数据表

```sql
/*
SQLyog Ultimate v11.27 (32 bit)
MySQL - 8.0.26 : Database - db_authority_system
*********************************************************************
*/
 
 
/*!40101 SET NAMES utf8 */;
 
/*!40101 SET SQL_MODE=''*/;
 
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_authority_system` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
 
USE `db_authority_system`;
 
/*Table structure for table `sys_department` */
 
DROP TABLE IF EXISTS `sys_department`;
 
CREATE TABLE `sys_department` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门编号',
  `department_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门地址',
  `pid` bigint NOT NULL COMMENT '所属部门编号',
  `parent_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属部门名称',
  `order_num` int DEFAULT NULL COMMENT '排序',
  `is_delete` tinyint DEFAULT '0' COMMENT '是否删除(0-未删除 1-已删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
 
/*Data for the table `sys_department` */
 
insert  into `sys_department`(`id`,`department_name`,`phone`,`address`,`pid`,`parent_name`,`order_num`,`is_delete`) values (1,'广州码农信息技术有限公司','020-8888888','广州市天河区',0,'顶级部门',0,0),(2,'软件技术部','020-88881001','广州市天河区',1,'广州码农信息技术有限公司',1,0),(3,'人事管理部','020-88881002','广州市天河区',1,'广州码农信息技术有限公司',1,0),(4,'市场管理部','020-88881003','广州市天河区',1,'广州码农信息技术有限公司',1,0),(5,'软件研发部','020-88881234','广州市天河区',1,'广州码农信息技术有限公司',2,0),(6,'Java技术部','020-12345678','广州市天河区',2,'软件技术部',NULL,1);
 
/*Table structure for table `sys_permission` */
 
DROP TABLE IF EXISTS `sys_permission`;
 
CREATE TABLE `sys_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '权限编号',
  `label` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `parent_id` bigint DEFAULT NULL COMMENT '父权限ID',
  `parent_name` varchar(50) DEFAULT NULL COMMENT '父权限名称',
  `code` varchar(50) DEFAULT NULL COMMENT '授权标识符',
  `path` varchar(100) DEFAULT NULL COMMENT '路由地址',
  `name` varchar(50) DEFAULT NULL COMMENT '路由名称',
  `url` varchar(100) DEFAULT NULL COMMENT '授权路径',
  `type` tinyint DEFAULT NULL COMMENT '权限类型(0-目录 1-菜单 2-按钮)',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `order_num` int DEFAULT NULL COMMENT '排序',
  `is_delete` tinyint DEFAULT '0' COMMENT '是否删除(0-未删除，1-已删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb3;
 
/*Data for the table `sys_permission` */
 
insert  into `sys_permission`(`id`,`label`,`parent_id`,`parent_name`,`code`,`path`,`name`,`url`,`type`,`icon`,`create_time`,`update_time`,`remark`,`order_num`,`is_delete`) values (1,'系统管理',0,'顶级菜单','sys:manager','/system','system','/system/system',0,'el-icon-menu','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,0,0),(2,'部门管理',1,'系统管理','sys:department','/department','department','/system/department/department',1,'el-icon-s-tools','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(3,'新增',2,'部门管理','sys:department:add',NULL,NULL,NULL,2,'el-icon-plus','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(4,'修改',2,'部门管理','sys:department:edit',NULL,NULL,NULL,2,'el-icon-edit','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(5,'删除',2,'部门管理','sys:department:delete',NULL,NULL,NULL,2,'el-icon-delete','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(6,'用户管理',1,'系统管理','sys:user','/userList','userList','/system/user/userList',1,'el-icon-s-custom','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(7,'新增',6,'用户管理','sys:user:add',NULL,NULL,NULL,2,'el-icon-plus','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(8,'修改',6,'用户管理','sys:user:edit',NULL,NULL,NULL,2,'el-icon-edit','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(9,'删除',6,'用户管理','sys:user:delete',NULL,NULL,NULL,2,'el-icon-delete','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(10,'角色管理',1,'系统管理','sys:role','/roleList','roleList','/system/role/roleList',1,'el-icon-s-tools','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(11,'新增',10,'角色管理','sys:role:add',NULL,NULL,NULL,2,'el-icon-plus','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(12,'修改',10,'角色管理','sys:role:edit',NULL,NULL,NULL,2,'el-icon-edit','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(13,'删除',10,'角色管理','sys:role:delete',NULL,NULL,NULL,2,'el-icon-delete','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(14,'菜单管理',1,'系统管理','sys:menu','/menuList','menuList','/system/menu/menuList',1,'el-icon-s-tools','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(15,'新增',14,'权限管理','sys:menu:add',NULL,NULL,NULL,2,'el-icon-plus','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(16,'修改',14,'权限管理','sys:menu:edit',NULL,NULL,NULL,2,'el-icon-edit','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(17,'删除',14,'权限管理','sys:menu:delete',NULL,NULL,NULL,2,'el-icon-delete','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(18,'资料管理',0,'顶级菜单','sys:resource','/resource','resource','/resource/index',0,'el-icon-menu','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,2,0),(19,'供应商管理',18,'资料管理','sys:provider','/providerList','providerList','/system/provider/providerList',1,'el-icon-s-tools','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,2,0),(20,'新增',19,'供应商管理','sys:provider:add',NULL,NULL,NULL,2,'el-icon-plus','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(21,'修改',19,'供应商管理','sys:provider:edit',NULL,NULL,NULL,2,'el-icon-edit','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(22,'删除',19,'供应商管理','sys:provider:delete',NULL,NULL,NULL,2,'el-icon-delete','2022-04-25 14:40:32','2022-04-25 14:40:32',NULL,NULL,0),(23,'分配角色',6,'用户管理','sys:user:assign','','','',2,'el-icon-setting',NULL,NULL,NULL,NULL,0),(24,'分配权限',10,'角色管理','sys:role:assign','','','',2,'el-icon-setting',NULL,NULL,NULL,NULL,0),(25,'查询',2,'部门管理','sys:department:select','','','',2,'el-icon-search',NULL,NULL,NULL,NULL,0),(26,'查询',6,'用户管理','sys:user:select','','','',2,'el-icon-search',NULL,NULL,NULL,NULL,0),(27,'查询',10,'角色管理','sys:role:select','','','',2,'el-icon-search',NULL,NULL,NULL,NULL,0),(28,'查询',14,'菜单管理','sys:menu:select','','','',2,'el-icon-search',NULL,NULL,NULL,NULL,0),(29,'订单管理',18,'资料管理','resource:order','/resource/order','OrderList','/resource/order/orderList',1,'el-icon-setting',NULL,NULL,NULL,2,0);
 
/*Table structure for table `sys_role` */
 
DROP TABLE IF EXISTS `sys_role`;
 
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `role_code` varchar(50) NOT NULL COMMENT '角色编码',
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `create_user` bigint DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `is_delete` tinyint DEFAULT '0' COMMENT '是否删除(0-未删除，1-已删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
 
/*Data for the table `sys_role` */
 
insert  into `sys_role`(`id`,`role_code`,`role_name`,`create_user`,`create_time`,`update_time`,`remark`,`is_delete`) values (1,'ROLE_SYSTEM','超级管理员',1,'2022-04-25 14:44:23','2022-04-25 14:44:23',NULL,0),(2,'ROLE_SYSTEM','系统管理员',1,'2022-04-25 14:44:23','2022-04-25 14:44:23','拥有系统管理功能模块的权限',0),(3,'ROLE_RESOURCE','资料管理员',NULL,NULL,NULL,'拥有资料管理模块的功能权限',0);
 
/*Table structure for table `sys_role_permission` */
 
DROP TABLE IF EXISTS `sys_role_permission`;
 
CREATE TABLE `sys_role_permission` (
  `role_Id` bigint NOT NULL COMMENT '角色ID',
  `permission_Id` bigint NOT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
 
/*Data for the table `sys_role_permission` */
 
insert  into `sys_role_permission`(`role_Id`,`permission_Id`) values (1,1),(1,2),(1,3),(1,4),(1,5),(1,25),(1,6),(1,7),(1,8),(1,9),(1,23),(1,26),(1,10),(1,11),(1,12),(1,13),(1,24),(1,27),(1,14),(1,15),(1,16),(1,17),(1,28),(1,18),(1,19),(1,20),(1,21),(1,22),(2,1),(2,2),(2,3),(2,4),(2,5),(2,25),(2,6),(2,7),(2,8),(2,9),(2,23),(2,26),(2,10),(2,11),(2,12),(2,13),(2,24),(2,27),(2,14),(2,15),(2,16),(2,17),(2,28),(3,18),(3,19),(3,20),(3,21),(3,22),(3,29);
 
/*Table structure for table `sys_user` */
 
DROP TABLE IF EXISTS `sys_user`;
 
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录名称(用户名)',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录密码',
  `is_account_non_expired` tinyint NOT NULL COMMENT '帐户是否过期(1-未过期，0-已过期)',
  `is_account_non_locked` tinyint NOT NULL COMMENT '帐户是否被锁定(1-未过期，0-已过期)',
  `is_credentials_non_expired` tinyint NOT NULL COMMENT '密码是否过期(1-未过期，0-已过期)',
  `is_enabled` tinyint NOT NULL COMMENT '帐户是否可用(1-可用，0-禁用)',
  `real_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '真实姓名',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '昵称',
  `department_id` bigint DEFAULT NULL COMMENT '所属部门ID',
  `department_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '所属部门名称',
  `gender` tinyint NOT NULL COMMENT '性别(0-男，1-女)',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'https://manong-authority.oss-cn-guangzhou.aliyuncs.com/avatar/default-avatar.gif' COMMENT '用户头像',
  `is_admin` tinyint DEFAULT '0' COMMENT '是否是管理员(1-管理员)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` tinyint DEFAULT '0' COMMENT '是否删除(0-未删除，1-已删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
 
/*Data for the table `sys_user` */
 
insert  into `sys_user`(`id`,`username`,`password`,`is_account_non_expired`,`is_account_non_locked`,`is_credentials_non_expired`,`is_enabled`,`real_name`,`nick_name`,`department_id`,`department_name`,`gender`,`phone`,`email`,`avatar`,`is_admin`,`create_time`,`update_time`,`is_delete`) values (1,'admin','$2a$10$TdEVQtGCkpo8L.jKjFB3/uxV5xkkDfiy0zoCa.ZS2yAXHe7H95OIC',1,1,1,1,'李明','超级管理员',1,'广州码农信息技术有限公司',0,'13242587415','liming@163.com','https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',1,NULL,NULL,0),(2,'liming','$2a$10$WwhJ8dBezfyMFIn19.ELru58K65k6N2tgewtv2sWdClKiRCjC55wG',1,1,1,1,'黎明','黎明',2,'软件技术部',0,'13262365412578','','https://manong-authority.oss-cn-guangzhou.aliyuncs.com/avatar/default-avatar.gif',0,NULL,NULL,0),(3,'zhangsan','$2a$10$iBQbmrAEBE5B84/U3RY7c.zhObI4aIpjl807FV4LzL/uay7arIcpu',1,1,1,1,'张三','张三',2,'软件技术部',0,'13245678965','zhangsan@163.com','https://manong-authority.oss-cn-guangzhou.aliyuncs.com/avatar/2022/05/16/bfa834a4c9424461a1ea0cbf8d4c9105-5acd2ed959790ec52b2825cbbc11b72d.jpeg',0,NULL,NULL,1),(4,'lisi','$2a$10$QywHvELdRoFGiU6LKpd/X.LYpfaXETtS0pD4Nem2K3c0iMQwaZuAm',1,1,1,1,'李四','李四',2,'软件技术部',0,'13754214568','','https://manong-authority.oss-cn-guangzhou.aliyuncs.com/avatar/2022/05/16/8868a2bfb4364e0697f7c3d28f3d889a-5acd2ed959790ec52b2825cbbc11b72d.jpeg',0,NULL,NULL,0),(5,'wangwu','$2a$10$O8uyPZFS9PLfR8JN.aMRi.l/YeykYYuKH.cg/HBAR.N4NJeNg8hQK',1,1,1,1,'王五','王五',2,'软件技术部',0,'13212345678','','https://manong-authority.oss-cn-guangzhou.aliyuncs.com/avatar/2022/05/16/fe664c1e45bb4e39a719cd3f6d95232a-male.jpg',0,NULL,NULL,0),(6,'zhaoliu','$2a$10$r45wkEYLHlteEr0KLI8y3.G506ylhQrEJkmGM.i2eHkcCnFvfbhCS',1,1,1,1,'赵六','赵六',2,'软件技术部',0,'13212345676','','https://manong-authority.oss-cn-guangzhou.aliyuncs.com/avatar/2022/05/16/8868a2bfb4364e0697f7c3d28f3d889a-5acd2ed959790ec52b2825cbbc11b72d.jpeg',0,NULL,NULL,0);
 
/*Table structure for table `sys_user_role` */
 
DROP TABLE IF EXISTS `sys_user_role`;
 
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `role_id` bigint NOT NULL COMMENT '角色编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
 
/*Data for the table `sys_user_role` */
 
insert  into `sys_user_role`(`user_id`,`role_id`) values (1,1),(2,2),(4,2),(6,3),(5,3);
 
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
```

# 初始化springboot项目

## 创建maven项目

+ 创建springboot项目，使用`Custom: https://start.aliyun.com/`
+ 防止一些傻逼情况，创建项目没`pom.xml`,创建项目时先选择`web`和`lombok`两个依赖(随便选两个)
+ 项目名为`vue-element-admin_and_springboot_project`

## 安装依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.zhang</groupId>
    <artifactId>code</artifactId>
    <version>0.0.1</version>
    <name>code</name>
    <description>vue-element-admin_and_springboot_security_project</description>
    <properties>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <spring-boot.version>2.6.13</spring-boot.version>
        <jwt.version>0.9.1</jwt.version>
        <mybatis-plus.version>3.5.1</mybatis-plus.version>
        <fastjson.version>1.2.80
        </fastjson.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- mysql -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <!-- mybatis plus -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>${mybatis-plus.version}</version>
        </dependency>
        <!-- 热部署 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <!-- 单元测试 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- fastjson -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>${fastjson.version}</version>
        </dependency>
        <!-- jwt -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>${jwt.version}</version>
        </dependency>
        <!-- redis -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <encoding>UTF-8</encoding>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>${spring-boot.version}</version>
                <configuration>
                    <mainClass>com.zhang.code.CodeApplication</mainClass>
                    <skip>true</skip>
                </configuration>
                <executions>
                    <execution>
                        <id>repackage</id>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

</project>
```

## 编写配置文件

```properties
# 设置端口号
server.port=9999
# 数据库驱动
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# 数据库连接地址
spring.datasource.url=jdbc:mysql://localhost:3306/db_authority_system?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
# 数据库用户名
spring.datasource.username=root
# 数据库密码
spring.datasource.password=123456
# 加载映射文件
mybatis-plus.mapper-locations=classpath*:/mapper/**/*.xml
# 设置别名
mybatis-plus.type-aliases-package=com.zhang.code.entity
# 关闭驼峰命名映射
# mybatis-plus.configuration.map-underscore-to-camel-case=false
# 显示日志
logging.level.com.manong.dao=debug
#JSON 日期格式化
spring.jackson.date-format=yyyy-MM-dd
#JSON 日期格式化设置时区为上海
spring.jackson.time-zone=Asia/Shanghai
# 日期格式化
spring.mvc.format.date=yyyy-MM-dd
spring.mvc.format.date-time=yyyy-MM-ddHH:mm:ss
```

## 代码生成器

+ 代码生成器主文件

```java
package com.zhang.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class GeneratorCode {
    private static String author ="zhang";//作者名称
    private static String outputDir ="D:\\";//生成的位置
    private static String driver ="com.mysql.cj.jdbc.Driver";//驱动，注意版本
    //连接路径,注意修改数据库名称
    private static String url ="jdbc:mysql://localhost:3306/db_authority_system?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private static String username ="root";//数据库用户名
    private static String password ="123456";//数据库密码
    private static String tablePrefix ="sys_";//数据库表的前缀，如t_user
    private static String [] tables = {"sys_user","sys_role","sys_permission","sys_department"};   //生成的表
    private static String parentPackage = "com.zhang.code";//顶级包结构
    private static String dao = "dao";//数据访问层包名称
    private static String service = "service";//业务逻辑层包名称
    private static String entity = "entity";//实体层包名称
    private static String controller = "controller";//控制器层包名称
    private static String mapperxml = "mapper";//mapper映射文件包名称

    public static void main(String[] args) {
        //1. 全局配置
        GlobalConfig config = new GlobalConfig();
        config.setAuthor(author) // 作者
                .setOutputDir(outputDir) // 生成路径
                .setFileOverride(true)  // 文件覆盖
                .setIdType(IdType.AUTO) // 主键策略
                .setServiceName("%sService")  // 设置生成的service接口的名字的首字母是否为I，加%s则不生成I
                .setBaseResultMap(true)    //映射文件中是否生成ResultMap配置
                .setBaseColumnList(true);  //生成通用sql字段

        //2. 数据源配置
        DataSourceConfig dsConfig  = new DataSourceConfig();
        dsConfig.setDbType(DbType.MYSQL)  // 设置数据库类型
                .setDriverName(driver) //设置驱动
                .setUrl(url)         //设置连接路径
                .setUsername(username) //设置用户名
                .setPassword(password);    //设置密码

        //3. 策略配置
        StrategyConfig stConfig = new StrategyConfig();
        stConfig.setCapitalMode(true) //全局大写命名
                .setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
                .setTablePrefix(tablePrefix) //表前缀
                .setInclude(tables)  // 生成的表
                .setEntityLombokModel(true);//支持Lombok

        //4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent(parentPackage)//顶级包结构
                .setMapper(dao)    //数据访问层
                .setService(service)   //业务逻辑层
                .setController(controller) //控制器
                .setEntity(entity) //实体类
                .setXml(mapperxml);    //mapper映射文件

        //5. 整合配置
        AutoGenerator ag = new AutoGenerator();
        ag.setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setPackageInfo(pkConfig);
        //6. 执行
        ag.execute();
    }
}
```

+ 需要将它放到`maven`项目中
+ maven项目依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.manong</groupId>
    <artifactId>code-generator</artifactId>
    <version>1.0-SNAPSHOT</version>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.6.5</version>
    </parent>

    <dependencies>

        <!-- Web核心启动器 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- 单元测试 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- MySQL -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <!-- MyBatis Plus -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.4.1</version>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- 以下是代码生成器的jar依赖 -->
        <!-- 代码生成器核心jar依赖 -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-generator</artifactId>
            <version>3.4.1</version>
        </dependency>

        <!-- 使用默认的velocity模板 -->
        <dependency>
            <groupId>org.apache.velocity</groupId>
            <artifactId>velocity-engine-core</artifactId>
            <version>2.0</version>
        </dependency>

        <!-- sfl4j -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
        </dependency>



    </dependencies>


</project>
```

+ 安装依赖后，运行上面的Java文件
+ 就会在指定目录生成代码包
+ 将除`mapper`目录之外的所有目录都复制到和springboot启动类同级目录中
+ 将`mapper`目录复制到`resource`下

**<font color="red">注意：将实体类中属性数据类型为LocalDate和LocalDateTime修改成java.util.Date类型。</font>**

**<font color="red">User类需要继承UserDetails,后续要用到权限认证之类</font>**

```java
public class User implements Serializable, UserDetails {}
```

+ 这些字段人家要的都是`boolean`类型

```java
    /**
     * 帐户是否过期(1-未过期，0-已过期)
     */
    private boolean isAccountNonExpired = true;

    /**
     * 帐户是否被锁定(1-未过期，0-已过期)
     */
    private boolean isAccountNonLocked = true;

    /**
     * 密码是否过期(1-未过期，0-已过期)
     */
    private boolean isCredentialsNonExpired = true;

    /**
     * 帐户是否可用(1-可用，0-禁用)
     */
    private boolean isEnabled = true;
```

+ 将`service/impl`下所有实现类都加上`@Transactional`注解

### 修改后的全部代码

#### entity

> + User.java
    >
    >   ```java
>   package com.zhang.code.entity;
>   
>   import com.baomidou.mybatisplus.annotation.TableField;
>   import com.baomidou.mybatisplus.annotation.TableName;
>   import com.baomidou.mybatisplus.annotation.IdType;
>   import com.baomidou.mybatisplus.annotation.TableId;
>   
>   import java.util.Collection;
>   import java.util.Date;
>   import java.io.Serializable;
>   import java.util.List;
>   
>   import lombok.Data;
>   import org.springframework.security.core.GrantedAuthority;
>   import org.springframework.security.core.userdetails.UserDetails;
>   
>   @Data
>   @TableName("sys_user")
>   public class User implements Serializable, UserDetails {
>   
>       private static final long serialVersionUID = 1L;
>   
>       /**
>        * 用户编号
>        */
>       @TableId(value = "id", type = IdType.AUTO)
>       private Long id;
>   
>       /**
>        * 登录名称(用户名)
>        */
>       private String username;
>   
>       /**
>        * 登录密码
>        */
>       private String password;
>   
>       /**
>        * 帐户是否过期(1-未过期，0-已过期)
>        */
>       private boolean isAccountNonExpired = true;
>   
>       /**
>        * 帐户是否被锁定(1-未过期，0-已过期)
>        */
>       private boolean isAccountNonLocked = true;
>   
>       /**
>        * 密码是否过期(1-未过期，0-已过期)
>        */
>       private boolean isCredentialsNonExpired = true;
>   
>       /**
>        * 帐户是否可用(1-可用，0-禁用)
>        */
>       private boolean isEnabled = true;
>   
>       /**
>        * 真实姓名
>        */
>       private String realName;
>   
>       /**
>        * 昵称
>        */
>       private String nickName;
>   
>       /**
>        * 所属部门ID
>        */
>       private Long departmentId;
>   
>       /**
>        * 所属部门名称
>        */
>       private String departmentName;
>   
>       /**
>        * 性别(0-男，1-女)
>        */
>       private Integer gender;
>   
>       /**
>        * 电话
>        */
>       private String phone;
>   
>       /**
>        * 邮箱
>        */
>       private String email;
>   
>       /**
>        * 用户头像
>        */
>       private String avatar;
>   
>       /**
>        * 是否是管理员(1-管理员)
>        */
>       private Integer isAdmin;
>   
>       /**
>        * 创建时间
>        */
>       private Date createTime;
>   
>       /**
>        * 修改时间
>        */
>       private Date updateTime;
>   
>       /**
>        * 是否删除(0-未删除，1-已删除)
>        */
>       private Integer isDelete;
>   
>       /**
>        * 权限列表
>        */
>       @TableField(exist = false)
>       Collection<? extends GrantedAuthority> authorities;
>       /**
>        * 查询用户权限列表
>        */
>       @TableField(exist = false)
>       private List<Permission> permissionList;
>   
>   }
>   ```
>
> + `Role.java`
    >
    >   ```java
>   package com.zhang.code.entity;
>   
>   import com.baomidou.mybatisplus.annotation.TableName;
>   import com.baomidou.mybatisplus.annotation.IdType;
>   import com.baomidou.mybatisplus.annotation.TableId;
>   import java.util.Date;
>   import java.io.Serializable;
>   import lombok.Data;
>   
>   @Data
>   @TableName("sys_role")
>   public class Role implements Serializable {
>   
>       private static final long serialVersionUID = 1L;
>   
>       /**
>        * 角色编号
>        */
>         @TableId(value = "id", type = IdType.AUTO)
>       private Long id;
>   
>       /**
>        * 角色编码
>        */
>       private String roleCode;
>   
>       /**
>        * 角色名称
>        */
>       private String roleName;
>   
>       /**
>        * 创建人
>        */
>       private Long createUser;
>   
>       /**
>        * 创建时间
>        */
>       private Date createTime;
>   
>       /**
>        * 修改时间
>        */
>       private Date updateTime;
>   
>       /**
>        * 备注
>        */
>       private String remark;
>   
>       /**
>        * 是否删除(0-未删除，1-已删除)
>        */
>       private Integer isDelete;
>   ```
>
>
>   }
>
>   ```
> + `Permission.java`
> 
>   ```java
>   package com.zhang.code.entity;
> 
>   import com.baomidou.mybatisplus.annotation.TableName;
>   import com.baomidou.mybatisplus.annotation.IdType;
>   import com.baomidou.mybatisplus.annotation.TableId;
>   import java.util.Date;
>   import java.io.Serializable;
>   import lombok.Data;
> 
>   @Data
>   @TableName("sys_permission")
>   public class Permission implements Serializable {
> 
>       private static final long serialVersionUID = 1L;
> 
>       /**
>        * 权限编号
>        */
>         @TableId(value = "id", type = IdType.AUTO)
>       private Long id;
> 
>       /**
>        * 权限名称
>        */
>       private String label;
> 
>       /**
>        * 父权限ID
>        */
>       private Long parentId;
> 
>       /**
>        * 父权限名称
>        */
>       private String parentName;
> 
>       /**
>        * 授权标识符
>        */
>       private String code;
> 
>       /**
>        * 路由地址
>        */
>       private String path;
> 
>       /**
>        * 路由名称
>        */
>       private String name;
> 
>       /**
>        * 授权路径
>        */
>       private String url;
> 
>       /**
>        * 权限类型(0-目录 1-菜单 2-按钮)
>        */
>       private Integer type;
> 
>       /**
>        * 图标
>        */
>       private String icon;
> 
>       /**
>        * 创建时间
>        */
>       private Date createTime;
> 
>       /**
>        * 修改时间
>        */
>       private Date updateTime;
> 
>       /**
>        * 备注
>        */
>       private String remark;
> 
>       /**
>        * 排序
>        */
>       private Integer orderNum;
> 
>       /**
>        * 是否删除(0-未删除，1-已删除)
>        */
>       private Integer isDelete;
> 
> 
>   }
>   ```
>
> + `Department.java`
    >
    >   ```java
>   package com.zhang.code.entity;
>   
>   import com.baomidou.mybatisplus.annotation.TableName;
>   import com.baomidou.mybatisplus.annotation.IdType;
>   import com.baomidou.mybatisplus.annotation.TableId;
>   import java.io.Serializable;
>   import lombok.Data;
>   import lombok.EqualsAndHashCode;
>   
>   /**
>    * <p>
>    * 
>    * </p>
>    *
>    * @author zhang
>    * @since 2023-04-15
>    */
>   @Data
>   @TableName("sys_department")
>   public class Department implements Serializable {
>   
>       private static final long serialVersionUID = 1L;
>   
>       /**
>        * 部门编号
>        */
>         @TableId(value = "id", type = IdType.AUTO)
>       private Long id;
>   
>       /**
>        * 部门名称
>        */
>       private String departmentName;
>   
>       /**
>        * 部门电话
>        */
>       private String phone;
>   
>       /**
>        * 部门地址
>        */
>       private String address;
>   
>       /**
>        * 所属部门编号
>        */
>       private Long pid;
>   
>       /**
>        * 所属部门名称
>        */
>       private String parentName;
>   
>       /**
>        * 排序
>        */
>       private Integer orderNum;
>   
>       /**
>        * 是否删除(0-未删除 1-已删除)
>        */
>       private Integer isDelete;
>   ```
>
>
>   }
>
>   ```
> 
>   ```

#### dao

> + UserMapper.java
    >
    >   ```java
>   package com.zhang.code.dao;
>   
>   import com.zhang.code.entity.User;
>   import com.baomidou.mybatisplus.core.mapper.BaseMapper;
>   
>   public interface UserMapper extends BaseMapper<User> {
>   
>   }
>   
>   ```
>
> + RoleMapper.java
    >
    >   ```java
>   package com.zhang.code.dao;
>   
>   import com.zhang.code.entity.Role;
>   import com.baomidou.mybatisplus.core.mapper.BaseMapper;
>   
>   public interface RoleMapper extends BaseMapper<Role> {
>   
>   }
>   ```
>
> + DepartmentMapper.java
    >
    >   ```java
>   package com.zhang.code.dao;
>   
>   import com.zhang.code.entity.Department;
>   import com.baomidou.mybatisplus.core.mapper.BaseMapper;
>   
>   public interface DepartmentMapper extends BaseMapper<Department> {
>   
>   }
>   ```
>
> + PermissionMapper.java
    >
    >   ```java
>   package com.zhang.code.dao;
>   
>   import com.zhang.code.entity.Permission;
>   import com.baomidou.mybatisplus.core.mapper.BaseMapper;
>   
>   public interface PermissionMapper extends BaseMapper<Permission> {
>   
>   }
>   ```

#### service

> + UserService.java
    >
    >   ```java
>   package com.zhang.code.service;
>   
>   import com.zhang.code.entity.User;
>   import com.baomidou.mybatisplus.extension.service.IService;
>   
>   public interface UserService extends IService<User> {
>   
>   }
>   ```
>
> + PermissionService.java
    >
    >   ```java
>   package com.zhang.code.service;
>   
>   import com.zhang.code.entity.Permission;
>   import com.baomidou.mybatisplus.extension.service.IService;
>   ```
>
>
>   public interface PermissionService extends IService<Permission> {
>
>   }
>
>   ```
> + DepartmentService.java
> 
>   ````java
>   package com.zhang.code.service;
> 
>   import com.zhang.code.entity.Department;
>   import com.baomidou.mybatisplus.extension.service.IService;
> 
> 
>   public interface DepartmentService extends IService<Department> {
> 
>   }
> ````
>
> + RoleService.java
    >
    >   ```java
>   package com.zhang.code.service;
>   
>   import com.zhang.code.entity.Role;
>   import com.baomidou.mybatisplus.extension.service.IService;
>   
>   public interface RoleService extends IService<Role> {
>   
>   }
>   ```

##### impl

> + DepartmentServiceImpl.java
    >
    >   ```java
>   package com.zhang.code.service.impl;
>   
>   import com.zhang.code.entity.Department;
>   import com.zhang.code.dao.DepartmentMapper;
>   import com.zhang.code.service.DepartmentService;
>   import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
>   import org.springframework.stereotype.Service;
>   import org.springframework.transaction.annotation.Transactional;
>   ```
>
>
>   @Service
>   @Transactional
>   public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
>
>   }
>
>   ```
> + PermissionServiceImpl.java
> 
>   ```java
>   package com.zhang.code.service.impl;
> 
>   import com.zhang.code.entity.Permission;
>   import com.zhang.code.dao.PermissionMapper;
>   import com.zhang.code.service.PermissionService;
>   import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
>   import org.springframework.stereotype.Service;
> 
> 
>   @Service
>   public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
> 
>   }
>   ```
>
> + RoleServiceImpl.java
    >
    >   ```java
>   package com.zhang.code.service.impl;
>   
>   import com.zhang.code.entity.Role;
>   import com.zhang.code.dao.RoleMapper;
>   import com.zhang.code.service.RoleService;
>   import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
>   import org.springframework.stereotype.Service;
>   import org.springframework.transaction.annotation.Transactional;
>   ```
>
>
>   @Service
>   @Transactional
>   public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
>
>   }
>
>   ```
> + UserServiceImpl.java
> 
>   ```java
>   package com.zhang.code.service.impl;
> 
>   import com.zhang.code.entity.User;
>   import com.zhang.code.dao.UserMapper;
>   import com.zhang.code.service.UserService;
>   import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
>   import org.springframework.stereotype.Service;
> 
>   @Service
>   public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
> 
>   }
>   ```

#### controller

> + DepartmentController.java
    >
    >   ```java
>   package com.zhang.code.controller;
>   ```
>
>
>   import org.springframework.web.bind.annotation.RequestMapping;
>
>   import org.springframework.stereotype.Controller;
>
>   @Controller
>   @RequestMapping("/department")
>   public class DepartmentController {
>
>   }
>
>   ```
> + PermissionController.java
> 
>   ```java
>   package com.zhang.code.controller;
> 
> 
>   import org.springframework.web.bind.annotation.RequestMapping;
> 
>   import org.springframework.stereotype.Controller;
> 
>   @Controller
>   @RequestMapping("/permission")
>   public class PermissionController {
> 
>   }
>   ```
>
> + RoleController.java
    >
    >   ```java
>   package com.zhang.code.controller;
>   ```
>
>
>   import org.springframework.web.bind.annotation.RequestMapping;
>
>   import org.springframework.stereotype.Controller;
>
>   @Controller
>   @RequestMapping("/role")
>   public class RoleController {
>
>   }
>
>   ```
> + UserController.java
> 
>   ```java
>   package com.zhang.code.controller;
> 
> 
>   import org.springframework.web.bind.annotation.RequestMapping;
> 
>   import org.springframework.stereotype.Controller;
> 
>   @Controller
>   @RequestMapping("/user")
>   public class UserController {
> 
>   }
>   ```

#### mapper

> + DepartmentMapper.xml
    >
    >   ```xml
>   <?xml version="1.0" encoding="UTF-8"?>
>   <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
>   <mapper namespace="com.zhang.code.dao.DepartmentMapper">
>   
>       <!-- 通用查询映射结果 -->
>       <resultMap id="BaseResultMap" type="com.zhang.code.entity.Department">
>           <id column="id" property="id" />
>           <result column="department_name" property="departmentName" />
>           <result column="phone" property="phone" />
>           <result column="address" property="address" />
>           <result column="pid" property="pid" />
>           <result column="parent_name" property="parentName" />
>           <result column="order_num" property="orderNum" />
>           <result column="is_delete" property="isDelete" />
>       </resultMap>
>   
>       <!-- 通用查询结果列 -->
>       <sql id="Base_Column_List">
>           id, department_name, phone, address, pid, parent_name, order_num, is_delete
>       </sql>
>   
>   </mapper>
>   ```
>
> + PermissionMapper.xml
    >
    >   ```xml
>   <?xml version="1.0" encoding="UTF-8"?>
>   <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
>   <mapper namespace="com.zhang.code.dao.PermissionMapper">
>   
>       <!-- 通用查询映射结果 -->
>       <resultMap id="BaseResultMap" type="com.zhang.code.entity.Permission">
>           <id column="id" property="id" />
>           <result column="label" property="label" />
>           <result column="parent_id" property="parentId" />
>           <result column="parent_name" property="parentName" />
>           <result column="code" property="code" />
>           <result column="path" property="path" />
>           <result column="name" property="name" />
>           <result column="url" property="url" />
>           <result column="type" property="type" />
>           <result column="icon" property="icon" />
>           <result column="create_time" property="createTime" />
>           <result column="update_time" property="updateTime" />
>           <result column="remark" property="remark" />
>           <result column="order_num" property="orderNum" />
>           <result column="is_delete" property="isDelete" />
>       </resultMap>
>   
>       <!-- 通用查询结果列 -->
>       <sql id="Base_Column_List">
>           id, label, parent_id, parent_name, code, path, name, url, type, icon, create_time, update_time, remark, order_num, is_delete
>       </sql>
>   
>   </mapper>
>   ```
>
> + RoleMapper.xml
    >
    >   ```xml
>   <?xml version="1.0" encoding="UTF-8"?>
>   <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
>   <mapper namespace="com.zhang.code.dao.RoleMapper">
>   
>       <!-- 通用查询映射结果 -->
>       <resultMap id="BaseResultMap" type="com.zhang.code.entity.Role">
>           <id column="id" property="id" />
>           <result column="role_code" property="roleCode" />
>           <result column="role_name" property="roleName" />
>           <result column="create_user" property="createUser" />
>           <result column="create_time" property="createTime" />
>           <result column="update_time" property="updateTime" />
>           <result column="remark" property="remark" />
>           <result column="is_delete" property="isDelete" />
>       </resultMap>
>   
>       <!-- 通用查询结果列 -->
>       <sql id="Base_Column_List">
>           id, role_code, role_name, create_user, create_time, update_time, remark, is_delete
>       </sql>
>   
>   </mapper>
>   ```
>
> + UserMapper.xml
    >
    >   ```xml
>   <?xml version="1.0" encoding="UTF-8"?>
>   <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
>   <mapper namespace="com.zhang.code.dao.UserMapper">
>   
>       <!-- 通用查询映射结果 -->
>       <resultMap id="BaseResultMap" type="com.zhang.code.entity.User">
>           <id column="id" property="id" />
>           <result column="username" property="username" />
>           <result column="password" property="password" />
>           <result column="is_account_non_expired" property="isAccountNonExpired" />
>           <result column="is_account_non_locked" property="isAccountNonLocked" />
>           <result column="is_credentials_non_expired" property="isCredentialsNonExpired" />
>           <result column="is_enabled" property="isEnabled" />
>           <result column="real_name" property="realName" />
>           <result column="nick_name" property="nickName" />
>           <result column="department_id" property="departmentId" />
>           <result column="department_name" property="departmentName" />
>           <result column="gender" property="gender" />
>           <result column="phone" property="phone" />
>           <result column="email" property="email" />
>           <result column="avatar" property="avatar" />
>           <result column="is_admin" property="isAdmin" />
>           <result column="create_time" property="createTime" />
>           <result column="update_time" property="updateTime" />
>           <result column="is_delete" property="isDelete" />
>       </resultMap>
>   
>       <!-- 通用查询结果列 -->
>       <sql id="Base_Column_List">
>           id, username, password, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled, real_name, nick_name, department_id, department_name, gender, phone, email, avatar, is_admin, create_time, update_time, is_delete
>       </sql>
>   
>   </mapper>
>   ```

---

## 封装统一返回结果类型

+ `com.zhang.code.utils.ResultCode.java`

```java
package com.zhang.code.utils;


public class ResultCode {
    /**
     * 成功状态码
     */
    public static Integer SUCCESS = 200;
    /**
     * 失败状态码
     */
    public static Integer ERROR = 500;
    /**
     *
     未登录状态码
     */
    public static final int NO_LOGIN = 600;
    /**
     *
     没有权限状态码
     */
    public static final int NO_AUTH = 700;
}
```

+ `com.zhang.code.utils.Result.java`

```java
package com.zhang.code.utils;

import
        lombok.Data;

/**
 * 全局统一返回结果类
 */
@Data
public class Result<T> {
    private Boolean success;//是否成功
    private Integer code;//状态码
    private String message;//返回消息
    private T data;//返回数据

    /**
     * 私有化构造方法，
     * 禁止在其它类创建对象
     */
    private Result() {
    }

    /**
     * 成功执行，
     * 不返回数据
     *
     * @return
     */
    public static <T> Result<T> ok() {
        Result<T> result = new Result<T>();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("执行成功" );
        return result;
    }

    /**
     * 成功执行，
     * 并返回数据
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<T>();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("执行成功" );
        result.setData(data);
        return result;
    }

    /**
     * 失败
     *
     * @return
     */
    public static <T> Result<T> error() {
        Result<T> result = new Result<T>();
        result.setSuccess(false);
        result.setCode(ResultCode.ERROR);
        result.setMessage("执行失败" );
        return result;
    }

    /**
     * 设置是否成功
     *
     * @param success
     * @return
     */
    public Result<T> success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    /**
     * 设置状态码
     *
     * @param code
     * @return
     */
    public Result<T> code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 设置返回消息
     *
     * @param message
     * @return
     */
    public Result<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * 是否存在
     *
     * @return
     */
    public static <T> Result<T> exist() {
        Result<T> result = new Result<T>();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("执行成功" );
        return result;
    }
}
```

+ 在`UserController.java`中测试是否能使用

```java
package com.zhang.code.controller;


import com.zhang.code.service.UserService;
import com.zhang.code.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 查询所有用户信息(测试使用)
     * @Return
     */
    @GetMapping("/list")
    public Result list(){
        return Result.ok(userService.list());
    }
}
```

+ `application.properties`中需要配置

```properties
# 加载映射文件
mybatis-plus.mapper-locations=classpath*:/mapper/*.xml
```

+ 在启动类上添加`@MapperScan`扫描mapper类

```java
package com.zhang.code;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.zhang.code.dao")
@SpringBootApplication
public class CodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeApplication.class, args);
    }

}
```

---


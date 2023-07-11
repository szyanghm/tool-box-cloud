/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50741
Source Host           : localhost:3306
Source Database       : tool_box

Target Server Type    : MYSQL
Target Server Version : 50741
File Encoding         : 65001

Date: 2023-07-11 10:19:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_permissions
-- ----------------------------
DROP TABLE IF EXISTS `t_permissions`;
CREATE TABLE `t_permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `permissions_code` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '权限Code',
  `permissions_name` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '权限名称',
  `create_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人（数据库中最终取 update_by）',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间（数据库中最终取 update_time）',
  `is_delete` tinyint(1) DEFAULT NULL COMMENT '是否删除（0、否，1、是）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='权限表';

-- ----------------------------
-- Records of t_permissions
-- ----------------------------
INSERT INTO `t_permissions` VALUES ('1', 'op:write', '写', '1', '2023-06-28 16:45:41', '1', '2023-06-28 16:45:39', '0');
INSERT INTO `t_permissions` VALUES ('2', 'op:read', '读', '1', '2023-06-28 16:46:16', '1', '2023-06-28 16:46:19', '0');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `role_code` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色Code',
  `role_name` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色名称',
  `create_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人（数据库中最终取 update_by）',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间（数据库中最终取 update_time）',
  `is_delete` tinyint(1) DEFAULT NULL COMMENT '是否删除（0、否，1、是）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', 'admin', '超级管理员', '1', '2023-06-28 16:43:45', '1', '2023-06-28 16:43:48', '0');

-- ----------------------------
-- Table structure for t_role_permissions
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permissions`;
CREATE TABLE `t_role_permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `role_code` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色Code',
  `permissions_code` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '权限Code',
  `create_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人（数据库中最终取 update_by）',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间（数据库中最终取 update_time）',
  `is_delete` tinyint(1) DEFAULT NULL COMMENT '是否删除（0、否，1、是）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='角色权限表';

-- ----------------------------
-- Records of t_role_permissions
-- ----------------------------
INSERT INTO `t_role_permissions` VALUES ('1', 'admin', 'op:write', '1', '2023-06-28 16:50:19', '1', '2023-06-28 16:50:23', '0');
INSERT INTO `t_role_permissions` VALUES ('2', 'admin', 'op:read', '1', '2023-06-28 16:50:53', '1', '2023-06-28 16:50:56', '0');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键ID',
  `account` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '账号(唯一)',
  `name` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  `password` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码',
  `salt` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码加盐',
  `role` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色code',
  `create_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '修改人（数据库中最终取 update_by）',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间（数据库中最终取 update_time）',
  `is_delete` tinyint(1) DEFAULT NULL COMMENT '是否删除（0、否，1、是）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `index_account` (`account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='测试表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '11', 'EWFIwJQ6yF/O001yRHUHVRHAnaKWVrgm4jPrN8SwPoY=', 'tool', 'admin', '1', '2023-04-20 16:38:03', '1', '2023-04-20 16:38:06', '0');

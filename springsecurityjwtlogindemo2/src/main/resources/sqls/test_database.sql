/*
 Navicat Premium Data Transfer

 Source Server         : root connection
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : test_database

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 03/03/2021 00:18:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tab_mail_log
-- ----------------------------
DROP TABLE IF EXISTS `tab_mail_log`;
CREATE TABLE `tab_mail_log`  (
  `msg_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `login_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `send_date` timestamp NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL COMMENT '0:投递中;1:投递成功;2:投递失败',
  `route_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由键',
  `exchange` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '交换机',
  `count` int NULL DEFAULT NULL COMMENT '重试次数',
  `try_time` datetime NULL DEFAULT NULL COMMENT '重试时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`msg_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tab_mail_log
-- ----------------------------
INSERT INTO `tab_mail_log` VALUES ('5ebe49bf-988c-49d6-8121-782242651d34', 'user', '2021-03-02 23:25:08', 1, 'mail.routing.key', 'mail.exchange', 1, '2021-03-02 23:27:10', '2021-03-02 23:25:08', '2021-03-02 23:26:10');
INSERT INTO `tab_mail_log` VALUES ('9ee97219-52f2-4c36-ba2d-724d7fa77b44', 'admin', '2021-03-03 00:04:01', 1, 'mail.routing.key', 'mail.exchange', 0, '2021-03-03 00:05:01', '2021-03-03 00:04:01', NULL);
INSERT INTO `tab_mail_log` VALUES ('a3b4d577-4aba-4432-955c-dec1f1fd2c5a', 'user', '2021-03-02 23:11:17', 1, 'mail.routing.key', 'mail.exchange', 3, '2021-03-02 23:12:17', '2021-03-02 23:11:17', NULL);
INSERT INTO `tab_mail_log` VALUES ('b8826906-3a4c-4f90-a9ab-961edcf15c7e', 'admin', '2021-03-02 23:44:32', 1, 'mail.routing.key', 'mail.exchange', 1, '2021-03-02 23:45:40', '2021-03-02 23:44:32', '2021-03-02 23:44:40');

-- ----------------------------
-- Table structure for tab_manager
-- ----------------------------
DROP TABLE IF EXISTS `tab_manager`;
CREATE TABLE `tab_manager`  (
  `manager_id` int NOT NULL AUTO_INCREMENT,
  `manager_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `manager_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `manager_role_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`manager_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tab_manager
-- ----------------------------
INSERT INTO `tab_manager` VALUES (1, 'admin', '222', 1);

-- ----------------------------
-- Table structure for tab_manager_info
-- ----------------------------
DROP TABLE IF EXISTS `tab_manager_info`;
CREATE TABLE `tab_manager_info`  (
  `manager_info_id` int NOT NULL AUTO_INCREMENT,
  `manager_account_id` int NULL DEFAULT NULL,
  `manager_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `manager_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `manager_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`manager_info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tab_manager_info
-- ----------------------------
INSERT INTO `tab_manager_info` VALUES (1, 1, '陈陈陈', '1245213', 'Chen@sina.com');

-- ----------------------------
-- Table structure for tab_role
-- ----------------------------
DROP TABLE IF EXISTS `tab_role`;
CREATE TABLE `tab_role`  (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tab_role
-- ----------------------------
INSERT INTO `tab_role` VALUES (1, 'manager');
INSERT INTO `tab_role` VALUES (2, 'user');
INSERT INTO `tab_role` VALUES (3, 'superManager');
INSERT INTO `tab_role` VALUES (4, 'developer');

-- ----------------------------
-- Table structure for tab_user
-- ----------------------------
DROP TABLE IF EXISTS `tab_user`;
CREATE TABLE `tab_user`  (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_role_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tab_user
-- ----------------------------
INSERT INTO `tab_user` VALUES (1, 'user', '111', 2);

-- ----------------------------
-- Table structure for tab_user_info
-- ----------------------------
DROP TABLE IF EXISTS `tab_user_info`;
CREATE TABLE `tab_user_info`  (
  `user_info_id` int NOT NULL AUTO_INCREMENT,
  `user_account_id` int NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_age` int NULL DEFAULT NULL,
  `user_gender` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tab_user_info
-- ----------------------------
INSERT INTO `tab_user_info` VALUES (1, 1, 'Jay', 30, '男', 'Jay1990@qq.com');

SET FOREIGN_KEY_CHECKS = 1;

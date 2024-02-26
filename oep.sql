/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80035
 Source Host           : localhost:3306
 Source Schema         : oep

 Target Server Type    : MySQL
 Target Server Version : 80035
 File Encoding         : 65001

 Date: 27/02/2024 00:25:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `account_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`account_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('hy', '$2a$10$63CH4tzDZ0MQ54y6lAigaupnwvEXVyGYYLq2QUaRjjNYICIMbdkdS', 'enterprise');

-- ----------------------------
-- Table structure for candidate
-- ----------------------------
DROP TABLE IF EXISTS `candidate`;
CREATE TABLE `candidate`  (
  `candidate_id` int NOT NULL AUTO_INCREMENT,
  `fullname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `headphoto` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `telephone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL,
  `account_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`candidate_id`) USING BTREE,
  INDEX `candidate_account_account_id_fk`(`account_id` ASC) USING BTREE,
  CONSTRAINT `candidate_account_account_id_fk` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of candidate
-- ----------------------------

-- ----------------------------
-- Table structure for enterprise
-- ----------------------------
DROP TABLE IF EXISTS `enterprise`;
CREATE TABLE `enterprise`  (
  `enterprise_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `account_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`enterprise_id`) USING BTREE,
  UNIQUE INDEX `enterprise_pk`(`name` ASC) USING BTREE,
  UNIQUE INDEX `enterprise_pk_2`(`name` ASC) USING BTREE,
  UNIQUE INDEX `enterprise_pk_3`(`name` ASC) USING BTREE,
  UNIQUE INDEX `enterprise_pk_4`(`name` ASC) USING BTREE,
  INDEX `enterprise_account_account_id_fk`(`account_id` ASC) USING BTREE,
  CONSTRAINT `enterprise_account_account_id_fk` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of enterprise
-- ----------------------------
INSERT INTO `enterprise` VALUES (1, 'hy', '2846617029@qq.com', 'hy');

-- ----------------------------
-- Table structure for group
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `group_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `enterprise_id` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `groups_pk`(`group_name` ASC) USING BTREE,
  INDEX `enterprise_id`(`enterprise_id` ASC) USING BTREE,
  CONSTRAINT `enterprise_id` FOREIGN KEY (`enterprise_id`) REFERENCES `enterprise` (`enterprise_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group
-- ----------------------------
INSERT INTO `group` VALUES (1, '默认分组', 1);

-- ----------------------------
-- Table structure for group_problem
-- ----------------------------
DROP TABLE IF EXISTS `group_problem`;
CREATE TABLE `group_problem`  (
  `group_id` int NOT NULL,
  `problem_id` int NOT NULL,
  PRIMARY KEY (`problem_id`, `group_id`) USING BTREE,
  INDEX `group_problems_group_id_fk`(`group_id` ASC) USING BTREE,
  CONSTRAINT `group_problems_group_id_fk` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `group_problems_problems_id_fk` FOREIGN KEY (`problem_id`) REFERENCES `problem` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_problem
-- ----------------------------
INSERT INTO `group_problem` VALUES (1, 55);
INSERT INTO `group_problem` VALUES (1, 57);
INSERT INTO `group_problem` VALUES (1, 58);
INSERT INTO `group_problem` VALUES (1, 59);
INSERT INTO `group_problem` VALUES (1, 60);

-- ----------------------------
-- Table structure for problem
-- ----------------------------
DROP TABLE IF EXISTS `problem`;
CREATE TABLE `problem`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `difficulty` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `check_by` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `right_answer` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `appendix_name` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `accurate_times` int NOT NULL DEFAULT 0,
  `finished_times` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of problem
-- ----------------------------
INSERT INTO `problem` VALUES (55, '1', '1', '未设置', '自动批改', 'true', '', '判断', 0, 0);
INSERT INTO `problem` VALUES (57, '3', '3', '简单', '自动批改', '3', '', '填空', 0, 0);
INSERT INTO `problem` VALUES (58, '英语', 'sss', '简单', '自动批改', '6', '', '综合', 0, 0);
INSERT INTO `problem` VALUES (59, '英语', 'sss', '简单', '人工批改', '9', '', '综合', 0, 0);
INSERT INTO `problem` VALUES (60, 'yuwen', 'w', '简单', '自动批改', '7', '', '综合', 0, 0);

-- ----------------------------
-- Table structure for testpaper
-- ----------------------------
DROP TABLE IF EXISTS `testpaper`;
CREATE TABLE `testpaper`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `note` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `problem_count` int NULL DEFAULT NULL,
  `problems` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `enterprise_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `testpaper_enterprise_name_fk`(`enterprise_name` ASC) USING BTREE,
  CONSTRAINT `testpaper_enterprise_name_fk` FOREIGN KEY (`enterprise_name`) REFERENCES `enterprise` (`name`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of testpaper
-- ----------------------------
INSERT INTO `testpaper` VALUES (18, '32', '32', 2, '{\"problemId\":60,\"description\":\"w\",\"rightAnswer\":\"7\",\"checkBy\":\"自动批改\",\"type\":\"综合\"}{\"problemId\":58,\"description\":\"sss\",\"rightAnswer\":\"6\",\"checkBy\":\"自动批改\",\"type\":\"综合\"}', 'hy');
INSERT INTO `testpaper` VALUES (19, 'po', 'op', 2, '{\"problemId\":60,\"description\":\"w\",\"rightAnswer\":\"7\",\"checkBy\":\"自动批改\",\"type\":\"综合\"}{\"problemId\":58,\"description\":\"sss\",\"rightAnswer\":\"6\",\"checkBy\":\"自动批改\",\"type\":\"综合\"}', 'hy');

SET FOREIGN_KEY_CHECKS = 1;

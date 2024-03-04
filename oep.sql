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

 Date: 03/03/2024 17:41:40
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
INSERT INTO `account` VALUES ('twinkle', '$2a$10$NpNX5uSXMNmcbQ/K00zgZOBXIT1xW1jS7rcZMV6YKHZ9AxdO.jNme', 'candidate');
INSERT INTO `account` VALUES ('uy', '$2a$10$S5/PusGi9QJ4fdoH340ld.J1gmj9D2q9c.AC.EBbc4fWs4RjQa2va', 'candidate');

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
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of candidate
-- ----------------------------
INSERT INTO `candidate` VALUES (3, '123', NULL, '\0', NULL, NULL, NULL, 'twinkle');
INSERT INTO `candidate` VALUES (4, '123', NULL, '\0', NULL, NULL, NULL, 'uy');

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
-- Table structure for exam
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam`  (
  `exam_id` int NOT NULL AUTO_INCREMENT,
  `begin_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `note` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `announced` tinyint(1) NOT NULL DEFAULT 0,
  `testpaper_title` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `enterprise_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`exam_id`) USING BTREE,
  INDEX `exam_enterprise_name_fk`(`enterprise_name` ASC) USING BTREE,
  INDEX `exam_testpaper_title_fk`(`testpaper_title` ASC) USING BTREE,
  CONSTRAINT `exam_enterprise_name_fk` FOREIGN KEY (`enterprise_name`) REFERENCES `enterprise` (`name`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `exam_testpaper_title_fk` FOREIGN KEY (`testpaper_title`) REFERENCES `testpaper` (`title`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam
-- ----------------------------
INSERT INTO `exam` VALUES (1, '2024-03-02 08:00:00', '2024-03-02 10:00:00', '无', 1, 'FDS', 'hy');
INSERT INTO `exam` VALUES (2, '2024-03-08 08:00:00', '2024-03-08 12:00:00', '', 1, '第九次大联考', 'hy');
INSERT INTO `exam` VALUES (3, '2024-03-08 08:00:00', '2024-03-08 10:00:00', '', 1, '第一次大联考', 'hy');
INSERT INTO `exam` VALUES (4, '2024-03-03 18:00:00', '2024-03-03 20:00:00', '', 0, 'po', 'hy');
INSERT INTO `exam` VALUES (5, '2024-02-16 00:00:00', '2024-04-10 00:00:00', '', 1, '965675', 'hy');
INSERT INTO `exam` VALUES (6, '2024-02-16 00:00:00', '2024-04-12 00:00:00', '', 0, '12', 'hy');
INSERT INTO `exam` VALUES (7, '2024-02-16 00:00:00', '2024-04-25 00:00:00', '', 1, '87', 'hy');
INSERT INTO `exam` VALUES (8, '2024-02-16 00:00:00', '2024-04-25 00:00:00', '', 1, 'FDS', 'hy');
INSERT INTO `exam` VALUES (9, '2024-03-16 00:00:00', '2024-03-21 00:00:00', '', 1, 'oic', 'hy');
INSERT INTO `exam` VALUES (11, '2024-03-03 00:00:00', '2024-03-12 00:00:00', '', 0, '09', 'hy');
INSERT INTO `exam` VALUES (12, '2024-03-03 00:00:00', '2024-03-12 00:00:00', '', 1, 'FDS', 'hy');
INSERT INTO `exam` VALUES (13, '2024-03-03 00:00:00', '2024-03-12 00:00:00', '', 1, 'oic', 'hy');
INSERT INTO `exam` VALUES (14, '2024-03-13 00:00:00', '2024-02-27 00:00:00', '', 1, '12', 'hy');
INSERT INTO `exam` VALUES (15, '2024-03-07 00:00:00', '2024-03-14 00:00:00', '', 1, '965675', 'hy');
INSERT INTO `exam` VALUES (16, '2024-03-02 00:00:00', '2024-03-10 00:00:00', '', 1, '87', 'hy');
INSERT INTO `exam` VALUES (17, '2024-03-04 00:00:00', '2024-03-04 00:00:00', '', 0, 'FDS', 'hy');
INSERT INTO `exam` VALUES (18, '2024-03-04 00:00:00', '2024-03-04 00:00:00', '', 1, '87', 'hy');
INSERT INTO `exam` VALUES (19, '2024-03-04 00:00:00', '2024-03-04 00:00:00', '', 1, '12', 'hy');
INSERT INTO `exam` VALUES (20, '2024-03-04 00:00:00', '2024-03-04 00:00:00', '', 1, '965675', 'hy');
INSERT INTO `exam` VALUES (21, '2024-03-04 00:00:00', '2024-03-04 00:00:00', '', 1, 'hg', 'hy');

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
  UNIQUE INDEX `testpaper_pk`(`title` ASC) USING BTREE,
  INDEX `testpaper_enterprise_name_fk`(`enterprise_name` ASC) USING BTREE,
  INDEX `testpaper_title_index`(`title` ASC) USING BTREE,
  CONSTRAINT `testpaper_enterprise_name_fk` FOREIGN KEY (`enterprise_name`) REFERENCES `enterprise` (`name`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of testpaper
-- ----------------------------
INSERT INTO `testpaper` VALUES (1, '32', '32', 2, '{\"problemId\":60,\"description\":\"w\",\"rightAnswer\":\"7\",\"checkBy\":\"自动批改\",\"type\":\"综合\"}{\"problemId\":58,\"description\":\"sss\",\"rightAnswer\":\"6\",\"checkBy\":\"自动批改\",\"type\":\"综合\"}', 'hy');
INSERT INTO `testpaper` VALUES (2, 'po', 'op', 2, '{\"problemId\":60,\"description\":\"w\",\"rightAnswer\":\"7\",\"checkBy\":\"自动批改\",\"type\":\"综合\"}{\"problemId\":58,\"description\":\"sss\",\"rightAnswer\":\"6\",\"checkBy\":\"自动批改\",\"type\":\"综合\"}', 'hy');
INSERT INTO `testpaper` VALUES (20, 'y', 't', 2, '{\"problemId\":55,\"description\":\"1\",\"rightAnswer\":\"true\",\"checkBy\":\"自动批改\",\"type\":\"判断\"}{\"problemId\":60,\"description\":\"w\",\"rightAnswer\":\"7\",\"checkBy\":\"自动批改\",\"type\":\"综合\"}', 'hy');
INSERT INTO `testpaper` VALUES (21, '第九次大联考', 'hhh', 2, '{\"problemId\":55,\"description\":\"1\",\"rightAnswer\":\"true\",\"checkBy\":\"自动批改\",\"type\":\"判断\"}{\"problemId\":58,\"description\":\"sss\",\"rightAnswer\":\"6\",\"checkBy\":\"自动批改\",\"type\":\"综合\"}', 'hy');
INSERT INTO `testpaper` VALUES (22, '第一次大联考', '666', 2, '{\"problemId\":58,\"description\":\"sss\",\"rightAnswer\":\"6\",\"checkBy\":\"自动批改\",\"type\":\"综合\"}{\"problemId\":59,\"description\":\"sss\",\"rightAnswer\":\"9\",\"checkBy\":\"人工批改\",\"type\":\"综合\"}', 'hy');
INSERT INTO `testpaper` VALUES (23, 'iu', 'iu', 2, '{\"problemId\":55,\"description\":\"1\",\"rightAnswer\":\"true\",\"checkBy\":\"自动批改\",\"type\":\"判断\"}{\"problemId\":59,\"description\":\"sss\",\"rightAnswer\":\"9\",\"checkBy\":\"人工批改\",\"type\":\"综合\"}', 'hy');
INSERT INTO `testpaper` VALUES (24, 'oic', 'OI', 2, '{\"problemId\":60,\"description\":\"w\",\"rightAnswer\":\"7\",\"checkBy\":\"自动批改\",\"type\":\"综合\"}{\"problemId\":58,\"description\":\"sss\",\"rightAnswer\":\"6\",\"checkBy\":\"自动批改\",\"type\":\"综合\"}', 'hy');
INSERT INTO `testpaper` VALUES (25, 'FDS', 'HGF', 2, '{\"problemId\":57,\"description\":\"3\",\"rightAnswer\":\"3\",\"checkBy\":\"自动批改\",\"type\":\"填空\"}{\"problemId\":60,\"description\":\"w\",\"rightAnswer\":\"7\",\"checkBy\":\"自动批改\",\"type\":\"综合\"}', 'hy');
INSERT INTO `testpaper` VALUES (26, '87', '87', 1, '{\"problemId\":55,\"description\":\"1\",\"rightAnswer\":\"true\",\"checkBy\":\"自动批改\",\"type\":\"判断\"}', 'hy');
INSERT INTO `testpaper` VALUES (27, '09', '90', 1, '{\"problemId\":58,\"description\":\"sss\",\"rightAnswer\":\"6\",\"checkBy\":\"自动批改\",\"type\":\"综合\"}', 'hy');
INSERT INTO `testpaper` VALUES (28, '12', '12', 2, '{\"problemId\":58,\"description\":\"sss\",\"rightAnswer\":\"6\",\"checkBy\":\"自动批改\",\"type\":\"综合\"}{\"problemId\":59,\"description\":\"sss\",\"rightAnswer\":\"9\",\"checkBy\":\"人工批改\",\"type\":\"综合\"}', 'hy');
INSERT INTO `testpaper` VALUES (29, '965675', '63', 2, '{\"problemId\":60,\"description\":\"w\",\"rightAnswer\":\"7\",\"checkBy\":\"自动批改\",\"type\":\"综合\"}{\"problemId\":58,\"description\":\"sss\",\"rightAnswer\":\"6\",\"checkBy\":\"自动批改\",\"type\":\"综合\"}', 'hy');
INSERT INTO `testpaper` VALUES (30, 'hg', 'hg', 1, '{\"problemId\":55,\"description\":\"1\",\"rightAnswer\":\"true\",\"checkBy\":\"自动批改\",\"type\":\"判断\"}', 'hy');

SET FOREIGN_KEY_CHECKS = 1;

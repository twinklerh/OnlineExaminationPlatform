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

 Date: 10/03/2024 02:25:53
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
-- Table structure for candidate_exam
-- ----------------------------
DROP TABLE IF EXISTS `candidate_exam`;
CREATE TABLE `candidate_exam`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `candidate_id` int NOT NULL,
  `exam_id` int NOT NULL,
  `score` float NULL DEFAULT NULL,
  `answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `candidate_exam_pk_2`(`id` ASC) USING BTREE,
  INDEX `candidate_exam_candidate_candidate_id_fk`(`candidate_id` ASC) USING BTREE,
  INDEX `candidate_exam_exam_exam_id_fk`(`exam_id` ASC) USING BTREE,
  CONSTRAINT `candidate_exam_candidate_candidate_id_fk` FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`candidate_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `candidate_exam_exam_exam_id_fk` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`exam_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of candidate_exam
-- ----------------------------
INSERT INTO `candidate_exam` VALUES (13, 4, 40, -1, '');
INSERT INTO `candidate_exam` VALUES (14, 4, 41, -1, '');

-- ----------------------------
-- Table structure for candidate_problem
-- ----------------------------
DROP TABLE IF EXISTS `candidate_problem`;
CREATE TABLE `candidate_problem`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `problem_id` int NULL DEFAULT NULL,
  `candidate_id` int NULL DEFAULT NULL,
  `exam_id` int NULL DEFAULT NULL,
  `candidate_answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `date_time` date NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `candidate_problem_candidate_candidate_id_fk`(`candidate_id` ASC) USING BTREE,
  INDEX `candidate_problem_problem_id_fk`(`problem_id` ASC) USING BTREE,
  INDEX `candidate_problem_exam_exam_id_fk`(`exam_id` ASC) USING BTREE,
  CONSTRAINT `candidate_problem_candidate_candidate_id_fk` FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`candidate_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `candidate_problem_exam_exam_id_fk` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`exam_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `candidate_problem_problem_id_fk` FOREIGN KEY (`problem_id`) REFERENCES `problem` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of candidate_problem
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
-- Table structure for exam
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam`  (
  `exam_id` int NOT NULL AUTO_INCREMENT,
  `begin_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `note` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `announced` tinyint(1) NOT NULL DEFAULT 0,
  `invite_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `testpaper_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `enterprise_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`exam_id`) USING BTREE,
  INDEX `exam_enterprise_name_fk`(`enterprise_name` ASC) USING BTREE,
  INDEX `exam_testpaper_title_fk`(`testpaper_title` ASC) USING BTREE,
  CONSTRAINT `exam_enterprise_name_fk` FOREIGN KEY (`enterprise_name`) REFERENCES `enterprise` (`name`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `exam_testpaper_title_fk` FOREIGN KEY (`testpaper_title`) REFERENCES `testpaper` (`title`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam
-- ----------------------------
INSERT INTO `exam` VALUES (40, '2024-03-07 19:28:06', '2024-03-08 00:00:00', '', 1, '22306453j10011753u967067123', '{hy}第一次测试', 'hy');
INSERT INTO `exam` VALUES (41, '2024-03-08 13:46:24', '2024-03-11 00:00:00', '', 1, '89a707644125791728487433116', '{hy}第二次考试', 'hy');

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
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group
-- ----------------------------
INSERT INTO `group` VALUES (19, '默认分组', 1);
INSERT INTO `group` VALUES (20, '计算机组成原理', 1);

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
INSERT INTO `group_problem` VALUES (19, 77);
INSERT INTO `group_problem` VALUES (19, 80);
INSERT INTO `group_problem` VALUES (19, 81);
INSERT INTO `group_problem` VALUES (20, 72);
INSERT INTO `group_problem` VALUES (20, 73);
INSERT INTO `group_problem` VALUES (20, 78);
INSERT INTO `group_problem` VALUES (20, 79);

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
  `score` float NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of problem
-- ----------------------------
INSERT INTO `problem` VALUES (72, '系统总线', '系统总线用来连接（ ）', '简单', '自动批改', 'C', '[null,\"寄存器和运算器部件\",\"运算器和控制器部件\",\"CPU、主存和外设部件\",\"接口和外部设备\"]', '选择', 5);
INSERT INTO `problem` VALUES (73, '间址寻址', '间址寻址第一次访问内存所得到的信息经系统总线的（ ）传送到CPU。', '简单', '自动批改', 'A', '[null,\"数据总线\",\"地址总线\",\"控制总线\",\"总线控制器\"]', '选择', 5);
INSERT INTO `problem` VALUES (77, '系统总线中地址线的功能', '系统总线中地址线的功能是（ ）', '未设置', '自动批改', 'D', '[null,\"选择主存单元地址\",\"选择进行信息传输的设备\",\"选择外存地址\",\"指定主存和I/O设备接口电路的地址\"]', '选择', 5);
INSERT INTO `problem` VALUES (78, '数据传输的计算', '传输一幅分辨率为640*480像素、颜色数量为65536的照片（采用无压缩方式），设有效数据传输率为56kb/s，大约需要的时间是（）', '简单', '自动批改', 'D', '[null,\"34.82s\",\"43.86s\",\"85.71s\",\"87.77s\"]', '选择', 5);
INSERT INTO `problem` VALUES (79, '微机中控制总线上完成传输的信号', '微机中控制总线上完成传输的信号有（）\na.存储器和I/O设备的地址码\nb.所有存储器和I/O设备的时序信号与控制信号\nc.来自I/O设备和存储器的响应信号', '未设置', '自动批改', 'B', '[null,\"仅a\",\"b和c\",\"仅b\",\"a、b、c\"]', '选择', 5);
INSERT INTO `problem` VALUES (80, 'MVC、MVVM模型', '什么是 MVVM、MVC 模型？', '简单', '人工批改', '', '', '综合', 10);
INSERT INTO `problem` VALUES (81, 'vue的生命周期', 'vue 的生命周期有哪些？', '简单', '人工批改', '', '', '综合', 10);

-- ----------------------------
-- Table structure for testpaper
-- ----------------------------
DROP TABLE IF EXISTS `testpaper`;
CREATE TABLE `testpaper`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `note` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `problem_count` int NULL DEFAULT NULL,
  `is_need_appendix` tinyint(1) NOT NULL DEFAULT 0,
  `problems` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `enterprise_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `testpaper_pk`(`title` ASC) USING BTREE,
  INDEX `testpaper_enterprise_name_fk`(`enterprise_name` ASC) USING BTREE,
  INDEX `testpaper_title_index`(`title` ASC) USING BTREE,
  CONSTRAINT `testpaper_enterprise_name_fk` FOREIGN KEY (`enterprise_name`) REFERENCES `enterprise` (`name`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of testpaper
-- ----------------------------
INSERT INTO `testpaper` VALUES (32, '{hy}第一次测试', '', 7, 0, '{\"problemId\":80,\"description\":\"什么是 MVVM、MVC 模型？\",\"rightAnswer\":\"\",\"checkBy\":\"人工批改\",\"type\":\"综合\"},{\"problemId\":81,\"description\":\"vue 的生命周期有哪些？\",\"rightAnswer\":\"\",\"checkBy\":\"人工批改\",\"type\":\"综合\"},{\"problemId\":79,\"description\":\"微机中控制总线上完成传输的信号有（）\\na.存储器和I/O设备的地址码\\nb.所有存储器和I/O设备的时序信号与控制信号\\nc.来自I/O设备和存储器的响应信号\",\"rightAnswer\":\"B\",\"checkBy\":\"自动批改\",\"type\":\"选择\"},{\"problemId\":78,\"description\":\"传输一幅分辨率为640*480像素、颜色数量为65536的照片（采用无压缩方式），设有效数据传输率为56kb/s，大约需要的时间是（）\",\"rightAnswer\":\"D\",\"checkBy\":\"自动批改\",\"type\":\"选择\"},{\"problemId\":72,\"description\":\"系统总线用来连接（ ）\",\"rightAnswer\":\"C\",\"checkBy\":\"自动批改\",\"type\":\"选择\"},{\"problemId\":77,\"description\":\"系统总线中地址线的功能是（ ）\",\"rightAnswer\":\"D\",\"checkBy\":\"自动批改\",\"type\":\"选择\"},{\"problemId\":73,\"description\":\"间址寻址第一次访问内存所得到的信息经系统总线的（ ）传送到CPU。\",\"rightAnswer\":\"A\",\"checkBy\":\"自动批改\",\"type\":\"选择\"},', 'hy');
INSERT INTO `testpaper` VALUES (33, '{hy}第二次测试', '无', 2, 0, '{\"problemId\":80,\"description\":\"什么是 MVVM、MVC 模型？\",\"rightAnswer\":\"\",\"checkBy\":\"人工批改\",\"type\":\"综合\"},{\"problemId\":73,\"description\":\"间址寻址第一次访问内存所得到的信息经系统总线的（ ）传送到CPU。\",\"rightAnswer\":\"A\",\"checkBy\":\"自动批改\",\"type\":\"选择\"},', 'hy');
INSERT INTO `testpaper` VALUES (34, '{hy}第二次考试', '', 4, 0, '{\"problemId\":81,\"description\":\"vue 的生命周期有哪些？\",\"rightAnswer\":\"\",\"checkBy\":\"人工批改\",\"type\":\"综合\"},{\"problemId\":78,\"description\":\"传输一幅分辨率为640*480像素、颜色数量为65536的照片（采用无压缩方式），设有效数据传输率为56kb/s，大约需要的时间是（）\",\"rightAnswer\":\"D\",\"checkBy\":\"自动批改\",\"type\":\"选择\"},{\"problemId\":77,\"description\":\"系统总线中地址线的功能是（ ）\",\"rightAnswer\":\"D\",\"checkBy\":\"自动批改\",\"type\":\"选择\"},{\"problemId\":73,\"description\":\"间址寻址第一次访问内存所得到的信息经系统总线的（ ）传送到CPU。\",\"rightAnswer\":\"A\",\"checkBy\":\"自动批改\",\"type\":\"选择\"},', 'hy');

SET FOREIGN_KEY_CHECKS = 1;

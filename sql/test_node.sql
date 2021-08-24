/*
 Navicat MySQL Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : ry-vue

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 23/08/2021 13:55:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for test_node
-- ----------------------------
DROP TABLE IF EXISTS `test_node`;
CREATE TABLE `test_node`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名',
  `categary` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类',
  `pid` bigint NULL DEFAULT NULL COMMENT '父id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 73 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of test_node
-- ----------------------------
INSERT INTO `test_node` VALUES (1, 'java', '编程语言', 0);
INSERT INTO `test_node` VALUES (2, 'docker', '容器技术', 1);
INSERT INTO `test_node` VALUES (4, 'springboot', '框架技术', 1);
INSERT INTO `test_node` VALUES (5, '前端', '前端', 0);
INSERT INTO `test_node` VALUES (6, 'vue', '前端框架', 5);
INSERT INTO `test_node` VALUES (12, '灯芯绒', NULL, 0);
INSERT INTO `test_node` VALUES (13, '特点', NULL, 12);
INSERT INTO `test_node` VALUES (14, '厚实且耐磨', NULL, 13);
INSERT INTO `test_node` VALUES (16, '手感弹滑柔软', NULL, 13);
INSERT INTO `test_node` VALUES (17, '绒条清晰圆润', NULL, 13);
INSERT INTO `test_node` VALUES (18, '光泽柔和均匀', NULL, 13);
INSERT INTO `test_node` VALUES (19, '沿绒条方向易撕裂', NULL, 13);
INSERT INTO `test_node` VALUES (20, '面料用途', NULL, 12);
INSERT INTO `test_node` VALUES (21, '秋冬外衣', NULL, 20);
INSERT INTO `test_node` VALUES (22, '鞋帽', NULL, 20);
INSERT INTO `test_node` VALUES (23, '家具装饰布', NULL, 20);
INSERT INTO `test_node` VALUES (24, '窗帘', NULL, 20);
INSERT INTO `test_node` VALUES (25, '沙发', NULL, 20);
INSERT INTO `test_node` VALUES (26, '玩具', NULL, 20);
INSERT INTO `test_node` VALUES (27, '绒条粗细', NULL, 12);
INSERT INTO `test_node` VALUES (28, '细条', NULL, 27);
INSERT INTO `test_node` VALUES (29, '中条', NULL, 27);
INSERT INTO `test_node` VALUES (30, '粗条', NULL, 27);
INSERT INTO `test_node` VALUES (31, '宽条', NULL, 27);
INSERT INTO `test_node` VALUES (32, '灯草绒、条绒、趟绒', '别名', 12);
INSERT INTO `test_node` VALUES (33, '经纱纬纱交织', '组成', 12);
INSERT INTO `test_node` VALUES (34, 'Deng\'xin\'rong', '拼音', 12);
INSERT INTO `test_node` VALUES (48, '常见面料类型', NULL, 12);
INSERT INTO `test_node` VALUES (49, '弹力性', NULL, 48);
INSERT INTO `test_node` VALUES (50, '粘胶型', NULL, 48);
INSERT INTO `test_node` VALUES (51, '涤纶型', NULL, 48);
INSERT INTO `test_node` VALUES (52, '粗细条型', NULL, 48);
INSERT INTO `test_node` VALUES (53, '间歇割型', NULL, 48);
INSERT INTO `test_node` VALUES (54, '飞毛型', NULL, 48);
INSERT INTO `test_node` VALUES (55, '双色型', NULL, 48);
INSERT INTO `test_node` VALUES (56, '防掉毛', NULL, 49);
INSERT INTO `test_node` VALUES (57, '提高服装保型性', NULL, 49);
INSERT INTO `test_node` VALUES (58, '提高服装舒适性', NULL, 49);
INSERT INTO `test_node` VALUES (59, '光泽亮丽', NULL, 50);
INSERT INTO `test_node` VALUES (60, '悬垂性高', NULL, 50);
INSERT INTO `test_node` VALUES (61, '手感光泽', NULL, 50);
INSERT INTO `test_node` VALUES (62, '颜色鲜艳', NULL, 50);
INSERT INTO `test_node` VALUES (63, '颜色鲜艳', NULL, 51);
INSERT INTO `test_node` VALUES (64, '易保养', NULL, 51);
INSERT INTO `test_node` VALUES (65, '洗可穿性', NULL, 51);
INSERT INTO `test_node` VALUES (66, '视觉丰富', NULL, 52);
INSERT INTO `test_node` VALUES (67, '立体感', NULL, 52);
INSERT INTO `test_node` VALUES (68, '偏割', '方式', 52);
INSERT INTO `test_node` VALUES (69, '立体感', NULL, 53);
INSERT INTO `test_node` VALUES (70, '浮雕状', '效果', 53);
INSERT INTO `test_node` VALUES (71, '轻薄透明', NULL, 54);
INSERT INTO `test_node` VALUES (72, '视觉丰富', NULL, 54);
INSERT INTO `test_node` VALUES (73, '浮雕状', '效果', 54);

SET FOREIGN_KEY_CHECKS = 1;

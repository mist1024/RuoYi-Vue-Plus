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

 Date: 23/08/2021 13:53:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `is_frame` int NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2006 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, 1, 0, 'M', '0', '0', '', 'system', 'admin', '2021-08-06 15:01:44', '', NULL, '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 2, 'monitor', NULL, 1, 0, 'M', '0', '0', '', 'monitor', 'admin', '2021-08-06 15:01:44', '', NULL, '系统监控目录');
INSERT INTO `sys_menu` VALUES (3, '系统工具', 0, 3, 'tool', NULL, 1, 0, 'M', '0', '0', '', 'tool', 'admin', '2021-08-06 15:01:44', '', NULL, '系统工具目录');
INSERT INTO `sys_menu` VALUES (4, 'PLUS官网', 0, 4, 'https://gitee.com/JavaLionLi/RuoYi-Vue-Plus', NULL, 0, 0, 'M', '0', '0', '', 'guide', 'admin', '2021-08-06 15:01:44', '', NULL, 'RuoYi-Vue-Plus官网地址');
INSERT INTO `sys_menu` VALUES (5, '测试菜单', 0, 5, 'demo', NULL, 1, 0, 'M', '0', '0', NULL, 'star', 'admin', '2021-05-30 00:34:26', NULL, NULL, '');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', '2021-08-06 15:01:44', '', NULL, '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2021-08-06 15:01:44', '', NULL, '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2021-08-06 15:01:44', '', NULL, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (103, '部门管理', 1, 4, 'dept', 'system/dept/index', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', 'admin', '2021-08-06 15:01:44', '', NULL, '部门管理菜单');
INSERT INTO `sys_menu` VALUES (104, '岗位管理', 1, 5, 'post', 'system/post/index', 1, 0, 'C', '0', '0', 'system:post:list', 'post', 'admin', '2021-08-06 15:01:44', '', NULL, '岗位管理菜单');
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict', 'admin', '2021-08-06 15:01:44', '', NULL, '字典管理菜单');
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', 'admin', '2021-08-06 15:01:44', '', NULL, '参数设置菜单');
INSERT INTO `sys_menu` VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', 1, 0, 'C', '0', '0', 'system:notice:list', 'message', 'admin', '2021-08-06 15:01:44', '', NULL, '通知公告菜单');
INSERT INTO `sys_menu` VALUES (108, '日志管理', 1, 9, 'log', '', 1, 0, 'M', '0', '0', '', 'log', 'admin', '2021-08-06 15:01:44', '', NULL, '日志管理菜单');
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', 1, 0, 'C', '0', '0', 'monitor:online:list', 'online', 'admin', '2021-08-06 15:01:44', '', NULL, '在线用户菜单');
INSERT INTO `sys_menu` VALUES (110, '定时任务', 2, 2, 'job', 'monitor/job/index', 1, 0, 'C', '0', '0', 'monitor:job:list', 'job', 'admin', '2021-08-06 15:01:44', '', NULL, '定时任务菜单');
INSERT INTO `sys_menu` VALUES (111, '数据监控', 2, 3, 'druid', 'monitor/druid/index', 1, 0, 'C', '0', '0', 'monitor:druid:list', 'druid', 'admin', '2021-08-06 15:01:44', '', NULL, '数据监控菜单');
INSERT INTO `sys_menu` VALUES (113, '缓存监控', 2, 5, 'cache', 'monitor/cache/index', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis', 'admin', '2021-08-06 15:01:44', '', NULL, '缓存监控菜单');
INSERT INTO `sys_menu` VALUES (114, '表单构建', 3, 1, 'build', 'tool/build/index', 1, 0, 'C', '0', '0', 'tool:build:list', 'build', 'admin', '2021-08-06 15:01:44', '', NULL, '表单构建菜单');
INSERT INTO `sys_menu` VALUES (115, '代码生成', 3, 2, 'gen', 'tool/gen/index', 1, 0, 'C', '0', '0', 'tool:gen:list', 'code', 'admin', '2021-08-06 15:01:44', '', NULL, '代码生成菜单');
INSERT INTO `sys_menu` VALUES (116, '系统接口', 3, 3, 'swagger', 'tool/swagger/index', 1, 0, 'C', '0', '0', 'tool:swagger:list', 'swagger', 'admin', '2021-08-06 15:01:44', '', NULL, '系统接口菜单');
INSERT INTO `sys_menu` VALUES (117, 'Admin监控', 2, 5, 'Admin', 'monitor/admin/index', 1, 0, 'C', '0', '0', 'monitor:admin:list', 'dashboard', 'admin', '2021-08-06 15:01:44', '', NULL, 'Admin监控菜单');
INSERT INTO `sys_menu` VALUES (118, '文件管理', 1, 10, 'oss', 'system/oss/index', 1, 0, 'C', '0', '0', 'system:oss:list', 'upload', 'admin', '2021-08-06 15:02:52', '', NULL, '文件管理菜单');
INSERT INTO `sys_menu` VALUES (500, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', 1, 0, 'C', '0', '0', 'monitor:operlog:list', 'form', 'admin', '2021-08-06 15:01:44', '', NULL, '操作日志菜单');
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', 'admin', '2021-08-06 15:01:44', '', NULL, '登录日志菜单');
INSERT INTO `sys_menu` VALUES (1001, '用户查询', 100, 1, '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1002, '用户新增', 100, 2, '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1003, '用户修改', 100, 3, '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1004, '用户删除', 100, 4, '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1005, '用户导出', 100, 5, '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1006, '用户导入', 100, 6, '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1007, '重置密码', 100, 7, '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1008, '角色查询', 101, 1, '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1009, '角色新增', 101, 2, '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1010, '角色修改', 101, 3, '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1011, '角色删除', 101, 4, '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1012, '角色导出', 101, 5, '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1013, '菜单查询', 102, 1, '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1014, '菜单新增', 102, 2, '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1015, '菜单修改', 102, 3, '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1016, '菜单删除', 102, 4, '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1017, '部门查询', 103, 1, '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1018, '部门新增', 103, 2, '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1019, '部门修改', 103, 3, '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1020, '部门删除', 103, 4, '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1021, '岗位查询', 104, 1, '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1022, '岗位新增', 104, 2, '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1023, '岗位修改', 104, 3, '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1024, '岗位删除', 104, 4, '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1025, '岗位导出', 104, 5, '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1026, '字典查询', 105, 1, '#', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1027, '字典新增', 105, 2, '#', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1028, '字典修改', 105, 3, '#', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1029, '字典删除', 105, 4, '#', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1030, '字典导出', 105, 5, '#', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1031, '参数查询', 106, 1, '#', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1032, '参数新增', 106, 2, '#', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1033, '参数修改', 106, 3, '#', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1034, '参数删除', 106, 4, '#', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1035, '参数导出', 106, 5, '#', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1036, '公告查询', 107, 1, '#', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1037, '公告新增', 107, 2, '#', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1038, '公告修改', 107, 3, '#', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1039, '公告删除', 107, 4, '#', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1040, '操作查询', 500, 1, '#', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1041, '操作删除', 500, 2, '#', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1042, '日志导出', 500, 4, '#', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1043, '登录查询', 501, 1, '#', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1044, '登录删除', 501, 2, '#', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1045, '日志导出', 501, 3, '#', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1046, '在线查询', 109, 1, '#', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1047, '批量强退', 109, 2, '#', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1048, '单条强退', 109, 3, '#', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1049, '任务查询', 110, 1, '#', '', 1, 0, 'F', '0', '0', 'monitor:job:query', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1050, '任务新增', 110, 2, '#', '', 1, 0, 'F', '0', '0', 'monitor:job:add', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1051, '任务修改', 110, 3, '#', '', 1, 0, 'F', '0', '0', 'monitor:job:edit', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1052, '任务删除', 110, 4, '#', '', 1, 0, 'F', '0', '0', 'monitor:job:remove', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1053, '状态修改', 110, 5, '#', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1054, '任务导出', 110, 7, '#', '', 1, 0, 'F', '0', '0', 'monitor:job:export', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1055, '生成查询', 115, 1, '#', '', 1, 0, 'F', '0', '0', 'tool:gen:query', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1056, '生成修改', 115, 2, '#', '', 1, 0, 'F', '0', '0', 'tool:gen:edit', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1057, '生成删除', 115, 3, '#', '', 1, 0, 'F', '0', '0', 'tool:gen:remove', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1058, '导入代码', 115, 2, '#', '', 1, 0, 'F', '0', '0', 'tool:gen:import', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1059, '预览代码', 115, 4, '#', '', 1, 0, 'F', '0', '0', 'tool:gen:preview', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1060, '生成代码', 115, 5, '#', '', 1, 0, 'F', '0', '0', 'tool:gen:code', '#', 'admin', '2021-08-06 15:01:44', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1500, '测试单表', 5, 1, 'demo', 'demo/demo/index', 1, 0, 'C', '0', '0', 'demo:demo:list', '#', 'admin', '2021-05-30 00:39:23', '', NULL, '测试单表菜单');
INSERT INTO `sys_menu` VALUES (1501, '测试单表查询', 1500, 1, '#', '', 1, 0, 'F', '0', '0', 'demo:demo:query', '#', 'admin', '2021-05-30 00:39:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1502, '测试单表新增', 1500, 2, '#', '', 1, 0, 'F', '0', '0', 'demo:demo:add', '#', 'admin', '2021-05-30 00:39:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1503, '测试单表修改', 1500, 3, '#', '', 1, 0, 'F', '0', '0', 'demo:demo:edit', '#', 'admin', '2021-05-30 00:39:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1504, '测试单表删除', 1500, 4, '#', '', 1, 0, 'F', '0', '0', 'demo:demo:remove', '#', 'admin', '2021-05-30 00:39:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1505, '测试单表导出', 1500, 5, '#', '', 1, 0, 'F', '0', '0', 'demo:demo:export', '#', 'admin', '2021-05-30 00:39:23', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1506, '测试树表', 5, 1, 'tree', 'demo/tree/index', 1, 0, 'C', '0', '0', 'demo:tree:list', '#', 'admin', '2021-05-30 00:39:30', '', NULL, '测试树表菜单');
INSERT INTO `sys_menu` VALUES (1507, '测试树表查询', 1506, 1, '#', '', 1, 0, 'F', '0', '0', 'demo:tree:query', '#', 'admin', '2021-05-30 00:39:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1508, '测试树表新增', 1506, 2, '#', '', 1, 0, 'F', '0', '0', 'demo:tree:add', '#', 'admin', '2021-05-30 00:39:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1509, '测试树表修改', 1506, 3, '#', '', 1, 0, 'F', '0', '0', 'demo:tree:edit', '#', 'admin', '2021-05-30 00:39:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1510, '测试树表删除', 1506, 4, '#', '', 1, 0, 'F', '0', '0', 'demo:tree:remove', '#', 'admin', '2021-05-30 00:39:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1511, '测试树表导出', 1506, 5, '#', '', 1, 0, 'F', '0', '0', 'demo:tree:export', '#', 'admin', '2021-05-30 00:39:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1600, '文件查询', 118, 1, '#', '', 1, 0, 'F', '0', '0', 'system:oss:query', '#', 'admin', '2021-08-06 15:02:52', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1601, '文件上传', 118, 2, '#', '', 1, 0, 'F', '0', '0', 'system:oss:upload', '#', 'admin', '2021-08-06 15:02:52', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1602, '文件下载', 118, 3, '#', '', 1, 0, 'F', '0', '0', 'system:oss:download', '#', 'admin', '2021-08-06 15:02:52', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1603, '文件删除', 118, 4, '#', '', 1, 0, 'F', '0', '0', 'system:oss:remove', '#', 'admin', '2021-08-06 15:02:52', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2000, '节点维护', 5, 1, 'node', 'system/node/index', 1, 0, 'C', '0', '0', 'system:node:list', '#', 'admin', '2021-08-08 22:17:41', '', NULL, '节点维护菜单');
INSERT INTO `sys_menu` VALUES (2001, '节点维护查询', 2000, 1, '#', '', 1, 0, 'F', '0', '0', 'system:node:query', '#', 'admin', '2021-08-08 22:17:41', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2002, '节点维护新增', 2000, 2, '#', '', 1, 0, 'F', '0', '0', 'system:node:add', '#', 'admin', '2021-08-08 22:17:41', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2003, '节点维护修改', 2000, 3, '#', '', 1, 0, 'F', '0', '0', 'system:node:edit', '#', 'admin', '2021-08-08 22:17:41', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2004, '节点维护删除', 2000, 4, '#', '', 1, 0, 'F', '0', '0', 'system:node:remove', '#', 'admin', '2021-08-08 22:17:41', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2005, '节点维护导出', 2000, 5, '#', '', 1, 0, 'F', '0', '0', 'system:node:export', '#', 'admin', '2021-08-08 22:17:41', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2006, '展示界面', 0, 6, 'show', 'show/index', 1, 0, 'C', '0', '0', 'show', 'color', 'admin', '2021-08-11 17:25:57', NULL, NULL, '');

SET FOREIGN_KEY_CHECKS = 1;

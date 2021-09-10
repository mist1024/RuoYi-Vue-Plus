SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for isc_app_service
-- ----------------------------
DROP TABLE IF EXISTS `isc_app_service`;
CREATE TABLE `isc_app_service`  (
  `app_service_id` bigint NOT NULL AUTO_INCREMENT COMMENT '应用服务ID',
  `service_id` bigint NULL DEFAULT NULL COMMENT '服务ID',
  `application_id` bigint NULL DEFAULT NULL COMMENT '应用ID',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `enabled` char(1) NOT NULL COMMENT '启用状态（0启用 1停用）',
  `status` char(1)  DEFAULT NULL COMMENT '审核状态（0待审核 1审核通过 2驳回）',
  `virtual_addr` varchar(255)  DEFAULT NULL COMMENT '虚拟地址',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '到期时间',
  `quota_days` int NULL DEFAULT NULL COMMENT '天配额',
  `quota_hours` int NULL DEFAULT NULL COMMENT '小时配额',
  `quota_minutes` int NULL DEFAULT NULL COMMENT '分钟配额',
  `quota_seconds` int NULL DEFAULT NULL COMMENT '秒配额',
  `create_by` varchar(64)  DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64)  DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1)  DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`app_service_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = '应用服务信息';

-- ----------------------------
-- Records of isc_app_service
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for isc_app_service_apply
-- ----------------------------
DROP TABLE IF EXISTS `isc_app_service_apply`;
CREATE TABLE `isc_app_service_apply`  (
  `apply_id` bigint NOT NULL AUTO_INCREMENT COMMENT '申请ID',
  `app_service_id` bigint NOT NULL COMMENT '应用服务ID',
  `apply_type` char(1)  DEFAULT NULL COMMENT '申请类型(0申请 1续期)',
  `status` char(1)  DEFAULT NULL COMMENT '审核状态（0待审核 1审核通过 2驳回）',
  `audit_mind` varchar(255)  DEFAULT NULL COMMENT '审核意见',
  `renewal_duration` int NULL DEFAULT NULL COMMENT '续期时长（单位月）',
  `quota_days` int NULL DEFAULT NULL COMMENT '天配额',
  `quota_hours` int NULL DEFAULT NULL COMMENT '小时配额',
  `quota_minutes` int NULL DEFAULT NULL COMMENT '分钟配额',
  `quota_seconds` int NULL DEFAULT NULL COMMENT '秒配额',
  `create_by` varchar(64)  DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64)  DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1)  DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `remark` varchar(500)  DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`apply_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = '应用服务申请信息';

-- ----------------------------
-- Records of isc_app_service_apply
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for isc_application
-- ----------------------------
DROP TABLE IF EXISTS `isc_application`;
CREATE TABLE `isc_application`  (
  `application_id` bigint NOT NULL AUTO_INCREMENT COMMENT '应用ID',
  `application_name` varchar(255) NOT NULL COMMENT '应用名称',
  `access_key` varchar(32) NOT NULL COMMENT '应用密钥',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `create_by` varchar(64)  DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64)  DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1)  DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `remark` varchar(500)  DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`application_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = '应用信息';

-- ----------------------------
-- Records of isc_application
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for isc_service
-- ----------------------------
DROP TABLE IF EXISTS `isc_service`;
CREATE TABLE `isc_service`  (
  `service_id` bigint NOT NULL AUTO_INCREMENT COMMENT '服务ID',
  `service_name` varchar(255) NOT NULL COMMENT '服务名称',
  `service_addr` varchar(255) NOT NULL COMMENT '服务地址',
  `probe_active_addr` varchar(255)  DEFAULT NULL COMMENT '探活地址',
  `request_method` varchar(10)  DEFAULT NULL COMMENT '请求方式（默认GET）',
  `remark` varchar(500)  DEFAULT NULL COMMENT '备注',
  `cors_flag` char(1)  DEFAULT NULL COMMENT '跨域标志（Y是 N否）',
  `hidden_params` varchar(500)  DEFAULT NULL COMMENT '隐藏参数',
  `api_doc` text  COMMENT 'JSON文档',
  `online_status` char(1) DEFAULT NULL COMMENT '是否在线（0离线 1在线）',
  `status` char(1)  DEFAULT NULL COMMENT '审核状态（0待审核 1审核通过 2驳回）',
  `audit_mind` varchar(255)  DEFAULT NULL COMMENT '审核意见',
  `enabled` char(1) NOT NULL COMMENT '服务状态（0启用 1停用）',
  `cate_full_path` varchar(30)  DEFAULT NULL COMMENT '服务分类全路径',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `create_by` varchar(64)  DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64)  DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1)  DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`service_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = '服务信息';

-- ----------------------------
-- Records of isc_service
-- ----------------------------
BEGIN;
INSERT INTO `isc_service` VALUES (1, '测试', 'https://www.baidu.com/', 'https://www.baidu.com/', 'GET', NULL, 'Y', NULL, '{}', '1', '0', NULL, '0', '001000000000000000000000000000', 1, 'admin', '2021-09-05 22:47:41', 'admin', '2021-09-05 22:47:41', '0');
COMMIT;

-- ----------------------------
-- Table structure for isc_service_cate
-- ----------------------------
DROP TABLE IF EXISTS `isc_service_cate`;
CREATE TABLE `isc_service_cate`  (
  `cate_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父分类ID',
  `cate_name` varchar(64)  DEFAULT NULL COMMENT '分类名称',
  `full_path` varchar(30)  DEFAULT NULL COMMENT '搜索全路径',
  `enabled` char(1) NOT NULL COMMENT '启用状态（0启用 1停用）',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `create_by` varchar(64)  DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64)  DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1)  DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `remark` varchar(500)  DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`cate_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 COMMENT = '服务分类';

-- ----------------------------
-- Records of isc_service_cate
-- ----------------------------
BEGIN;
INSERT INTO `isc_service_cate` VALUES (10, 0, 'API服务', '001000000000000000000000000000', '0', 10, 'admin', '2021-09-04 23:35:13', 'admin', '2021-09-04 23:35:13', '0', NULL), (11, 0, '地图服务', '002000000000000000000000000000', '0', 20, 'admin', '2021-09-04 23:35:37', 'admin', '2021-09-04 23:35:37', '0', NULL), (12, 0, '数据服务', '003000000000000000000000000000', '0', 30, 'admin', '2021-09-04 23:36:01', 'admin', '2021-09-04 23:36:01', '0', NULL), (13, 11, 'WFS服务', '002001000000000000000000000000', '0', 10, 'admin', '2021-09-04 23:36:26', 'admin', '2021-09-04 23:36:26', '0', NULL);
COMMIT;

-- ----------------------------
-- Table structure for isc_service_log_count
-- ----------------------------
DROP TABLE IF EXISTS `isc_service_log_count`;
CREATE TABLE `isc_service_log_count`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `service_id` bigint NOT NULL COMMENT '服务ID',
  `count_date` date NOT NULL COMMENT '统计日期',
  `total_num` bigint NOT NULL COMMENT '调用次数',
  `avg_time` bigint NULL DEFAULT NULL COMMENT '平均响应时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = '服务每日统计';

-- ----------------------------
-- Records of isc_service_log_count
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for isc_service_log_detail
-- ----------------------------
DROP TABLE IF EXISTS `isc_service_log_detail`;
CREATE TABLE `isc_service_log_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `service_id` bigint NOT NULL COMMENT '服务ID',
  `application_id` bigint NOT NULL COMMENT '应用ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `request_ip` varchar(64)  DEFAULT NULL COMMENT '调用IP',
  `begin_time` timestamp(0) NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` timestamp(0) NULL DEFAULT NULL COMMENT '结束时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(500)  DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = '服务调用日志';

-- ----------------------------
-- Records of isc_service_log_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for isc_service_log_user_count
-- ----------------------------
DROP TABLE IF EXISTS `isc_service_log_user_count`;
CREATE TABLE `isc_service_log_user_count`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `service_id` bigint NOT NULL COMMENT '服务ID',
  `application_id` bigint NOT NULL COMMENT '应用ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `count_date` date NULL DEFAULT NULL COMMENT '统计日期',
  `total_num` bigint NULL DEFAULT NULL COMMENT '调用次数',
  `avg_time` bigint NULL DEFAULT NULL COMMENT '平均响应时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = '服务日志用户统计';

-- ----------------------------
-- Records of isc_service_log_user_count
-- ----------------------------
BEGIN;
COMMIT;

BEGIN;
INSERT INTO `sys_menu` VALUES (1607, '服务分类', 1606, 1, 'cate', 'isc/cate/index', 1, 0, 'C', '0', '0', 'isc:cate:list', '#', 'admin', '2021-09-08 13:59:21', 'admin', '2021-09-08 14:02:25', '服务分类菜单');
INSERT INTO `sys_menu` VALUES (1608, '服务分类查询', 1607, 1, '#', '', 1, 0, 'F', '0', '0', 'isc:cate:query', '#', 'admin', '2021-09-08 13:59:21', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1609, '服务分类新增', 1607, 2, '#', '', 1, 0, 'F', '0', '0', 'isc:cate:add', '#', 'admin', '2021-09-08 13:59:21', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1610, '服务分类修改', 1607, 3, '#', '', 1, 0, 'F', '0', '0', 'isc:cate:edit', '#', 'admin', '2021-09-08 13:59:21', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1611, '服务分类删除', 1607, 4, '#', '', 1, 0, 'F', '0', '0', 'isc:cate:remove', '#', 'admin', '2021-09-08 13:59:21', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1612, '服务分类导出', 1607, 5, '#', '', 1, 0, 'F', '0', '0', 'isc:cate:export', '#', 'admin', '2021-09-08 13:59:21', '', NULL, '');

INSERT INTO `sys_menu` VALUES (1619, '服务信息', 1606, 1, 'service', 'isc/service/index', 1, 0, 'C', '0', '0', 'isc:service:list', '#', 'admin', '2021-09-08 14:04:29', '', NULL, '服务信息菜单');
INSERT INTO `sys_menu` VALUES (1620, '服务信息查询', 1619, 1, '#', '', 1, 0, 'F', '0', '0', 'isc:service:query', '#', 'admin', '2021-09-08 14:04:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1621, '服务信息新增', 1619, 2, '#', '', 1, 0, 'F', '0', '0', 'isc:service:add', '#', 'admin', '2021-09-08 14:04:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1622, '服务信息修改', 1619, 3, '#', '', 1, 0, 'F', '0', '0', 'isc:service:edit', '#', 'admin', '2021-09-08 14:04:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1623, '服务信息删除', 1619, 4, '#', '', 1, 0, 'F', '0', '0', 'isc:service:remove', '#', 'admin', '2021-09-08 14:04:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1624, '服务信息导出', 1619, 5, '#', '', 1, 0, 'F', '0', '0', 'isc:service:export', '#', 'admin', '2021-09-08 14:04:29', '', NULL, '');

INSERT INTO `sys_menu` VALUES (1625, '应用信息', 1606, 1, 'application', 'isc/application/index', 1, 0, 'C', '0', '0', 'isc:application:list', '#', 'admin', '2021-09-08 14:10:06', '', NULL, '应用信息菜单');
INSERT INTO `sys_menu` VALUES (1626, '应用信息查询', 1625, 1, '#', '', 1, 0, 'F', '0', '0', 'isc:application:query', '#', 'admin', '2021-09-08 14:10:06', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1627, '应用信息新增', 1625, 2, '#', '', 1, 0, 'F', '0', '0', 'isc:application:add', '#', 'admin', '2021-09-08 14:10:06', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1628, '应用信息修改', 1625, 3, '#', '', 1, 0, 'F', '0', '0', 'isc:application:edit', '#', 'admin', '2021-09-08 14:10:06', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1629, '应用信息删除', 1625, 4, '#', '', 1, 0, 'F', '0', '0', 'isc:application:remove', '#', 'admin', '2021-09-08 14:10:06', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1630, '应用信息导出', 1625, 5, '#', '', 1, 0, 'F', '0', '0', 'isc:application:export', '#', 'admin', '2021-09-08 14:10:06', '', NULL, '');

INSERT INTO `sys_menu` VALUES (1631, '应用服务', 1625, 1, 'appservice', 'isc/appservice/index', 1, 0, 'F', '0', '0', 'isc:appservice:list', '#', 'admin', '2021-09-08 16:11:57', 'admin', '2021-09-08 16:22:10', '应用服务菜单');
INSERT INTO `sys_menu` VALUES (1632, '应用服务查询', 1631, 1, '#', '', 1, 0, 'F', '0', '0', 'isc:appservice:query', '#', 'admin', '2021-09-08 16:11:57', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1633, '应用服务新增', 1631, 2, '#', '', 1, 0, 'F', '0', '0', 'isc:appservice:add', '#', 'admin', '2021-09-08 16:11:57', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1634, '应用服务修改', 1631, 3, '#', '', 1, 0, 'F', '0', '0', 'isc:appservice:edit', '#', 'admin', '2021-09-08 16:11:57', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1635, '应用服务删除', 1631, 4, '#', '', 1, 0, 'F', '0', '0', 'isc:appservice:remove', '#', 'admin', '2021-09-08 16:11:57', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1636, '应用服务导出', 1631, 5, '#', '', 1, 0, 'F', '0', '0', 'isc:appservice:export', '#', 'admin', '2021-09-08 16:11:57', '', NULL, '');

INSERT INTO `sys_menu` VALUES (1637, '服务申请', 1606, 1, 'serviceapply', 'isc/serviceapply/index', 1, 0, 'C', '0', '0', 'isc:serviceapply:list', '#', 'admin', '2021-09-09 14:45:09', '', NULL, '服务申请菜单');
INSERT INTO `sys_menu` VALUES (1638, '服务申请信息查询', 1637, 1, '#', '', 1, 0, 'F', '0', '0', 'isc:serviceapply:query', '#', 'admin', '2021-09-09 14:45:09', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1639, '服务申请信息审核', 1637, 2, '#', '', 1, 0, 'F', '0', '0', 'isc:serviceapply:audit', '#', 'admin', '2021-09-09 14:45:09', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1640, '服务申请信息导出', 1637, 5, '#', '', 1, 0, 'F', '0', '0', 'isc:serviceapply:export', '#', 'admin', '2021-09-09 14:45:09', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1641, '服务审核', 1606, 5, 'service/audit', 'isc/service/audit', 1, 0, 'C', '0', '0', 'isc:service:audit', 'eye-open', 'admin', '2021-09-10 13:35:18', 'admin', '2021-09-10 13:37:14', '');

INSERT INTO `sys_dict_type` VALUES (100, '审核状态', 'sys_audit_status', '0', 'admin', '2021-08-21 21:21:58', 'admin', '2021-08-21 21:21:58', '审核状态列表');
INSERT INTO `sys_dict_type` VALUES (101, '在线状态', 'isc_online_status', '0', 'admin', '2021-08-21 21:24:28', 'admin', '2021-08-21 21:24:28', '在线状态列表');
INSERT INTO `sys_dict_type` VALUES (102, '申请类型', 'isc_apply_type', '0', 'admin', '2021-08-21 21:25:26', 'admin', '2021-08-21 21:25:26', '申请类型列表');
INSERT INTO `sys_dict_type` VALUES (103, '请求方法', 'isc_request_method', '0', 'admin', '2021-09-07 22:12:58', 'admin', '2021-09-07 22:12:58', 'GET/POST');

INSERT INTO `sys_dict_data` VALUES (100, 0, '待审核', '0', 'sys_audit_status', NULL, 'primary', 'N', '0', 'admin', '2021-08-21 21:26:36', 'admin', '2021-08-21 21:26:36', NULL);
INSERT INTO `sys_dict_data` VALUES (101, 1, '通过', '1', 'sys_audit_status', NULL, 'success', 'N', '0', 'admin', '2021-08-21 21:27:02', 'admin', '2021-08-21 21:27:02', NULL);
INSERT INTO `sys_dict_data` VALUES (102, 2, '驳回', '2', 'sys_audit_status', NULL, 'warning', 'N', '0', 'admin', '2021-08-21 21:27:24', 'admin', '2021-08-21 21:27:24', NULL);
INSERT INTO `sys_dict_data` VALUES (103, 0, '离线', '0', 'isc_online_status', NULL, 'info', 'N', '0', 'admin', '2021-08-21 21:28:56', 'admin', '2021-08-21 21:28:56', NULL);
INSERT INTO `sys_dict_data` VALUES (104, 1, '在线', '1', 'isc_online_status', NULL, 'primary', 'N', '0', 'admin', '2021-08-21 21:29:15', 'admin', '2021-08-21 21:29:15', NULL);
INSERT INTO `sys_dict_data` VALUES (105, 0, '申请', '0', 'isc_apply_type', NULL, 'primary', 'N', '0', 'admin', '2021-08-21 21:30:26', 'admin', '2021-08-21 21:30:26', NULL);
INSERT INTO `sys_dict_data` VALUES (106, 1, '续期', '1', 'isc_apply_type', NULL, 'primary', 'N', '0', 'admin', '2021-08-21 21:30:42', 'admin', '2021-08-21 21:30:42', NULL);
INSERT INTO `sys_dict_data` VALUES (107, 2, '修改', '2', 'isc_apply_type', NULL, 'primary', 'N', '0', 'admin', '2021-09-09 17:00:48', 'admin', '2021-09-09 17:00:48', NULL);
INSERT INTO `sys_dict_data` VALUES (108, 1, 'GET', 'GET', 'isc_request_method', NULL, 'primary', 'N', '0', 'admin', '2021-09-07 22:13:22', 'admin', '2021-09-07 22:13:56', NULL);
INSERT INTO `sys_dict_data` VALUES (109, 2, 'POST', 'POST', 'isc_request_method', NULL, 'info', 'N', '0', 'admin', '2021-09-07 22:13:40', 'admin', '2021-09-07 22:13:40', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

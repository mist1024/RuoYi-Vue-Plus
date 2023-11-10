SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for go_view_file
-- ----------------------------
DROP TABLE IF EXISTS `go_view_file`;
CREATE TABLE `go_view_file`
(
    `id`            varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编号',
    `file_name`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名',
    `file_size`     int(11) NOT NULL COMMENT '文件大小',
    `file_suffix`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件后缀',
    `virtual_key`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '虚拟路径',
    `relative_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相对路径',
    `absolute_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '绝对路径',
    `state`         int(1) NOT NULL DEFAULT -1 COMMENT '发布(1发布-1取消发布)',
    `del_flag`      int(1) NOT NULL DEFAULT 0 COMMENT '删除(0正常2删除)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '大屏文件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of go_view_file
-- ----------------------------

-- ----------------------------
-- Table structure for go_view_project
-- ----------------------------
DROP TABLE IF EXISTS `go_view_project`;
CREATE TABLE `go_view_project`
(
    `id`           bigint(20) NOT NULL COMMENT '编号',
    `project_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '项目名',
    `index_image`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面',
    `state`        int(1) NOT NULL DEFAULT -1 COMMENT '发布(1发布-1取消发布)',
    `del_flag`     int(1) NOT NULL DEFAULT 0 COMMENT '删除(0正常2删除)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '大屏项目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of go_view_project
-- ----------------------------

-- ----------------------------
-- Table structure for go_view_project_data
-- ----------------------------
DROP TABLE IF EXISTS `go_view_project_data`;
CREATE TABLE `go_view_project_data`
(
    `id`         bigint(20) NOT NULL COMMENT '编号',
    `project_id` bigint(20) NOT NULL COMMENT '项目',
    `content`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
    `del_flag`   int(1) NOT NULL DEFAULT 0 COMMENT '删除(0正常2删除)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '大屏项目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of go_view_project_data
-- ----------------------------

-- ----------------------------
-- Table structure for go_view_user
-- ----------------------------
DROP TABLE IF EXISTS `go_view_user`;
CREATE TABLE `go_view_user`
(
    `id`       bigint(20) NOT NULL COMMENT '编号',
    `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
    `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
    `del_flag` int(1) NOT NULL DEFAULT 0 COMMENT '删除(0正常2删除)',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '大屏账号表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of go_view_user
-- ----------------------------
INSERT INTO `go_view_user`
VALUES (1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 0);

SET
FOREIGN_KEY_CHECKS = 1;

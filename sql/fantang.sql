SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ft_catering
-- ----------------------------
DROP TABLE IF EXISTS `ft_catering`;
CREATE TABLE `ft_catering`
(
    `id`                int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `patient_id`        int(11) NULL DEFAULT NULL COMMENT '病人 id',
    `type`              int(2) NULL DEFAULT NULL COMMENT '正餐类型',
    `number`            int(11) NULL DEFAULT NULL COMMENT '配餐号',
    `frequency`         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配餐频次',
    `catering_usage`    int(2) NULL DEFAULT NULL COMMENT '用法',
    `is_replace`        int(2) NULL DEFAULT NULL COMMENT '是否代替正餐',
    `flag`              int(2) NULL DEFAULT NULL COMMENT '作废标志',
    `update_at`         datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    `update_by`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
    `create_at`         datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_by`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `catering_describe` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ft_config
-- ----------------------------
DROP TABLE IF EXISTS `ft_config`;
CREATE TABLE `ft_config`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT,
    `corp_id`      int(255) NULL DEFAULT NULL,
    `config_key`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `config_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `flag`         int(11) NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统参数表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ft_config
-- ----------------------------
INSERT INTO `ft_config`
VALUES (1, NULL, 'breakfast_start', NULL, NULL);
INSERT INTO `ft_config`
VALUES (2, NULL, 'breakfast_end', NULL, NULL);
INSERT INTO `ft_config`
VALUES (3, NULL, 'lunch_start', NULL, NULL);
INSERT INTO `ft_config`
VALUES (4, NULL, 'lunch_end', NULL, NULL);
INSERT INTO `ft_config`
VALUES (5, NULL, 'dinner_start', NULL, NULL);
INSERT INTO `ft_config`
VALUES (6, NULL, 'dinner_end', NULL, NULL);

-- ----------------------------
-- Table structure for ft_depart
-- ----------------------------
DROP TABLE IF EXISTS `ft_depart`;
CREATE TABLE `ft_depart`
(
    `depart_id`   int(20) NOT NULL AUTO_INCREMENT COMMENT '科室编号',
    `depart_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室名称',
    `depart_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室编号',
    PRIMARY KEY (`depart_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ft_depart
-- ----------------------------
INSERT INTO `ft_depart`
VALUES (2, '精神一科', 'j1k');
INSERT INTO `ft_depart`
VALUES (3, '精神二科', 'j2k');
INSERT INTO `ft_depart`
VALUES (4, '医保科', '011');
INSERT INTO `ft_depart`
VALUES (5, '杜阮颐养院', 'dr');

-- ----------------------------
-- Table structure for ft_food
-- ----------------------------
DROP TABLE IF EXISTS `ft_food`;
CREATE TABLE `ft_food`
(
    `food_id`     int(20) NOT NULL AUTO_INCREMENT COMMENT '食品id',
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
    `picture_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片地址',
    `price`       decimal(10, 2) NULL DEFAULT NULL COMMENT '售价',
    `flag`        int(11) NULL DEFAULT NULL COMMENT '启用标志',
    `type`        int(11) NULL DEFAULT NULL COMMENT '食品分类',
    PRIMARY KEY (`food_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ft_food
-- ----------------------------
INSERT INTO `ft_food`
VALUES (3, '粥', NULL, 2.00, NULL, 1);
INSERT INTO `ft_food`
VALUES (4, '粉', NULL, 3.00, NULL, 1);
INSERT INTO `ft_food`
VALUES (5, '好饭', NULL, 6.00, NULL, 1);
INSERT INTO `ft_food`
VALUES (6, '烂饭', NULL, 6.00, NULL, 1);
INSERT INTO `ft_food`
VALUES (7, '优米', NULL, 7.00, NULL, 1);
INSERT INTO `ft_food`
VALUES (8, '面', NULL, 6.00, NULL, 1);
INSERT INTO `ft_food`
VALUES (9, '鸡蛋', NULL, 1.50, NULL, 2);
INSERT INTO `ft_food`
VALUES (10, '营养汤', NULL, 2.00, NULL, 3);
INSERT INTO `ft_food`
VALUES (11, '老火汤', NULL, 3.00, NULL, 3);
INSERT INTO `ft_food`
VALUES (14, '早餐（养老院）', NULL, 2.00, NULL, 1);
INSERT INTO `ft_food`
VALUES (15, '白粥肉沫菜靡', NULL, 2.00, NULL, 1);
INSERT INTO `ft_food`
VALUES (16, '稀饭肉沫菜靡', NULL, 2.00, NULL, 1);
INSERT INTO `ft_food`
VALUES (17, '米饭肉沫菜靡', NULL, 2.00, NULL, 1);
INSERT INTO `ft_food`
VALUES (18, '粥靡', NULL, 2.00, NULL, 1);
INSERT INTO `ft_food`
VALUES (19, '普通餐', NULL, 7.00, NULL, 1);

-- ----------------------------
-- Table structure for ft_food_default
-- ----------------------------
DROP TABLE IF EXISTS `ft_food_default`;
CREATE TABLE `ft_food_default`
(
    `id`         int(11) NOT NULL AUTO_INCREMENT,
    `type`       int(11) NULL DEFAULT NULL COMMENT '类型',
    `food_list`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜品列表',
    `created_at` datetime(0) NULL DEFAULT NULL,
    `created_by` int(11) NULL DEFAULT NULL,
    `updated_at` datetime(0) NULL DEFAULT NULL,
    `updated_by` int(11) NULL DEFAULT NULL,
    `price`      decimal(10, 2) NULL DEFAULT NULL COMMENT '总价格',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ft_food_demand
-- ----------------------------
DROP TABLE IF EXISTS `ft_food_demand`;
CREATE TABLE `ft_food_demand`
(
    `id`          int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `patient_id`  int(11) NULL DEFAULT NULL COMMENT '病人id',
    `foods`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '正餐清单',
    `type`        int(11) NULL DEFAULT NULL COMMENT '用餐类型',
    `create_at`   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `update_at`   datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
    `update_by`   int(11) NULL DEFAULT NULL COMMENT '更新人',
    `update_from` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新来源（1.手动更新 2.报餐时记住）',
    `vegetables`  int(2) NULL DEFAULT 0 COMMENT '加蔬菜',
    `meat`        int(2) NULL DEFAULT 0 COMMENT '加肉',
    `rice`        int(2) NULL DEFAULT 0 COMMENT '加饭',
    `egg`         int(2) NULL DEFAULT 0 COMMENT '加蛋',
    `order_info`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单详情',
    `flag`        int(2) NULL DEFAULT NULL COMMENT '启用状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 389 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ft_invoice
-- ----------------------------
DROP TABLE IF EXISTS `ft_invoice`;
CREATE TABLE `ft_invoice`
(
    `id`              int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `invoice_unit`    varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票单位',
    `invoice_id`      int(11) NULL DEFAULT NULL COMMENT '发票 id',
    `create_at`       datetime(0) NULL DEFAULT NULL COMMENT '日期',
    `drawer`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开票人',
    `collection_type` int(2) NULL DEFAULT NULL COMMENT '收款方式',
    `payable`         decimal(10, 2) NULL DEFAULT NULL COMMENT '应收',
    `receipts`        decimal(10, 2) NULL DEFAULT NULL COMMENT '实收',
    `voucher_list`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '凭证列表',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ft_nutrition_food
-- ----------------------------
DROP TABLE IF EXISTS `ft_nutrition_food`;
CREATE TABLE `ft_nutrition_food`
(
    `id`        int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `name`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营养餐名称',
    `price`     decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
    `flag`      int(2) NULL DEFAULT NULL COMMENT '启用标志',
    `create_at` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `apply`     int(2) NULL DEFAULT NULL COMMENT '用途',
    `start_at`  datetime(0) NULL DEFAULT NULL COMMENT '开餐时间',
    `type`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '餐次',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ft_order
-- ----------------------------
DROP TABLE IF EXISTS `ft_order`;
CREATE TABLE `ft_order`
(
    `order_id`       int(20) NOT NULL AUTO_INCREMENT COMMENT '订单 id',
    `order_type`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单类型',
    `staff_id`       int(20) NULL DEFAULT NULL COMMENT '员工 id',
    `order_list`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '清单',
    `total_price`    decimal(10, 2) NULL DEFAULT NULL COMMENT '总价',
    `discount`       decimal(3, 2) NULL DEFAULT NULL COMMENT '折扣',
    `receipts`       decimal(10, 2) NULL DEFAULT NULL COMMENT '实收',
    `create_at`      datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_by`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `order_src`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单来源',
    `current_price`  decimal(10, 2) NULL DEFAULT NULL COMMENT '订单现售',
    `pay_type`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付方式',
    `pay_flag`       int(1) NULL DEFAULT NULL COMMENT '支付标志',
    `expired_flag`   int(1) NULL DEFAULT NULL COMMENT '过期标志',
    `write_off_flag` int(1) NULL DEFAULT NULL COMMENT '核销标志',
    `write_off_at`   datetime(0) NULL DEFAULT NULL COMMENT '核销时间',
    `is_expired`     int(1) NULL DEFAULT NULL COMMENT '是否过期',
    `device_id`      int(20) NULL DEFAULT NULL COMMENT '核销设备 id',
    PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '饭堂订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ft_order_log
-- ----------------------------
DROP TABLE IF EXISTS `ft_order_log`;
CREATE TABLE `ft_order_log`
(
    `id`        int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `create_by` int(11) NULL DEFAULT NULL COMMENT '报餐人',
    `create_at` datetime(0) NULL DEFAULT NULL COMMENT '报餐时间',
    `info`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报餐信息',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '报餐操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ft_params
-- ----------------------------
DROP TABLE IF EXISTS `ft_params`;
CREATE TABLE `ft_params`
(
    `params_id`      int(20) NOT NULL AUTO_INCREMENT COMMENT '参数 id',
    `order_discount` double(3, 2
) NULL DEFAULT NULL COMMENT '订餐优惠结构',
  `dining_at` datetime(0) NULL DEFAULT NULL COMMENT '开餐时间',
  `sync_at` datetime(0) NULL DEFAULT NULL COMMENT '数据同步时间',
  `cashier_id` int(20) NULL DEFAULT NULL COMMENT '收银台 id',
  `pad_id` int(20) NULL DEFAULT NULL COMMENT '订餐平板 id',
  PRIMARY KEY (`params_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '参数表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ft_patient
-- ----------------------------
DROP TABLE IF EXISTS `ft_patient`;
CREATE TABLE `ft_patient`
(
    `patient_id`  int(20) NOT NULL AUTO_INCREMENT COMMENT '病人id',
    `name`        varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `depart_id`   int(11) NULL DEFAULT NULL COMMENT '所属部门id',
    `bed_id`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '床号',
    `hospital_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '住院号',
    `sync_flag`   tinyint(4) NULL DEFAULT NULL COMMENT '同步标志',
    `off_flag`    tinyint(4) NULL DEFAULT NULL COMMENT '出院标志',
    `create_at`   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`patient_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 125 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ft_prepayment
-- ----------------------------
DROP TABLE IF EXISTS `ft_prepayment`;
CREATE TABLE `ft_prepayment`
(
    `prepayment_id`   int(20) NOT NULL AUTO_INCREMENT COMMENT '预付费id',
    `patient_id`      int(11) NULL DEFAULT NULL COMMENT '病人id',
    `collect_at`      datetime(0) NULL DEFAULT NULL COMMENT '收款时间',
    `collect_by`      varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收款员',
    `settlement_at`   datetime(0) NULL DEFAULT NULL COMMENT '结算时间',
    `settlement_by`   int(11) NULL DEFAULT NULL COMMENT '结算员',
    `settlement_id`   int(11) NULL DEFAULT NULL COMMENT '结算报表id',
    `settlement_flag` int(11) NULL DEFAULT NULL COMMENT '结算标志',
    `prepaid`         decimal(10, 2) NULL DEFAULT NULL COMMENT '预付费金额',
    `prepaid_at`      datetime(0) NULL DEFAULT NULL COMMENT '预付费时间',
    `deleted`         int(2) NULL DEFAULT NULL,
    `deleted_by`      int(11) NULL DEFAULT NULL,
    `deleted_at`      datetime(0) NULL DEFAULT NULL,
    PRIMARY KEY (`prepayment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ft_report_meals
-- ----------------------------
DROP TABLE IF EXISTS `ft_report_meals`;
CREATE TABLE `ft_report_meals`
(
    `id`              int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `create_at`       date NULL DEFAULT NULL COMMENT '报餐日期',
    `type`            int(11) NULL DEFAULT NULL COMMENT '报餐类型',
    `patient_id`      int(11) NULL DEFAULT NULL COMMENT '病人id',
    `create_by`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报餐人',
    `foods`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单列表',
    `price`           decimal(10, 2) NULL DEFAULT NULL COMMENT '总价',
    `settlement_flag` int(255) NULL DEFAULT 0 COMMENT '结算标志',
    `settlement_at`   datetime(0) NULL DEFAULT NULL COMMENT '结算时间',
    `settlement_by`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结算人',
    `dining_at`       datetime(0) NULL DEFAULT NULL COMMENT '用餐时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1041 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ft_settle
-- ----------------------------
DROP TABLE IF EXISTS `ft_settle`;
CREATE TABLE `ft_settle`
(
    `settle_id`  int(20) NOT NULL AUTO_INCREMENT COMMENT '结算 id',
    `patient_id` int(20) NULL DEFAULT NULL COMMENT '病人 id',
    `settle_at`  datetime(0) NULL DEFAULT NULL COMMENT '结算日期',
    `opera`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员',
    `list`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '记录清单',
    `price`      decimal(10, 2) NULL DEFAULT NULL COMMENT '结算总价',
    `payable`    decimal(10, 2) NULL DEFAULT NULL COMMENT '应收',
    `receipts`   decimal(10, 2) NULL DEFAULT NULL COMMENT '实收',
    `type`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结算类型',
    `flag`       int(1) NULL DEFAULT NULL COMMENT '退押金标志',
    `refund`     decimal(10, 2) NULL DEFAULT NULL COMMENT '退款总额',
    PRIMARY KEY (`settle_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '结算报表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ft_staff_demand
-- ----------------------------
DROP TABLE IF EXISTS `ft_staff_demand`;
CREATE TABLE `ft_staff_demand`
(
    `id`          int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `staff_id`    int(11) NULL DEFAULT NULL COMMENT '员工 id',
    `foods`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '正餐清单',
    `type`        int(11) NULL DEFAULT NULL COMMENT '用餐类型',
    `create_at`   datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_by`   int(11) NULL DEFAULT NULL COMMENT '创建人',
    `update_at`   datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
    `update_by`   int(11) NULL DEFAULT NULL COMMENT '更新人',
    `update_from` int(2) NULL DEFAULT NULL COMMENT '更新来源',
    `order_info`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单详情',
    `demand_mode` int(2) NULL DEFAULT NULL COMMENT '报餐模式',
    `stop_flag`   int(2) NULL DEFAULT NULL COMMENT '停用标志',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ft_staff_info
-- ----------------------------
DROP TABLE IF EXISTS `ft_staff_info`;
CREATE TABLE `ft_staff_info`
(
    `staff_id`    int(11) NOT NULL AUTO_INCREMENT COMMENT '员工 id',
    `depart_id`   int(11) NULL DEFAULT NULL COMMENT '科室 id',
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `post`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '岗位',
    `role`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色',
    `password`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
    `create_at`   datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `create_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `flag`        int(1) NULL DEFAULT NULL COMMENT '启用标志',
    `balance`     decimal(10, 2) NULL DEFAULT NULL COMMENT '补贴余额',
    `staff_type`  int(11) NULL DEFAULT NULL COMMENT '员工类别',
    `corp_name`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属公司',
    `picture_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '照片',
    `dept_list`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报餐科室列表',
    `qr_code`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '二维码',
    `sex`         int(2) NULL DEFAULT NULL COMMENT '性别',
    `tel`         varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
    PRIMARY KEY (`staff_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '员工信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ft_staff_info
-- ----------------------------
INSERT INTO `ft_staff_info`
VALUES (27, 2, '马保国', '医生', NULL, '123456', '2020-11-25 16:20:10', NULL, 1, NULL, 1, NULL, NULL, NULL, NULL, 0,
        '123456');
INSERT INTO `ft_staff_info`
VALUES (28, NULL, '马保国2', NULL, NULL, '123456', '2020-11-25 16:20:51', NULL, NULL, NULL, 2, '浑元形意太极门', NULL, '2,3',
        NULL, 1, NULL);
INSERT INTO `ft_staff_info`
VALUES (30, NULL, '11', NULL, NULL, '123456', '2020-11-25 23:57:49', NULL, NULL, NULL, 2, '111', NULL, '2,3', NULL, 0,
        NULL);

-- ----------------------------
-- Table structure for ft_staff_stop_meals
-- ----------------------------
DROP TABLE IF EXISTS `ft_staff_stop_meals`;
CREATE TABLE `ft_staff_stop_meals`
(
    `id`          int(11) NOT NULL,
    `staff_id`    int(11) NULL DEFAULT NULL COMMENT '员工id',
    `type`        int(11) NULL DEFAULT NULL COMMENT '报餐类型',
    `demand_date` date NULL DEFAULT NULL COMMENT '停餐日期',
    `create_at`   datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP (0) COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '员工停餐登记表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ft_staff_subsidy
-- ----------------------------
DROP TABLE IF EXISTS `ft_staff_subsidy`;
CREATE TABLE `ft_staff_subsidy`
(
    `subsidy_id`   int(20) NOT NULL AUTO_INCREMENT COMMENT '补贴流水 id',
    `staff_id`     int(20) NULL DEFAULT NULL COMMENT '员工 id',
    `subsidy_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '补贴类型',
    `income_type`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收支类型',
    `price`        decimal(10, 2) NULL DEFAULT NULL COMMENT '金额',
    `consum_at`    datetime(0) NULL DEFAULT NULL COMMENT '消费日期',
    `order_id`     int(20) NULL DEFAULT NULL COMMENT '消费订单 id',
    PRIMARY KEY (`subsidy_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '员工补贴表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ft_subsidy
-- ----------------------------
DROP TABLE IF EXISTS `ft_subsidy`;
CREATE TABLE `ft_subsidy`
(
    `subsidy_id`    int(20) NOT NULL AUTO_INCREMENT COMMENT '补贴 id',
    `type`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '补贴类型',
    `price`         decimal(10, 2) NULL DEFAULT NULL COMMENT '金额',
    `subsidy_range` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '范围',
    `cycle`         varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '周期',
    `flag`          int(1) NULL DEFAULT NULL COMMENT '启用标志',
    `create_at`     datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
    `create_by`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (`subsidy_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '补贴管理表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ft_sync
-- ----------------------------
DROP TABLE IF EXISTS `ft_sync`;
CREATE TABLE `ft_sync`
(
    `hospital_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '住院号',
    `name`        varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `depart_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '科室名称',
    `bed_id`      varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '床号'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ft_sync_log
-- ----------------------------
DROP TABLE IF EXISTS `ft_sync_log`;
CREATE TABLE `ft_sync_log`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT,
    `created_at`   datetime(0) NULL DEFAULT NULL COMMENT '同步时间',
    `total_record` int(11) NULL DEFAULT NULL COMMENT '总接收记录',
    `sync_record`  int(11) NULL DEFAULT NULL COMMENT '同步记录',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据同步日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ft_week_menu
-- ----------------------------
DROP TABLE IF EXISTS `ft_week_menu`;
CREATE TABLE `ft_week_menu`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `dinner_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用餐类型',
    `weekday`     varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '星期几',
    `foods`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜品列表',
    `price`       decimal(10, 2) NULL DEFAULT NULL COMMENT '总价格',
    `flag`        int(1) NULL DEFAULT NULL COMMENT '启用标志，说明：=1启用；=0禁用',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ft_week_menu
-- ----------------------------
INSERT INTO `ft_week_menu`
VALUES (1, '早餐', '周一', '4,5,3', 11.00, 1);
INSERT INTO `ft_week_menu`
VALUES (2, '午餐', '周一', '4,5,8', 11.00, 1);
INSERT INTO `ft_week_menu`
VALUES (3, '晚餐', '周一', '4,5,8', 11.00, 1);
INSERT INTO `ft_week_menu`
VALUES (4, '早餐', '周二', '4,5', 5.00, 1);
INSERT INTO `ft_week_menu`
VALUES (5, '午餐', '周二', '3,4', 5.00, 1);
INSERT INTO `ft_week_menu`
VALUES (6, '晚餐', '周二', '8,7', 5.00, 1);
INSERT INTO `ft_week_menu`
VALUES (7, '早餐', '周三', '5,6,7,3', 21.00, 1);
INSERT INTO `ft_week_menu`
VALUES (8, '午餐', '周三', '6,3', 8.00, 1);
INSERT INTO `ft_week_menu`
VALUES (9, '晚餐', '周三', '7,3', 9.00, 1);
INSERT INTO `ft_week_menu`
VALUES (10, '早餐', '周四', '4,3', 5.00, 1);
INSERT INTO `ft_week_menu`
VALUES (11, '午餐', '周四', '8,3', 8.00, 1);
INSERT INTO `ft_week_menu`
VALUES (12, '晚餐', '周四', '8,3', 8.00, 1);
INSERT INTO `ft_week_menu`
VALUES (13, '早餐', '周五', '6,3', 8.00, 1);
INSERT INTO `ft_week_menu`
VALUES (14, '午餐', '周五', '7,4', 10.00, 1);
INSERT INTO `ft_week_menu`
VALUES (15, '晚餐', '周五', '8,4', 9.00, 1);
INSERT INTO `ft_week_menu`
VALUES (16, '早餐', '周六', '9,3', 3.50, 1);
INSERT INTO `ft_week_menu`
VALUES (17, '午餐', '周六', '7,3', 9.00, 1);
INSERT INTO `ft_week_menu`
VALUES (18, '晚餐', '周六', '5,3', 8.00, 1);
INSERT INTO `ft_week_menu`
VALUES (19, '早餐', '周日', '3,4', 5.00, 1);
INSERT INTO `ft_week_menu`
VALUES (20, '午餐', '周日', '4,3', 5.00, 1);
INSERT INTO `ft_week_menu`
VALUES (21, '晚餐', '周日', '3,4', 5.00, 1);

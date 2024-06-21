CREATE TABLE liteflow_chain
(
    `id`               bigint(20)  NOT NULL AUTO_INCREMENT COMMENT 'id',
    `application_name` varchar(32) NOT NULL COMMENT '配置环境',
    `chain_name`       varchar(32) NOT NULL COMMENT '编排规则ID',
    `chain_status`     tinyint(1)  NOT NULL DEFAULT '0' COMMENT '状态（0未激活 1激活）',
    `el_str`           text        NOT NULL COMMENT 'EL表达式',
    `name_space`       varchar(32)          DEFAULT NULL COMMENT 'namespace（决策路由很多，想要判断某一组决策路由时在chain层面加入namespace参数）',
    `route`            text COMMENT '决策路由EL',
    `del_flag`         char(1)              DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `create_dept`      bigint(20)           DEFAULT NULL COMMENT '创建部门',
    `create_by`        bigint(20)           DEFAULT NULL COMMENT '创建者',
    `create_time`      datetime             DEFAULT NULL COMMENT '创建时间',
    `update_by`        bigint(20)           DEFAULT NULL COMMENT '更新者',
    `update_time`      datetime             DEFAULT NULL COMMENT '更新时间',
    `remark`           varchar(500)         DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) engine = innodb comment = '编排规则表';

CREATE TABLE liteflow_script
(
    `id`               bigint(20)  NOT NULL AUTO_INCREMENT COMMENT 'id',
    `application_name` varchar(32) NOT NULL COMMENT '配置环境',
    `script_id`        varchar(32) NOT NULL COMMENT '脚本ID',
    `script_name`      varchar(64) NOT NULL COMMENT '脚本名称',
    `script_language`  varchar(32) NOT NULL COMMENT '脚本语言类型（groovy | qlexpress | js | python | lua | aviator | java | kotlin）',
    `script_type`      varchar(16) NOT NULL COMMENT '脚本类型(script普通脚本节点，脚本里无需返回 switch_script选择脚本节点，脚本里需要返回选择的节点Id boolean_script条件脚本节点，脚本里需要返回true/false for_script数量循环节点，脚本里需要返回数值类型，表示循环次数)',
    `script_status`    tinyint(1)  NOT NULL DEFAULT '0' COMMENT '状态（0未激活 1激活）',
    `script_data`      text        NOT NULL COMMENT '脚本内容',
    `del_flag`         char(1)              DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `create_dept`      bigint(20)           DEFAULT NULL COMMENT '创建部门',
    `create_by`        bigint(20)           DEFAULT NULL COMMENT '创建者',
    `create_time`      datetime             DEFAULT NULL COMMENT '创建时间',
    `update_by`        bigint(20)           DEFAULT NULL COMMENT '更新者',
    `update_time`      datetime             DEFAULT NULL COMMENT '更新时间',
    `remark`           varchar(500)         DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
) engine = innodb comment = '脚本表';

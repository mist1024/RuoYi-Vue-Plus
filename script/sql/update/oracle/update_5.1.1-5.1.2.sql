delete from sys_menu where menu_id in (1604, 1605);
insert into sys_menu values('1620', '配置列表', '118', '5', '#', '', '', 1, 0, 'F', '0', '0', 'system:ossConfig:list',   '#', 103, 1, sysdate, null, null, '');
insert into sys_menu values('1621', '配置添加', '118', '6', '#', '', '', 1, 0, 'F', '0', '0', 'system:ossConfig:add',    '#', 103, 1, sysdate, null, null, '');
insert into sys_menu values('1622', '配置编辑', '118', '6', '#', '', '', 1, 0, 'F', '0', '0', 'system:ossConfig:edit',   '#', 103, 1, sysdate, null, null, '');
insert into sys_menu values('1623', '配置删除', '118', '6', '#', '', '', 1, 0, 'F', '0', '0', 'system:ossConfig:remove', '#', 103, 1, sysdate, null, null, '');

ALTER TABLE sys_dept ADD dept_category VARCHAR2(100) DEFAULT NULL COMMENT '部门类别编码';
ALTER TABLE sys_post ADD dept_id NUMBER(20) NOT NULL COMMENT '部门id', ADD post_category VARCHAR2(100) DEFAULT NULL COMMENT '岗位类别编码';
UPDATE sys_post SET dept_id = 100;

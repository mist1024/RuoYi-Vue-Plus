DELETE FROM sys_menu WHERE menu_id IN (1604, 1605);
GO
INSERT sys_menu VALUES (1620, N'配置列表', 118, 5, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:ossConfig:list', N'#', 103, 1, getdate(), NULL, NULL, N'');
GO
INSERT sys_menu VALUES (1621, N'配置添加', 118, 6, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:ossConfig:add', N'#', 103, 1, getdate(), NULL, NULL, N'');
GO
INSERT sys_menu VALUES (1622, N'配置编辑', 118, 6, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:ossConfig:edit', N'#', 103, 1, getdate(), NULL, NULL, N'');
GO
INSERT sys_menu VALUES (1623, N'配置删除', 118, 6, N'#', N'', N'', 1, 0, N'F', N'0', N'0', N'system:ossConfig:remove', N'#', 103, 1, getdate(), NULL, NULL, N'');
GO

ALTER TABLE sys_dept ADD dept_category VARCHAR(100) DEFAULT NULL;
EXEC sp_addextendedproperty 'MS_Description', '部门类别编码', 'SCHEMA', 'dbo', 'TABLE', 'sys_dept', 'COLUMN', 'dept_category';
GO
ALTER TABLE sys_post ADD dept_id BIGINT NOT NULL;
ALTER TABLE sys_post ADD post_category VARCHAR(100) DEFAULT NULL;
EXEC sp_addextendedproperty 'MS_Description', '部门id', 'SCHEMA', 'dbo', 'TABLE', 'sys_post', 'COLUMN', 'dept_id';
EXEC sp_addextendedproperty 'MS_Description', '岗位类别编码', 'SCHEMA', 'dbo', 'TABLE', 'sys_post', 'COLUMN', 'post_category';
GO
UPDATE sys_post SET dept_id = 100;

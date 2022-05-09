ALTER TABLE [sys_oss_config] ADD [domain] nvarchar(255) DEFAULT '' NULL
GO

EXEC sp_addextendedproperty
'MS_Description', N'自定义域名',
'SCHEMA', N'dbo',
'TABLE', N'sys_oss_config',
'COLUMN', N'domain'
GO

UPDATE [sys_oss_config] SET [access_key] = N'ruoyi', [secret_key] = N'ruoyi123', [endpoint] = N'localhost:9000' WHERE [oss_config_id] = 1
GO

UPDATE [sys_oss_config] SET [endpoint] = N's3-cn-north-1.qiniucs.com' WHERE [oss_config_id] = 2
GO

UPDATE [sys_oss_config] SET [endpoint] = N'<桶名>.oss-cn-beijing.aliyuncs.com' WHERE [oss_config_id] = 3
GO

UPDATE [sys_oss_config] SET [endpoint] = N'<桶名>.cos.ap-beijing.myqcloud.com' WHERE [oss_config_id] = 4
GO

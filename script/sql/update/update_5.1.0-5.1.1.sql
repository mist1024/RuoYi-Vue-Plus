ALTER TABLE sys_logininfor
    ADD COLUMN user_type VARCHAR(10) NULL DEFAULT 'sys_user' COMMENT '用户类型（sys_user系统用户）' AFTER `user_name`,
    ADD COLUMN client_key VARCHAR(32)  NULL DEFAULT NULL COMMENT '客户端' AFTER `user_type`,
    ADD COLUMN device_type VARCHAR(32) NULL DEFAULT NULL COMMENT '设备类型' AFTER `client_key`;

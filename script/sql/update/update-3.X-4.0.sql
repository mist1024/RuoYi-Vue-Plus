ALTER TABLE `sys_user` MODIFY COLUMN `user_type` varchar(10) DEFAULT 'sys_user' COMMENT '用户类型（sys_user系统用户）' AFTER `nick_name`;

UPDATE `sys_user` SET `nick_name` = '疯狂的狮子Li', `user_type` = 'sys_user', `email` = 'crazyLionLi@163.com' WHERE `user_id` = 1;
UPDATE `sys_user` SET `user_name` = 'lionli', `nick_name` = '疯狂的狮子Li', `user_type` = 'sys_user', `email` = 'crazyLionLi@163.com' WHERE `user_id` = 2;
UPDATE `sys_user` SET `user_type` = 'sys_user' WHERE `user_id` = 3;
UPDATE `sys_user` SET `user_type` = 'sys_user' WHERE `user_id` = 4;

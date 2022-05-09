ALTER TABLE "sys_oss_config" ADD COLUMN "domain" varchar(255);

COMMENT ON COLUMN "sys_oss_config"."domain" IS '自定义域名';

update sys_oss_config set endpoint = 'localhost:9000' where oss_config_id = 1;
update sys_oss_config set endpoint = 's3-cn-north-1.qiniucs.com', region = '' where oss_config_id = 2;
update sys_oss_config set endpoint = '<桶名>.oss-cn-beijing.aliyuncs.com' where oss_config_id = 3;
update sys_oss_config set endpoint = '<桶名>.cos.ap-beijing.myqcloud.com' where oss_config_id = 4;

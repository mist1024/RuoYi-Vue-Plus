-- 给gen_table_column表新增column_max_length字段
ALTER TABLE `gen_table_column`
ADD COLUMN `column_max_length` int DEFAULT NULL COMMENT '字符最大长度'
AFTER `column_type`;

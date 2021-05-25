create table edu_exam
(
    id int auto_increment
        primary key,
    exam_no varchar(64) null comment '考试编号',
    exam_name varchar(64) null comment '考试名称',
    exam_short_name varchar(64) null comment '考试简称',
    exam_date datetime null comment '考试日期',
    create_time datetime null comment '创建时间',
    create_by varchar(64) null comment '创建人id',
    update_time datetime null comment '修改时间',
    update_by varchar(64) null comment '修改人id',
    constraint edu_exam_exam_no_uindex
        unique (exam_no)
)
    comment '考试信息' charset=utf8;

create table ruoyi.edu_student
(
    id int auto_increment
        primary key,
    id_card_no varchar(64) null comment '身份证号',
    student_name varchar(64) null comment '学生姓名',
    current_grade varchar(64) null comment '学生当前所在年级',
    current_class varchar(64) null comment '学生当前所在班级',
    current_school varchar(64) null comment '学生当前所在学校',
    mobile varchar(64) null comment '手机号',
    father_mobile varchar(64) null comment '父亲手机',
    mother_mobile varchar(64) null comment '母亲手机',
    create_time datetime null comment '创建时间',
    create_by varchar(64) null comment '创建人id',
    update_time datetime null comment '修改时间',
    update_by varchar(64) null comment '修改人id',
    constraint edu_student_id_card_no_uindex
        unique (id_card_no)
)
    comment '学生信息表' charset=utf8;

create table ruoyi.edu_student_exam
(
    id int auto_increment
        primary key,
    student_id int null comment '学生id',
    exam_id int null comment '考试id',
    chinese_score decimal(10,2) null comment '语文分数',
    math_score decimal(10,2) null comment '数学分数',
    english_score decimal(10,2) null comment '英语分数',
    chemistry_score decimal(10,2) null comment '化学分数',
    physics_score decimal(10,2) null comment '物理分数',
    biology_score decimal(10,2) null comment '生物分数',
    politics_score decimal(10,2) null comment '政治分数',
    history_score decimal(10,2) null comment '历史分数',
    geography_score decimal(10,2) null comment '地理分数',
    ethic_score decimal(10,2) null comment '思想品德分数',
    class_rank int null comment '班级排名',
    grade_rank int null comment '级部排名',
    create_time datetime null comment '创建时间',
    create_by varchar(64) null comment '创建人id',
    update_time datetime null comment '修改时间',
    update_by varchar(64) null comment '修改人id'
)
    comment '学生考试信息' charset=utf8;


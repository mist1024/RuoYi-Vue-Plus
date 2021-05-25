package com.ruoyi.edu.vo;

import com.ruoyi.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 学生考试信息视图对象 mall_package
 *
 * @author keyleaf
 * @date 2021-05-25
 */
@Data
@ApiModel("学生考试信息视图对象")
public class EduStudentExamVo {
	private static final long serialVersionUID = 1L;

	/** $pkColumn.columnComment */
	@ApiModelProperty("$pkColumn.columnComment")
	private Long id;

	/** 语文分数 */
	@Excel(name = "语文分数")
	@ApiModelProperty("语文分数")
	private BigDecimal chineseScore;
	/** 数学分数 */
	@Excel(name = "数学分数")
	@ApiModelProperty("数学分数")
	private BigDecimal mathScore;
	/** 英语分数 */
	@Excel(name = "英语分数")
	@ApiModelProperty("英语分数")
	private BigDecimal englishScore;
	/** 化学分数 */
	@Excel(name = "化学分数")
	@ApiModelProperty("化学分数")
	private BigDecimal chemistryScore;
	/** 物理分数 */
	@Excel(name = "物理分数")
	@ApiModelProperty("物理分数")
	private BigDecimal physicsScore;
	/** 生物分数 */
	@Excel(name = "生物分数")
	@ApiModelProperty("生物分数")
	private BigDecimal biologyScore;
	/** 政治分数 */
	@Excel(name = "政治分数")
	@ApiModelProperty("政治分数")
	private BigDecimal politicsScore;
	/** 历史分数 */
	@Excel(name = "历史分数")
	@ApiModelProperty("历史分数")
	private BigDecimal historyScore;
	/** 地理分数 */
	@Excel(name = "地理分数")
	@ApiModelProperty("地理分数")
	private BigDecimal geographyScore;
	/** 思想品德分数 */
	@Excel(name = "思想品德分数")
	@ApiModelProperty("思想品德分数")
	private BigDecimal ethicScore;
	/** 班级排名 */
	@Excel(name = "班级排名")
	@ApiModelProperty("班级排名")
	private Long classRank;
	/** 级部排名 */
	@Excel(name = "级部排名")
	@ApiModelProperty("级部排名")
	private Long gradeRank;

	// 补充主表字段
	private Long studentId;
	// 关联表 eduStudent 字段
	@Excel(name = "身份证号")
	private String eduStudentIdCardNo;
	@Excel(name = "班级")
	private String eduStudentCurrentClass;
	@Excel(name = "年级")
	private String eduStudentCurrentGrade;
	@Excel(name = "学生姓名")
	private String eduStudentStudentName;
	@Excel(name = "手机号")
	private String eduStudentMobile;
	@Excel(name = "父亲手机")
	private String eduStudentFatherMobile;
	@Excel(name = "母亲手机")
	private String eduStudentMotherMobile;
	@Excel(name = "学校")
	private String eduStudentCurrentSchool;

	// 补充主表字段
	private Long examId;
	// 关联表 eduExam 字段
	@Excel(name = "考试日期" , width = 30, dateFormat = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date eduExamExamDate;
	@Excel(name = "考试编号")
	private String eduExamExamNo;
	@Excel(name = "考试名称")
	private String eduExamExamName;


}

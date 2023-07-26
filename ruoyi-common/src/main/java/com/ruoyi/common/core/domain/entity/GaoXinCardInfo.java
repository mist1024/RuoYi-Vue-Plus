package com.ruoyi.common.core.domain.entity;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.exception.ServiceException;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @author Administrator
 * 高新人才接口返回所需
 */

@Data
public class GaoXinCardInfo implements Serializable {

    /**
     * 身份证
     */
    private  String card_id;
    /**
     * 国籍
     */
    private  String nationality;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private  String phone;
    /**
     * 公司名称
     */
    private  String company_name;
    /**
     * 性别
     */
    private  String sex;
    /**
     * 公司所在区域
     */
    private  String district;
    /**
     * 人才类型
     */
    private  String type;
    /**
     * 学历
     */
    private  String education;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (ObjectUtil.isNotNull(type) && ObjectUtil.isNotEmpty(type)) {
            type = StringUtils.trimAllWhitespace(type);
            if (type.length() == 1) {
                this.type = type + "类";
            }else {
                this.type = type;
            }
        }
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        if (ObjectUtil.isNotNull(nationality) && ObjectUtil.isNotEmpty(nationality)) {
            nationality = StringUtils.trimAllWhitespace(nationality);
            if ("中国".equals(nationality)) {
                this.nationality = nationality + "籍";
            }else {
                this.nationality = nationality;
            }
        }
    }
}

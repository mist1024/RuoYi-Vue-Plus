package com.ruoyi.sms.core;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.http.HttpRequest;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.sms.entity.SmsResult;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author Administrator
 */
@Service
public class TelecomSendMsg{
    private final String url ="https://msg.cdht.org.cn:8082/apigbk/BatchSend2";
    private final String CorpID ="jxxc";
    private final String Pwd ="ULul@2023";

    /**
     * 发送短信验证码
     * @param phones
     * @param content
     * @return
     */
    public SmsResult telecomSendCode(String phones, String content,String type) {
        try {
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("CorpID",CorpID);
            paramMap.put("Pwd",Pwd);
            paramMap.put("Mobile",phones);
            String gbk = null;
            if ("register".equals(type)){
                gbk = URLEncoder.encode("尊敬的用户,您正在办理用户注册操作,我们不会向您索要此验证码,切勿告知他人。本次验证码有效性为:"+ Constants.CAPTCHA_EXPIRATION +"分钟,本次验证码为:"+content+"【成都高新人才安居资格认定】", "GBK");
            }else if ("login".equals(type)){
                gbk = URLEncoder.encode("尊敬的用户,您正在办理用户登录操作,我们不会向您索要此验证码,切勿告知他人。本次验证码有效性为:"+ Constants.CAPTCHA_EXPIRATION +"分钟,本次验证码为:"+content+"【成都高新人才安居资格认定】", "GBK");
            }else if ("forgetPwd".equals(type)){
                gbk = URLEncoder.encode("尊敬的用户,您正在办理用户忘记密码操作,我们不会向您索要此验证码,切勿告知他人。本次验证码有效性为:"+ Constants.CAPTCHA_EXPIRATION +"分钟,本次验证码为:"+content+"【成都高新人才安居资格认定】", "GBK");
            }else if("updatePwd".equals(type)){
                gbk = URLEncoder.encode("尊敬的用户,您正在办理用户修改密码,我们不会向您索要此验证码,切勿告知他人。本次验证码有效性为:"+ Constants.CAPTCHA_EXPIRATION +"分钟,本次验证码为:"+content+"【成都高新人才安居资格认定】", "GBK");
            }else if ("updatePhone".equals(type)){

            }
            paramMap.put("Content",gbk);
            String result = HttpRequest.get(url)
                .form(paramMap)//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
            if (returnStatus(result)){
                return SmsResult.builder()
                    .isSuccess(true)
                    .build();
            }
            throw new ServiceException("未知异常");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送短信通知
     * @param phones
     * @param content
     * @return
     */
    public SmsResult telecomSendNotice(String phones, String content) {
        try {
//            String url ="https://msg.cdht.org.cn:8082/apigbk/BatchSend2";
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("CorpID","jxxc");
            paramMap.put("Pwd","ULul@2023");
            paramMap.put("Mobile",phones);
            String gbk = null;
            gbk = URLEncoder.encode(content+"【成都高新人才安居资格认定】", "GBK");
            paramMap.put("Content",gbk);
            String result = HttpRequest.get(url)
                .form(paramMap)//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
            if (returnStatus(result)){
                return SmsResult.builder()
                    .isSuccess(true)
                    .build();
            }
            System.out.println("result2 = " + result);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    /**
     * 状态返回值
     * @param number
     * @return
     */
    private boolean returnStatus(String number){
        if (!StringUtils.isNumeric(number)) {
            switch (number) {
                case "-1":
                    throw new ServiceException("账户或密码错误，未获取到用户信息");
                case "-2":
                    throw new ServiceException("其他错误(未知错误，网络波动，请稍后再试)");
                case "-3":
                    throw new ServiceException("账户配置不正确");
                case "-4":
                    throw new ServiceException("在禁止发送的时间段");
                case "-5":
                    throw new ServiceException("余额不足，请充值");
                case "-6":
                    throw new ServiceException("定时发送时间不是有效的时间格式");
                case "-7":
                    throw new ServiceException("提交信息末尾未签名，请添加中文的企业签名【 】或内容乱码");
                case "-8":
                    throw new ServiceException("发送号码数量超出系统限制");
                case "-9":
                    throw new ServiceException("发送字数超出系统限制长度");
                case "-10":
                    throw new ServiceException("产品不存在");
                case "-11":
                    throw new ServiceException("内容不能再存在签名");
                case "-12":
                    throw new ServiceException("传入参数不正确！");
                case "-13":
                    throw new ServiceException("手机号校验不正确");
                case "-14":
                    throw new ServiceException("内容中存在黑字典关键字");
                case "-15":
                    throw new ServiceException("定时时间格式错误");
                case "-16":
                    throw new ServiceException("扩展号格式不正确");
                case "-17":
                    throw new ServiceException("子号池全被占用，请等待释放后再操作");
                case "-18":
                    throw new ServiceException("该扩展号被投票问卷占用，请使用其他的扩展");
                case "-19":
                    throw new ServiceException("SendSms计费类型选择不正确");
                case "-100":
                    throw new ServiceException("发送失败");
                case "-101":
                    throw new ServiceException("调用频率过快");
                case "-103":
                    throw new ServiceException("IP未导白");
                case "-111":
                    throw new ServiceException("个性短信入口错误");
                case "-200":
                    throw new ServiceException("网络连接失败");
                case "-401":
                    throw new ServiceException("账号没有调用接口权限，请联系客服专员");
            }
        }
        return true;
    }
}

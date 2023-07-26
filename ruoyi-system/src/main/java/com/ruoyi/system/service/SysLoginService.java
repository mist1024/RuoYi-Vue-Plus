package com.ruoyi.system.service;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.event.LogininforEvent;
import com.ruoyi.common.core.domain.dto.RoleDTO;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.event.LogininforEvent;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.XcxLoginUser;
import com.ruoyi.common.enums.DeviceType;
import com.ruoyi.common.enums.LoginType;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.exception.user.UserException;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.redis.RedisUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.BuyHouses;
import com.ruoyi.system.domain.User;
import com.ruoyi.system.domain.dto.DeclareListDTO;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 登录校验方法
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class SysLoginService {

    private final IBuyHousesService iBuyHousesService;

    private final SysUserMapper sysUserMapper;
    private final ISysConfigService configService;
    private final SysPermissionService permissionService;

    private final UserMapper userMapper;

    @Value("${user.password.maxRetryCount}")
    private Integer maxRetryCount;

    @Value("${user.password.lockTime}")
    private Integer lockTime;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        // 验证码开关
        if (captchaEnabled) {
            validateCaptcha(username, code, uuid);
        }
        // 框架登录不限制从什么表查询 只要最终构建出 LoginUser 即可
        SysUser user = loadSysUserByUsername(username);
        checkLogin(LoginType.PASSWORD, username, () -> !BCrypt.checkpw(password, user.getPassword()));
        // 此处可根据登录用户的数据不同 自行创建 loginUser 属性不够用继承扩展就行了
        LoginUser loginUser = buildLoginSysUser(user);
        // 生成token
        LoginHelper.loginByDevice(loginUser, DeviceType.PC);

        recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
        recordLoginInfo(user.getUserId(), username);
        return StpUtil.getTokenValue();
    }

    public String smsLogin(String phonenumber, String smsCode) {
        // 通过手机号查找用户
        SysUser user = loadUserByPhonenumber(phonenumber);
        checkLogin(LoginType.SMS, user.getUserName(), () -> !validateSmsCode(phonenumber, smsCode,CacheConstants.CAPTCHA_CODE_KEY));
        // 此处可根据登录用户的数据不同 自行创建 loginUser
        LoginUser loginUser = buildLoginSysUser(user);
        // 生成token
        LoginHelper.loginByDevice(loginUser, DeviceType.APP);

        recordLogininfor(user.getUserName(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
        recordLoginInfo(user.getUserId(), user.getUserName());
        return StpUtil.getTokenValue();
    }

    public String emailLogin(String email, String emailCode) {
        // 通过手邮箱查找用户
        SysUser user = loadUserByEmail(email);

        checkLogin(LoginType.EMAIL, user.getUserName(), () -> !validateEmailCode(email, emailCode));
        // 此处可根据登录用户的数据不同 自行创建 loginUser
        LoginUser loginUser = buildLoginSysUser(user);
        // 生成token
        LoginHelper.loginByDevice(loginUser, DeviceType.APP);

        recordLogininfor(user.getUserName(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
        recordLoginInfo(user.getUserId(), user.getUserName());
        return StpUtil.getTokenValue();
    }

    public String xcxLogin(String xcxCode) {
        // xcxCode 为 小程序调用 wx.login 授权后获取
        // todo 以下自行实现
        // 校验 appid + appsrcret + xcxCode 调用登录凭证校验接口 获取 session_key 与 openid
        String openid = "";

        // 框架登录不限制从什么表查询 只要最终构建出 LoginUser 即可
        SysUser user = loadUserByOpenid(openid);

        // 此处可根据登录用户的数据不同 自行创建 loginUser 属性不够用继承扩展就行了
        XcxLoginUser loginUser = new XcxLoginUser();
        loginUser.setUserId(user.getUserId());
        loginUser.setUsername(user.getUserName());
        loginUser.setUserType(user.getUserType());
        loginUser.setOpenid(openid);
        // 生成token
        LoginHelper.loginByDevice(loginUser, DeviceType.XCX);

        recordLogininfor(user.getUserName(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
        recordLoginInfo(user.getUserId(), user.getUserName());
        return StpUtil.getTokenValue();
    }

    /**
     * 退出登录
     */
    public void logout() {
        try {
            LoginUser loginUser = LoginHelper.getLoginUser();
            recordLogininfor(loginUser.getUsername(), Constants.LOGOUT, MessageUtils.message("user.logout.success"));
        } catch (NotLoginException ignored) {
        } finally {
            try {
                StpUtil.logout();
            } catch (NotLoginException ignored) {
            }
        }
    }

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     */
    private void recordLogininfor(String username, String status, String message) {
        LogininforEvent logininforEvent = new LogininforEvent();
        logininforEvent.setUsername(username);
        logininforEvent.setStatus(status);
        logininforEvent.setMessage(message);
        logininforEvent.setRequest(ServletUtils.getRequest());
        SpringUtils.context().publishEvent(logininforEvent);
    }

    /**
     * 校验短信验证码
     */
    private boolean validateSmsCode(String phonenumber, String smsCode,String key) {
        String code = RedisUtils.getCacheObject(key + phonenumber);
        if (StringUtils.isBlank(code)) {
            recordLogininfor(phonenumber, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        return code.equals(smsCode);
    }

    /**
     * 校验邮箱验证码
     */
    private boolean validateEmailCode(String email, String emailCode) {
        String code = RedisUtils.getCacheObject(CacheConstants.CAPTCHA_CODE_KEY + email);
        if (StringUtils.isBlank(code)) {
            recordLogininfor(email, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        return code.equals(emailCode);
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.defaultString(uuid, "");
        String captcha = RedisUtils.getCacheObject(verifyKey);
        RedisUtils.deleteObject(verifyKey);
        if (captcha == null) {
            recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error"));
            throw new CaptchaException();
        }
    }

    private SysUser loadSysUserByUsername(String username) {
        SysUser user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
            .eq(SysUser::getUserName, username));
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new UserException("user.not.exists", username);
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new UserException("user.blocked", username);
        }
        return sysUserMapper.selectUserByUserName(username);
    }

    private SysUser loadUserByPhonenumber(String phonenumber) {
        SysUser user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
            .eq(SysUser::getPhonenumber, phonenumber));
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", phonenumber);
            throw new UserException("user.not.exists", phonenumber);
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", phonenumber);
            throw new UserException("user.blocked", phonenumber);
        }
        return sysUserMapper.selectUserByPhonenumber(phonenumber);
    }

    private SysUser loadUserByEmail(String email) {
        SysUser user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
            .select(SysUser::getPhonenumber, SysUser::getStatus)
            .eq(SysUser::getEmail, email));
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", email);
            throw new UserException("user.not.exists", email);
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", email);
            throw new UserException("user.blocked", email);
        }
        return sysUserMapper.selectUserByEmail(email);
    }

    private SysUser loadUserByOpenid(String openid) {
        // 使用 openid 查询绑定用户 如未绑定用户 则根据业务自行处理 例如 创建默认用户
        // todo 自行实现 userService.selectUserByOpenid(openid);
        SysUser user = new SysUser();
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", openid);
            // todo 用户不存在 业务逻辑自行实现
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", openid);
            // todo 用户已被停用 业务逻辑自行实现
        }
        return user;
    }

    /**
     * 构建登录用户
     */
    private LoginUser buildLoginSysUser(SysUser user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getUserId());
        loginUser.setDeptId(user.getDeptId());
        loginUser.setUsername(user.getUserName());
        loginUser.setUserType(user.getUserType());
        loginUser.setNickName(user.getNickName());
        loginUser.setProperties(user.getProperties());
        loginUser.setMenuPermission(permissionService.getMenuPermission(user));
        loginUser.setRolePermission(permissionService.getRolePermission(user));
        loginUser.setDeptName(ObjectUtil.isNull(user.getDept()) ? "" : user.getDept().getDeptName());
        List<RoleDTO> roles = BeanUtil.copyToList(user.getRoles(), RoleDTO.class);
        loginUser.setRoles(roles);
        return loginUser;
    }

    /**
     * 构建登录用户
     */
    private LoginUser buildLoginUser(User user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getId());
        loginUser.setUsername(user.getLoginName());
        loginUser.setCompanyId(user.getCompanyId());
        loginUser.setUserType(user.getUserType());
        return loginUser;
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId, String username) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(ServletUtils.getClientIP());
        sysUser.setLoginDate(DateUtils.getNowDate());
        sysUser.setUpdateBy(username);
        sysUserMapper.updateById(sysUser);
    }

    /**
     * 登录校验
     */
    private void checkLogin(LoginType loginType, String username, Supplier<Boolean> supplier) {
        String errorKey = CacheConstants.PWD_ERR_CNT_KEY + username;
        String loginFail = Constants.LOGIN_FAIL;

        // 获取用户登录错误次数，默认为0 (可自定义限制策略 例如: key + username + ip)
        int errorNumber = ObjectUtil.defaultIfNull(RedisUtils.getCacheObject(errorKey), 0);
        // 锁定时间内登录 则踢出
        if (errorNumber >= maxRetryCount) {
            recordLogininfor(username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
            throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
        }

        if (supplier.get()) {
            // 错误次数递增
            errorNumber++;
            RedisUtils.setCacheObject(errorKey, errorNumber, Duration.ofMinutes(lockTime));
            // 达到规定错误次数 则锁定登录
            if (errorNumber >= maxRetryCount) {
                recordLogininfor(username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
                throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
            } else {
                // 未达到规定错误次数
                recordLogininfor(username, loginFail, MessageUtils.message(loginType.getRetryLimitCount(), errorNumber));
                throw new UserException(loginType.getRetryLimitCount(), errorNumber,maxRetryCount-errorNumber);
            }
        }
        // 登录成功 清空错误次数
        RedisUtils.deleteObject(errorKey);
    }
//------------------------------------------------------------------------------------------------------------------------
    private User loadUserByUsername(String username) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
            .eq(User::getLoginName, username));
         if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new UserException("user.not.exists", username);
        } else if (user.getJurisdiction().equals(2L)) {
            log.info("登录用户：{} 已被停用.", username);
            throw new UserException("user.blocked", username);
        }
        return user;
    }


    /**
     * 客户端用户登录
     * @param username
     * @param password
     * @return
     */
    public Map<String,Object> userLogin(String username, String password) {
        User user = loadUserByUsername(username);
        Date updateTime = user.getUpdateTime();
        if (ObjectUtil.isNull(updateTime)){
            log.info("登录用户:{},密码已过期", username);
            throw new UserException("user.password.expired",username);
        }else if (DateUtil.date().getTime()>DateUtil.offsetMonth(updateTime, 3).getTime()){
            log.info("登录用户:{},密码已过期", username);
            throw new UserException("user.password.expired",username);
        }
        //检验登录
        checkLogin(LoginType.PASSWORD, username, () -> !BCrypt.checkpw(password, user.getPassword()));
        // 此处可根据登录用户的数据不同 自行创建 loginUser
        LoginUser loginUser = buildLoginUser(user);
        // 生成token
        LoginHelper.loginByDevice(loginUser, DeviceType.PC);
        recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
        HashMap<String, Object> map = new HashMap<>();
        String tokenValue = StpUtil.getTokenValue();
        map.put("token",tokenValue);
        map.put("loginName",user.getLoginName());
        return map;
    }

    /**
     * 忘记密码
     * @param username
     * @param password
     * @param code
     * @return
     */
    public R<?> userUpdatePwd(String username, String password, String code) {
        String key = CacheConstants.RORGOT_PASSWORD_SEND_MSG+username;
        boolean matches = username.matches("^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$");
        if (!matches) {
            throw new ServiceException("手机号格式不正确");
        }
        if (!validateSmsCode(username, code,key)){
            throw new UserException("user.jcaptcha.error");
        }
        //修改密码
        User user = loadUserByUsername(username);
        if (ObjectUtil.isNull(user)){
            return R.fail("当前用户"+username+"不存在");
        }
        user.setPassword(BCrypt.hashpw(password));
        user.setUpdateTime(DateUtils.getNowDate());
        user.setUpdateBy(username);
        if (userMapper.updateById(user)>0){
            RedisUtils.deleteObject(key);
            return R.ok("修改成功");
        }
        return R.fail("忘记密码修改异常");
    }

    /**
     * 用户注册
     * @param username
     * @param password
     * @param code
     * @return
     */
    public R<?> userRegister(String username, String password, String code) {
        String key =CacheConstants.REGISTER_SEND_MSG+username;
        boolean matches = username.matches("^1(3\\d|4[5-9]|5[0-35-9]|6[567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$");
        if (!matches) {
            throw new ServiceException("手机号格式不正确");
        }
        if (!validateSmsCode(username, code,key)){
            throw new UserException("user.jcaptcha.error");
        }
        //判断当前账号是否存在
        List<User> userList  = userMapper.selectList(new LambdaQueryWrapper<User>()
            .eq(User::getLoginName, username));
        if (userList.size()>0){
            return R.fail("当前用户已存在");
        }
        User user1 = new User();
        user1.setPassword(BCrypt.hashpw(password));
        user1.setUserType("app_user");
        user1.setLoginName(username);
        user1.setJurisdiction(1L);
        if (userMapper.insert(user1)>0){
            //删除验证码
            RedisUtils.deleteObject(key);
            return R.ok("注册成功");
        }
        return R.fail("注册异常");
    }


    /**
     * 用户短信登录
     * @param username
     * @param code
     * @return
     */
    public String userSmsLogin(String username, String code) {
        // 通过手机号查找用户
        User user = loadUserByUsername(username);
        checkLogin(LoginType.SMS, user.getUserName(), () -> !validateSmsCode(username, code,CacheConstants.SMS_LOGIN_SEND_MSG));
        // 此处可根据登录用户的数据不同 自行创建 loginUser
        LoginUser loginUser = buildLoginUser(user);
        // 生成token
        LoginHelper.loginByDevice(loginUser, DeviceType.APP);

        recordLogininfor(user.getUserName(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
        return StpUtil.getTokenValue();

    }

    /**
     * 公开登录接口
     * @param username
     * @param apiKey
     * @return
     */
    public String userOpenLogin(String username, String apiKey) {
        //1.根据用户账号和key获取用户信息
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
            .eq(User::getLoginName, username)
            .eq(User::getApiKey,apiKey));
        //2.如果不存在就新增,反之构造用户信息并返回一个token
        if (ObjectUtil.isNull(user)){
            User user1 = new User();
            user1.setUserType("app_user");
            user1.setLoginName(username);
            user1.setApiKey(apiKey);
            user1.setCreateTime(new Date());
            user1.setUpdateTime(new Date());
            user1.setUpdateBy(username);
            user1.setCreateBy(username);
            user1.setWxToken(username);
            user1.setJurisdiction(1L);
            int insert = userMapper.insert(user1);
            if (insert==0){
                throw new ServiceException("用户操作失败");
            }
            LoginUser loginUser = buildLoginUser(user1);
            // 生成token
            LoginHelper.loginByDevice(loginUser, DeviceType.PC);
            recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
            String tokenValue = StpUtil.getTokenValue();
            return tokenValue;
        }
        if (user.getJurisdiction().equals(2L)) {
            log.info("登录用户：{} 已被停用.", username);
            throw new UserException("user.blocked", username);
        }
        LoginUser loginUser = buildLoginUser(user);
        // 生成token
        LoginHelper.loginByDevice(loginUser, DeviceType.PC);
        recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
        String tokenValue = StpUtil.getTokenValue();
        return tokenValue;
    }
}

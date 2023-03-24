package com.ruoyi.test;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.helper.DataBaseHelper;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.BuyHouses;
import com.ruoyi.system.domain.MaterialTalents;
import com.ruoyi.system.domain.vo.HousesReviewVo;
import com.ruoyi.system.domain.vo.MaterialTalentsVo;
import com.ruoyi.system.mapper.BuyHousesMapper;
import com.ruoyi.system.mapper.HousesReviewMapper;
import com.ruoyi.system.mapper.MaterialTalentsMapper;
import com.ruoyi.system.service.impl.SysConfigServiceImpl;
import com.ruoyi.work.domain.ActProcess;
import com.ruoyi.work.domain.HisProcess;
import com.ruoyi.work.domain.TProcess;
import com.ruoyi.work.domain.vo.ActProcessVo;
import com.ruoyi.work.domain.vo.ProcessVo;
import com.ruoyi.work.dto.BusinessDTO;
import com.ruoyi.work.mapper.ActProcessMapper;
import com.ruoyi.work.mapper.HisProcessMapper;
import com.ruoyi.work.mapper.ProcessMapper;
import com.ruoyi.work.service.impl.ProcessServiceImpl;
import com.ruoyi.work.utils.WorkComplyUtils;
import org.apache.commons.text.CaseUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 单元测试案例
 *
 * @author Lion Li
 */
@SpringBootTest // 此注解只能在 springboot 主包下使用 需包含 main 方法与 yml 配置文件
@DisplayName("单元测试案例")
public class DemoUnitTest {

    @Autowired
    private RuoYiConfig ruoYiConfig;

    @Autowired
    ProcessServiceImpl processService;

    @Autowired
    private BuyHousesMapper buyHousesMapper;

    @Autowired
    HisProcessMapper hisProcessMapper;

    @Autowired
    private ProcessMapper processMapper;

    @Autowired
    private ActProcessMapper actProcessMapper;

    @Autowired
    private HousesReviewMapper housesReviewMapper;

    @Autowired
    private MaterialTalentsMapper materialTalentsMapper;

    @Autowired
    private SysConfigServiceImpl sysConfigService;


    @DisplayName("测试 @SpringBootTest @Test @DisplayName 注解")
    @Test
    public void testTest() {
        System.out.println(ruoYiConfig);
    }

    @Disabled
    @DisplayName("测试 @Disabled 注解")
    @Test
    public void testDisabled() {
        System.out.println(ruoYiConfig);
    }

    @Timeout(value = 2L, unit = TimeUnit.SECONDS)
    @DisplayName("测试 @Timeout 注解")
    @Test
    public void testTimeout() throws InterruptedException {
        Thread.sleep(3000);
        System.out.println(ruoYiConfig);
    }


    @DisplayName("测试 @RepeatedTest 注解")
    @RepeatedTest(3)
    public void testRepeatedTest() {
        System.out.println(666);
    }

    @BeforeAll
    public static void testBeforeAll() {
        System.out.println("@BeforeAll ==================");
    }

    @BeforeEach
    public void testBeforeEach() {
        System.out.println("@BeforeEach ==================");
    }

    @AfterEach
    public void testAfterEach() {
        System.out.println("@AfterEach ==================");
    }

    @AfterAll
    public static void testAfterAll() {
        System.out.println("@AfterAll ==================");
    }

    @Test
    public void  test001() throws Exception {
        //方法名
        String methodName = "queryById";
        //类名
        Object beanName = SpringUtils.getBean("processServiceImpl");
        //值
        String value ="1";
        Class aClass = Long.valueOf(value).getClass();
        System.out.println("stringClass = " + aClass);
        Object params;
        //将数值转类型
        params=(Long.valueOf(value));
        //调用方法
        Method method = ReflectionUtils.findMethod(beanName.getClass(), methodName, aClass);
        Object obj = ReflectionUtils.invokeMethod(method, beanName,params);
        System.out.println("obj = " + obj.toString());
    }


    @Test
    public void test002(){
        ProcessVo processVo = new ProcessVo();
        processVo.setProcessKey("apply_house");
        processVo.setStep("1");
        BuyHouses buyHouses = buyHousesMapper.selectById("2");
        Map<String, Object> map = BeanUtil.beanToMap(buyHouses);
        processVo.setParams(map);
        processVo.setBusinessId("2");
        processVo.setStartUser(buyHouses.getUserName());
        WorkComplyUtils.comply(processVo);
    }

    @Test
    public void test003(){
        HisProcess hisProcess = new HisProcess();
        hisProcess.setStatus("2");
        BuyHouses buyHouses = buyHousesMapper.selectById("2");
        Map<String, Object> map = BeanUtil.beanToMap(buyHouses);
        hisProcess.setBusinessId(buyHouses.getId().toString());
        hisProcess.setParams(map);
        hisProcess.setProcessKey(buyHouses.getProcessKey());
        hisProcess.setStartUser(buyHouses.getUserName());
        String s = WorkComplyUtils.batchDeleted(hisProcess);
        System.out.println("s = " + s);
    }

    @Test
    public void  test004(){
        BusinessDTO businessDTO = new BusinessDTO();
        BuyHouses buyHouses = buyHousesMapper.selectById("2");
        Map<String, Object> map = BeanUtil.beanToMap(buyHouses);
        businessDTO.setParams(map);
        businessDTO.setBusinessId(buyHouses.getId().toString());
        WorkComplyUtils.getStep(businessDTO);
    }

    @Test
    public void test005(){
        String s = "1";
        LambdaQueryWrapper<TProcess> wrapper = new LambdaQueryWrapper<>();
        wrapper.apply(ObjectUtil.isNotEmpty(s),"FIND_IN_SET('"+s+"',cc)");
        List<TProcess> tProcesses = processMapper.selectList(wrapper);
        List<Long> collect = tProcesses.stream().map(TProcess::getId).collect(Collectors.toList());
        LambdaQueryWrapper<ActProcess> lqw = new LambdaQueryWrapper<>();
        lqw.in(ActProcess::getProcessId,collect);
        List<ActProcessVo> actProcessVoList = actProcessMapper.selectVoList(lqw);
        System.out.println("actProcessVoList = " + actProcessVoList);
        System.out.println("collect = " + collect);
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNum(1);
        pageQuery.setPageSize(10);
        HisProcess hisProcess = new HisProcess();
        hisProcess.setUserId("2");
        hisProcess.setDeptId("103");
        hisProcess.setRoleId("1");
        Page<HisProcess> hisProcessPage = hisProcessMapper.selectVoListPage(pageQuery.build(), hisProcess);
        List<HisProcess> records = hisProcessPage.getRecords();
        System.out.println("records = " + records);
        long total = hisProcessPage.getTotal();
        System.out.println("total = " + total);
        long size = hisProcessPage.getSize();
        System.out.println("size = " + size);
    }

    @Test
    public void  test006(){
        //先获取出顶级目录
        HousesReviewVo housesReviewVo = housesReviewMapper.selectVoById("1633346490522927106");
        Map<String, Object> map = BeanUtil.beanToMap(housesReviewVo);
        Set<String> keys = map.keySet();
        LambdaQueryWrapper<MaterialTalents> eq = new LambdaQueryWrapper<MaterialTalents>()
            .eq(MaterialTalents::getTalentsValue, map.get("processKey"));
        MaterialTalentsVo materialTalentsVo = materialTalentsMapper.selectVoOne(eq);
        //根据id获取关于他下面的所有子集
        LambdaQueryWrapper<MaterialTalents> wrapper = new LambdaQueryWrapper<MaterialTalents>()
            .apply(DataBaseHelper.findInSet(materialTalentsVo.getId(), "selected"));
        List<MaterialTalents> materialTalents = materialTalentsMapper.selectList(wrapper);
        ArrayList<String> list = new ArrayList<>();
        for (MaterialTalents materialTalent : materialTalents) {
            if (keys.contains(materialTalent.getTalentsValue())){
                //获取当前目录下的数据
                Object value = map.get(materialTalent.getTalentsValue());
                if (ObjectUtil.isNotNull(value)) {
                    List<String> collect = materialTalents.stream().filter(m -> m.getTalentsValue().equals(value) && materialTalent.getId().equals(m.getParentId())).map(MaterialTalents::getMaterials).collect(Collectors.toList());
                    System.out.println("collect = " + collect);
                    list.addAll(collect);
                }
            }
        }

        List<String> collect = list.stream().distinct().collect(Collectors.toList());
        System.out.println("collect2 = " + collect);

    }

    @Test
    public void test0007(){
      /*  String s1 = sysConfigService.selectConfigByKey("sys:preventing:hotlinking");
        System.out.println("s1 = " + s1);
        List<String> strings = Arrays.asList(s1.split(","));
        System.out.println("strings = " + strings);*/
        ActProcess actProcess = new ActProcess();
        actProcess.setProcessKey("apply_house");
        LambdaQueryWrapper<TProcess> wrapper = new LambdaQueryWrapper<TProcess>()
            .eq(TProcess::getProcessKey, actProcess.getProcessKey());
        List<TProcess> tProcesses = processMapper.selectList(wrapper);
        System.out.println("tProcesses = " + tProcesses);
    }

    @Test
    public void test0008(){
        String host= "https://xyxtest.easyfees.cn/fcc";
        String XYXMK ="54a321";
        String qybh="XYX";
        String account="XYX";
        String key="5ee4adfedae1458a9ca040f06b416755";
        Long timestamp=System.currentTimeMillis();
        System.out.println("timestamp = " + timestamp);
        String sign = SecureUtil.md5(qybh+"0001"+key+timestamp);
        System.out.println("sign = " + sign);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("qybh",qybh);
        hashMap.put("yhm","8401");
        hashMap.put("sign",sign);
        hashMap.put("timestamp",timestamp);

        String s = HttpUtil.get(host, hashMap);
        System.out.println("s = " + s);
    }

    @Test
    public void test0009(){
        String s ="buy_houses";
//        BuyHouses buyHouses = buyHousesMapper.selectById("2");
        String s1 = CaseUtils.toCamelCase(s,false,new char[]{'_'});
        System.out.println("s1 = " + s1);
        String s2 = s1+"ServiceImpl";
        //类名
        Object beanName = SpringUtils.getBean(s2);
        String me = "queryById";
        //调用方法
        Method method = ReflectionUtils.findMethod(beanName.getClass(), me, Long.class);
        Object obj = ReflectionUtils.invokeMethod(method, beanName,2L);
        System.out.println("obj = " + obj);
//        Map<String, Object> map = BeanUtil.beanToMap(buyHouses);
//        System.out.println("map = " + map);
//        ProcessVo processVo = new ProcessVo();
//        processVo.setParams(map);
//        processVo.setProcessKey("apply_house");
//        List<ProcessVo> processPlan = WorkComplyUtils.getProcessPlan(processVo);
//        System.out.println("processPlan = " +processPlan) ;
    }

    @Test
    public void test00000(){
        processMapper.updateCommonByBusinessId("buy_houses", Constants.FAILD,"2");
    }
}

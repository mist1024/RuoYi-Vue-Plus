package com.ruoyi.test;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SM4;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.antherd.smcrypto.sm2.Keypair;
import com.antherd.smcrypto.sm2.Sm2;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.GaoXinCardInfo;
import com.ruoyi.common.helper.DataBaseHelper;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.demo.domain.ImageDemoData;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.vo.HousesReviewVo;
import com.ruoyi.system.domain.vo.MaterialTalentsVo;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.IBuyHousesService;
import com.ruoyi.system.service.impl.BuyHousesServiceImpl;
import com.ruoyi.system.service.impl.MaterialModuleServiceImpl;
import com.ruoyi.system.service.impl.SysConfigServiceImpl;
import com.ruoyi.work.domain.ActProcess;
import com.ruoyi.work.domain.AuditLog;
import com.ruoyi.work.domain.HisProcess;
import com.ruoyi.work.domain.TProcess;
import com.ruoyi.work.domain.vo.ActProcessVo;
import com.ruoyi.work.domain.vo.ProcessVo;
import com.ruoyi.work.dto.HousingConstructionBureauPushDto;
import com.ruoyi.work.mapper.ActProcessMapper;
import com.ruoyi.work.mapper.AuditLogMapper;
import com.ruoyi.work.mapper.HisProcessMapper;
import com.ruoyi.work.mapper.ProcessMapper;
import com.ruoyi.work.service.impl.ProcessServiceImpl;
import com.ruoyi.work.utils.WorkComplyUtils;
import com.ruoyi.work.utils.WorkUtils;
import org.apache.commons.text.CaseUtils;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Autowired
    private MaterialModuleMapper materialModuleMapper;

    @Autowired
    private MaterialProofMapper materialProofMapper;

    @Autowired
    private  MaterialModuleServiceImpl materialModuleService;

    @Autowired
    private IBuyHousesService iBuyHousesService;

    @Autowired
    private BuyHousesMemberMapper buyHousesMemberMapper;

    @Autowired
    private HousingConstructionBureauPushDto housingConstructionBureauPushDto;

    @Autowired
    private AuditLogMapper auditLogMapper;
    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    /*@Autowired
    private Produceras providerCustomer;

    @Autowired
    private OrderService orderService;
    */

    @Autowired
    private BuyHousesServiceImpl buyHousesService;

//    @Autowired
//    private RabbitTemplate amqpTemplate;


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
        BuyHouses buyHouses = buyHousesMapper.selectById("3247");
        buyHouses.setUpdateTime(DateUtils.getNowDate());
        Map<String, Object> map = BeanUtil.beanToMap(buyHouses);
        processVo.setParams(map);
        processVo.setBusinessId(buyHouses.getId().toString());
        processVo.setStartUser(buyHouses.getUserName());
        processVo.setCardId(buyHouses.getCardId());
        processVo.setCompanyName(buyHouses.getCompanyName());
        WorkComplyUtils.comply(processVo);
    }



    @Test
    public void test003(){
        HisProcess hisProcess = new HisProcess();
        hisProcess.setStatus("2");
        BuyHouses buyHouses = buyHousesMapper.selectById("10");
        Map<String, Object> map = BeanUtil.beanToMap(buyHouses);
        hisProcess.setBusinessId(buyHouses.getId().toString());
        hisProcess.setParams(map);
        hisProcess.setProcessKey(buyHouses.getProcessKey());
        hisProcess.setStartUser(buyHouses.getUserName());
        Map map1 = WorkComplyUtils.batchDeleted(hisProcess, null);
        System.out.println("s = " + map1);
    }

    @Test
    public void  test004(){
        /*BusinessDTO businessDTO = new BusinessDTO();
//        BuyHouses buyHouses = buyHousesMapper.selectById("1011");
        HousesReview housesReview = housesReviewMapper.selectById("1633344911078064130");
        Map<String, Object> map = BeanUtil.beanToMap(housesReview);
        businessDTO.setParams(map);
        businessDTO.setBusinessId(housesReview.getId().toString());
        WorkComplyUtils.getStep(businessDTO);*/

        boolean validCard = IdcardUtil.isValidCard("51050319960221405X");
        System.out.println("validCard = " + validCard);
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
        String host= "https://xyxtest.easyfees.cn/fcopen/fcsso/xyx?sfzdzc=1&qybh=XYX&yhm=8401&sign=b9831864d15add760321d2a3791e2ea7&timestamp=1680154767652";
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

//    @Test
//    public void test00000(){
//        processMapper.updateCommonByBusinessId("buy_houses", Constants.FAILD,"2");
//    }

    @Test
    public void tests1245545(){
        List<MaterialModule> materialModules = materialModuleMapper.selectList();
        List<BuyHouses> buyHouses = buyHousesMapper.selectList();
        ArrayList<MaterialProof> list = new ArrayList<>();
        for (BuyHouses buyHouse : buyHouses) {
            Map<String, Object> map = BeanUtil.beanToMap(buyHouse);
            for (MaterialModule materialModule : materialModules) {
                if (ObjectUtil.isNotNull(map.get(materialModule.getMaterialKey()))){
                    MaterialProof materialProof = new MaterialProof();
                    materialProof.setHouseId(buyHouse.getId().toString());
                    materialProof.setModulePathId(materialModule.getId().toString());
                    materialProof.setFile( map.get(materialModule.getMaterialKey()).toString());
                    materialProof.setMaterialKey(materialModule.getMaterialKey());
                    materialProof.setAuditDept(materialModule.getAuditDept());
                    materialProof.setMaterialName(materialModule.getMaterialName());
                    materialProof.setDescription(materialModule.getDescription());
                    materialProof.setProcessKey("apply_house");
                    list.add(materialProof);
                    String o = map.get(materialModule.getMaterialKey()).toString();
                    System.out.println(buyHouse.getId()+":"+materialModule.getMaterialName() +":" + o);
                }

            }
        }
        materialProofMapper.insertBatch(list);
    }

    @Test
    public void tesst00000() throws Exception {

      /* String http = HttpRequest.get("https://gx.chengdutalent.cn:8010/candidates/getByCard")
            .header("Cookie","security.session.id=e1eee749-70da-48a5-8390-b99c7e1749e0")
            .execute()
            .body();*/
//        hashMap.put("loginName", AesUtil.encryptBASE64("18716148446"));
//        hashMap.put("password",AesUtil.encryptBASE64("1234Qwer"));

       /* BuyHouses buyHouses = buyHousesMapper.selectById(1011L);
        Map<String, Object> map = BeanUtil.beanToMap(buyHouses);
        List<MaterialModuleVo> materialInfo = materialModuleService.getMaterialInfo(map);
        System.out.println("materialInfo = " + materialInfo);*/
    }

    @Test
    public void ddd() throws IOException {

        URL url = new URL("https://gx.chengdutalent.cn:8010/upload/GXTalents/images/d69af77836264dd897b2fe3d55a9edd3.jpg");
//        URL url = new URL("https://gx.chengdutalent.cn:8010/upload/GXTalents/images/ac2144e52f5a44be8f21a3ff3cfdc3ab.jpg");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");//POST
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        int responseCode = conn.getResponseCode();
        /*InputStream inputStream = conn.getInputStream();
        byte[] temp = new byte[inputStream.available()];
        FileOutputStream fos = new FileOutputStream("D:\\");
        int len = 0;
        while ((len = inputStream.read(temp)) != -1) {
            fos.write(temp, 0, len);
        }
*/
        InputStream inputStream =conn.getInputStream();
        byte[] temp =new byte[inputStream.available()];
        if (temp.length==0) {
            ClassPathResource classPathResource = new ClassPathResource("/404.jpg");
            inputStream = classPathResource.getStream();
            temp = new byte[inputStream.available()];
        }
        FileOutputStream fos = new FileOutputStream("D:\\ddd.jpg");
        int len = 0;
        while ((len = inputStream.read(temp)) != -1) {
            fos.write(temp, 0, len);
        }
        inputStream.close();
        fos.close();
        inputStream.close();
        fos.close();
        System.out.println("responseCode = " + responseCode);
    }
    @Test
    public void test4454545() throws Exception {
        Keypair keypair = Sm2.generateKeyPairHex();
        String publicKey1 = keypair.getPublicKey();

        String privateKey = "9cefdfcb925a32e10206d3a693ba204632c59e5f9a171faebb814885191dd35e";
        System.out.println("privateKey = " + privateKey);
        String publicKey = "0435661bb2d13bba88f47af0bbe243fcded8f27ac298932661787f88ea283c2b31fe427e1aa8410826a963e9114fe5ffab4ad278aeeb7f1f161e735d1f50570e78";
        System.out.println("publicKey = " + publicKey);
        final String VUE_PUBLIC_KEY = "048af4184056315a9ecfcc14280b32504ba194ec8dd0e06b298c4a1aa557e7e9a5d15e1293392f741f7fb31a82e55785b7f1880d61b57def1e38e3f06435fb0502";
        String s2 = Sm2.doEncrypt("12312312", VUE_PUBLIC_KEY);
        String s3 = Sm2.doDecrypt("c368df0c094044c0648b412078c2067f84984531afebe3fd494fbce4f5e9b7bc42465855964def4f3bbdd1f61b8780f62656f63c25f8cc6c7e5caff9b0757909848577302a0a1a708d226ecc6a2b3e5c3ac6056cc74884cf5dbe80482f7edb7af59778528a12986bf671d9619c5d60ba00f44e4369aa48f5cf91ac60b9af8e144c730dd0e60330a3fc9473a80ce88fa2", privateKey);
        System.out.println("s3 = " + s3);
        System.out.println("s2 = " + s2);

        SecretKey sm4 = SecureUtil.generateKey("SM4");
//        byte[] encoded = sm4.getEncoded();
//        String encode = Base64.encode(encoded);
        String encode = "08H5sBeeEsvMrmLMvcetrQ==";
//        SM4 sm41 = SmUtil.sm4(Base64.decode(encode));
//        sm41.setMode(CipherMode.encrypt)
//        String s1 = sm41.encryptHex("12346");
//        System.out.println("s1 = " + s1);
//        System.out.println("encode = " + encode);
        /*SM4 sm41 = SmUtil.sm4(Base64.decode(encode));
        BuyHouses buyHouses = buyHousesMapper.selectById(820);
        List<BuyHousesMember> buyHousesMembers = buyHousesMemberMapper.selectList(new LambdaQueryWrapper<>(BuyHousesMember.class).eq(BuyHousesMember::getBuyHousesId, "41"));
        buyHouses.setBuyHousesMemberList(buyHousesMembers);
        String s = sm41.encryptHex(buyHouses.toString());
        System.out.println("s = " + s);*/

        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] publicEncoded = pair.getPublic().getEncoded();
        byte[] privateEncoded = pair.getPrivate().getEncoded();


//        String PUBLIC_KEY = Base64.encode(publicEncoded);
//        System.out.println("publicKeyBase64 = " + PUBLIC_KEY);

//        String PRIVATE_KEY = Base64.encode(privateEncoded);
//        System.out.println("privateKeyBase64 = " + PRIVATE_KEY);
//        SM2 sm2 = SmUtil.sm2(PRIVATE_KEY, VUE_PUBLIC_KEY);
//        sm2.setMode(SM2Engine.Mode.C1C3C2);

        //使用前端得公钥加密
//        String s = sm2.encryptBase64("123123", KeyType.PublicKey);
//        System.out.println("s = " + s);

        //解密使用后端得公钥加密,私钥来解密
//        String s1 = sm2.decryptStr(s, KeyType.PrivateKey);
//        System.out.println("s1 = " + s1);

//        String s = RSAUtil.privateKeyDecryptStr("04463afac00a7ef5e500bc26c74f1340f8685a1da9a1de5a87e8babb0cdfd2ef5c55091d679cf1665a3c13986b71951205fc0de60a8a2287aaeb15efac7b44130d56787ef5fbfb842cc40704dabfc7c0c73853586a31a72e8afe2f0537a1a794ce861c224b1b16844f0b5612ec9623d51d55baa36664c205660940947b3211d8929aa2e165badf4f72729e0349bfb8a326d0f68f4205769b1f043190b5c78fa200d6c9bfdf");
//        System.out.println("s = " + s);
//        String s3 = StrUtil.utf8Str(sm2.decryptStr("04b8f145be1be60714eabac0ed712f6a4647fb88dd22c08e17e47120dc166d9e7016f61d17a0bd126a8e552fbeef71ebe26c1324a549058d63058795019cfa2d8eba414560e457d5dacc25794671818a106d4d55bc4a349aad8486ee669dbe09c7c02d80b8f647ce402179cbcefba1e842cc2370f2e8d0dcbe5e3b6caa48664f62712643d64949b65bb38bdf0d2c7092c77293e12542797f37f3aa8035e796a1df79c26d91", KeyType.PrivateKey));
//        System.out.println("s3= " + s3);
       /*
        List<BuyHousesMember> buyHousesMembers = buyHousesMemberMapper.selectList(new LambdaQueryWrapper<>(BuyHousesMember.class).eq(BuyHousesMember::getBuyHousesId, "41"));
        buyHouses.setBuyHousesMemberList(buyHousesMembers);
        SM2 sm2 = SmUtil.sm2(PRIVATE_KEY, PUBLIC_KEY);
        String s = sm2.encryptBase64(buyHouses.toString(), KeyType.PublicKey);
        String s1 = StrUtil.utf8Str(sm2.decryptStr(s, KeyType.PrivateKey));
        System.out.println("s1 = " + s1);*/
//        RSA rsa = new RSA(PRIVATE_KEY,PUBLIC_KEY);
//        String s = rsa.encryptBase64(buyHouses.toString(), KeyType.PublicKey);
//        System.out.println("s = " + s);
//        String s1 = rsa.decryptStr(s, KeyType.PrivateKey);
//        System.out.println("s1 = " + s1);


        String privateKeyBase641 = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALiB/4PE2IBQkxDI6yughGTVlBAj3Q/PYVi3qwjWnlIDLblG/BvihtMZxRbwQgmpsuUvnyVuMMYtMsP1xZQglmSLfxWyY0ke43u7VwDe3NnR3o/JjHQtQPfxHjlO9ziJP1PnrIaQpWpQ8UoRIUZMDdWWbt31Br6DuyC32TB/NtunAgMBAAECgYBNHgiuCphzCTpuyYuBsJWlj59TH6pF8We+rQXPq+SAYtO5nPHCteukUCEQdVskrskXAdCC1IuOSVXukcsDHpu8tHIEqfUHpuMlBZEa3hCmDeDBYcW2+Z/9NnBIi8+G6me1FNfTWrZegth7EWwt/qxdwFOl0PsNbqBJxVHyJxTIAQJBAOkW3PwrguUm2wujR7EkxCSTC8KnuKLngeOX9L1vupMrMhUjuupoRzfZYD+k3m14mUfS7jqrWKW4E9flg0/5IG0CQQDKpLJ4EjoAg7Gm0oXliWRYS/ijiZXdRmgWEj1taptTjKkIPtmIPcxutdbidwcrftbOJb+JAMvCEhMaT0UsyKfjAkEAkFAXgglugXINLKdrO8IHrp1cKqitKC8tvDvYy3DhkzyrRWtZzsfBUFLFxKHPFPgV7uIpnSl5OSE/J+xx4JHeAQJBAKtRXgig8CRrMg/lP4n1A76aS9SGhwqRcYHnXcNZM4QJEQaFjAbgqCqY1NiU5JzjGNsjkrBS2fBys2+0wLjB0x0CQQC88VLSYuh0+vrXOgyDR/hViEDePTdeubb6xggvxC8yazETXxHsougeLzeUQgKGl4nNLrQb0pv2tV7jLsFFJOkZ";
        String publicKeyBase641 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4gf+DxNiAUJMQyOsroIRk1ZQQI90Pz2FYt6sI1p5SAy25Rvwb4obTGcUW8EIJqbLlL58lbjDGLTLD9cWUIJZki38VsmNJHuN7u1cA3tzZ0d6PyYx0LUD38R45Tvc4iT9T56yGkKVqUPFKESFGTA3Vlm7d9Qa+g7sgt9kwfzbbpwIDAQAB";
        /**/
//        String text = "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";

//        RSA rsa = new RSA(privateKeyBase641,publicKeyBase641);
//        String s = rsa.encryptBase64(buyHouses.toString(), KeyType.PublicKey);
//        String s1 = rsa.decryptStr(text, KeyType.PrivateKey);
//        System.out.println("s1 = " + s1);

//        String publicKeyBase64 = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEQDNGdBB8hITt279hmBeCzZCP/CpszHhhW6IFPp5OGWkDV7w+l5HGv34IS9Asci7d1bft4ivMrEdJOmznkAO20Q==";
//        String privateKeyBase64 = "MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgLhQzxGWzCwx5HCWgMu5dF3jCrgWR81TKeTBH1u7LqA2gCgYIKoZIzj0DAQehRANCAARAM0Z0EHyEhO3bv2GYF4LNkI/8KmzMeGFbogU+nk4ZaQNXvD6Xkca/fghL0CxyLt3Vt+3iK8ysR0k6bOeQA7bR";
//        final ECIES ecies = new ECIES(privateKeyBase64,publicKeyBase64);

//        String encryptStr = ecies.encryptBase64(buyHouses.toString(), KeyType.PublicKey);
//        String decryptStr = StrUtil.utf8Str(ecies.decrypt(encryptStr, KeyType.PrivateKey));
//        System.out.println("decryptStr = " + decryptStr);
    }

    @Test
    public void test021231(){
        String publicKey="0435661bb2d13bba88f47af0bbe243fcded8f27ac298932661787f88ea283c2b31fe427e1aa8410826a963e9114fe5ffab4ad278aeeb7f1f161e735d1f50570e78";
        String privateKey="9cefdfcb925a32e10206d3a693ba204632c59e5f9a171faebb814885191dd35e";

        BuyHouses buyHouses = buyHousesMapper.selectById("41");
        Map<String, Object> map = BeanUtil.beanToMap(buyHouses);
        String virtualcode = String.valueOf(map.get("virtualcode"));
        map.put("virtualcode", virtualcode == "3" ? "010" : "009");
        String cardType = String.valueOf(map.get("cardType"));
        map.put("cardType", "中国籍".equals(cardType) ? 1 : 4);
        map.put("qyStatus", "4");
        map.put("gyStatus", "4");
        map.put("shStatus", "4");
        map.put("buyHousesMemberList", "null");
        map.put("buyHousesLogList", "null");
        map.put("buyHousesLogList", "null");
        String prettyStr = JSONUtil.toJsonPrettyStr(map);
        System.out.println("prettyStr = " + prettyStr);
        List<BuyHousesMember> buyHousesMembers = buyHousesMemberMapper.selectList(new LambdaQueryWrapper<>(BuyHousesMember.class).eq(BuyHousesMember::getBuyHousesId, buyHouses.getId()));
        buyHouses.setBuyHousesMemberList(buyHousesMembers);
        String s = JSONUtil.toJsonPrettyStr(buyHouses);

        String doEncrypt = Sm2.doEncrypt(s, publicKey);
//        System.out.println("doEncrypt = " + doEncrypt);
        String doDecrypt = Sm2.doDecrypt(doEncrypt, privateKey);
//        System.out.println("doDecrypt = " + doDecrypt);
        String result2 = HttpRequest.post("http://192.168.0.54:8084/user/house/insertOpenBuyHouses")
            .header("Referer","http://192.168.0.54:8084")
            .header("path","/user/house/insertOpenBuyHouses")
            .header("method","POST")
            .body(doEncrypt,"application/json")
            .execute().body();
        System.out.println("result2 = " + result2);
    }

    //String privateKey="9cefdfcb925a32e10206d3a693ba204632c59e5f9a171faebb814885191dd35e";
    @Test
    public void test0212311123123(){
        String publicKey="0435661bb2d13bba88f47af0bbe243fcded8f27ac298932661787f88ea283c2b31fe427e1aa8410826a963e9114fe5ffab4ad278aeeb7f1f161e735d1f50570e78";
        HashMap<String, Object> map = new HashMap<>();
        map.put("username","o6Jx05A8ze3Icr6KC59n8I0DBp14");
        map.put("apiKey","gaoxingongyuanchengshiju");
        String s = JSONUtil.toJsonPrettyStr(map);
        String doEncrypt = Sm2.doEncrypt(s, publicKey);
        String result2 = HttpRequest.post("https://rcaj.cdhtgycs.cn/gx-api/userOpenLogin")
            .header("Referer","https://rcaj.cdhtgycs.cn")
            .header("path","/userOpenLogin")
            .header("targe","weixin")
            .header("Authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpblR5cGUiOiJsb2dpbiIsImxvZ2luSWQiOiJhcHBfdXNlcjo0Mzc4OSIsInJuU3RyIjoiV05XZFFnaHpHYlRRRU5ObVpwbmhaaFp6MmtKaWtSR0MiLCJ1c2VySWQiOjQzNzg5fQ.QCsN3iIFk38dzTvXITGx2wV5c9kdPdvWZs7gjf6dFzc")
            .header("method","POST")
            .body(doEncrypt,"application/json")
            .execute().body();
        System.out.println("result2 = " + result2);
    }

    /**
     * 查看日志接口
     */
    @Test
    public void test0002(){
        String publicKey="0435661bb2d13bba88f47af0bbe243fcded8f27ac298932661787f88ea283c2b31fe427e1aa8410826a963e9114fe5ffab4ad278aeeb7f1f161e735d1f50570e78";
//        String publicKey="04eea960591fff39b445b289faf56f755a1c895de57dfa3ca6ab2c84d2b5429cad1444809d941e4a0ed168bd226d0bf28a016389f26ea8506039da5db6a93a79f6";
        HashMap<String, Object> map = new HashMap<>();
        map.put("businessId","2819");//用户标识符
        map.put("apiKey","gaoxingongyuanchengshiju");//使用方唯一key
        String s = JSONUtil.toJsonPrettyStr(map);
        String doEncrypt = Sm2.doEncrypt(s, publicKey);
//        String result2 = HttpRequest.post("https://dbxqtalents.cn/gx-api/user/stepProcessPlan")
        String result2 = HttpRequest.post("http://127.0.0.1:8084/user/stepProcessPlan")
            .header("Referer","https://dbxqtalents.cn/gx-api")
            .header("path","/user/stepProcessPlan")
            .header("targe","weixin")
            .header("Authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpblR5cGUiOiJsb2dpbiIsImxvZ2luSWQiOiJzeXNfdXNlcjoxIiwicm5TdHIiOiJUNjZNSjBIckwzcjF1YkFsQlFTem4yNTdjdDhTc2l2cyIsInVzZXJJZCI6MX0.qbEbSQ7ZElegj_1-BKs3xzDfBuZHBItQzJkNaMv0Lrs")
            .header("method","POST")
            .body(doEncrypt,"application/json")
            .execute().body();
        System.out.println("result2 = " + result2);
    }

    /**
     * 新增或者修改接口
     */
    @Test
    public void test0003(){
        String publicKey="0435661bb2d13bba88f47af0bbe243fcded8f27ac298932661787f88ea283c2b31fe427e1aa8410826a963e9114fe5ffab4ad278aeeb7f1f161e735d1f50570e78";
        HashMap<String, Object> map = new HashMap<>();
        map.put("username","15808234569");
        map.put("apiKey","gaoxingongyuanchengshiju");
        String s = JSONUtil.toJsonPrettyStr(map);
        String doEncrypt = Sm2.doEncrypt(s, publicKey);
//        String result2 = HttpRequest.post("https://dbxqtalents.cn/gx-api/userOpenLogin")
//        String result2 = HttpRequest.post("http://192.168.0.54:8084/userOpenLogin")
        String result2 = HttpRequest.post("http://ljlcom.gnway.cc/userOpenLogin")
            .header("Referer","https://dbxqtalents.cn/gx-api")
            .header("path","/userOpenLogin")
            .header("targe","weixin")
            .header("Authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpblR5cGUiOiJsb2dpbiIsImxvZ2luSWQiOiJhcHBfdXNlcjo0Mzc4OSIsInJuU3RyIjoiV05XZFFnaHpHYlRRRU5ObVpwbmhaaFp6MmtKaWtSR0MiLCJ1c2VySWQiOjQzNzg5fQ.QCsN3iIFk38dzTvXITGx2wV5c9kdPdvWZs7gjf6dFzc")
            .header("method","POST")
            .body(doEncrypt,"application/json")
            .execute().body();
        System.out.println("result2 = " + result2);
    }

    /**
     * 图片导出
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ImageDemoData}
     * <p>
     * 2. 直接写即可
     */
    @Test
    public void imageWrite() throws Exception {
        String fileName = "E:\\"+ System.currentTimeMillis() + ".xlsx";

        // 这里注意下 所有的图片都会放到内存 暂时没有很好的解法，大量图片的情况下建议 2选1:
        // 1. 将图片上传到oss 或者其他存储网站: https://www.aliyun.com/product/oss ，然后直接放链接
        // 2. 使用: https://github.com/coobird/thumbnailator 或者其他工具压缩图片
        String imagePath = "https://gx.chengdutalent.cn:8010/upload/GXTalents/images/f264ec41dcfb47b9b6ef231441d3123b.png";
        try  {
            List<ImageDemoData> list =  ListUtils.newArrayList();
            ImageDemoData imageDemoData = new ImageDemoData();
            list.add(imageDemoData);
            // 放入五种类型的图片 实际使用只要选一种即可
//            imageDemoData.setByteArray(FileUtils.readFileToByteArray(new File(imagePath)));
//            imageDemoData.setFile(new File(imagePath));
//            imageDemoData.setString(imagePath);
//            imageDemoData.setInputStream(inputStream);
            imageDemoData.setUrl(new URL(
                "https://gx.chengdutalent.cn:8010/upload/GXTalents/images/f264ec41dcfb47b9b6ef231441d3123b.png"));

            // 这里演示
            // 需要额外放入文字
            // 而且需要放入2个图片
            // 第一个图片靠左
            // 第二个靠右 而且要额外的占用他后面的单元格
            /*WriteCellData<Void> writeCellData = new WriteCellData<>();
            imageDemoData.setWriteCellDataFile(writeCellData);
            // 这里可以设置为 EMPTY 则代表不需要其他数据了
            writeCellData.setType(CellDataTypeEnum.STRING);
            writeCellData.setStringValue("额外的放一些文字");*/

            /*// 可以放入多个图片
            List<ImageData> imageDataList = new ArrayList<>();
            ImageData imageData = new ImageData();
            imageDataList.add(imageData);
            writeCellData.setImageDataList(imageDataList);
            // 放入2进制图片
            imageData.setImage(FileUtils.readFileToByteArray(new File(imagePath)));
            // 图片类型
            imageData.setImageType(ImageData.ImageType.PICTURE_TYPE_PNG);
            // 上 右 下 左 需要留空
            // 这个类似于 css 的 margin
            // 这里实测 不能设置太大 超过单元格原始大小后 打开会提示修复。暂时未找到很好的解法。
            imageData.setTop(5);
            imageData.setRight(40);
            imageData.setBottom(5);
            imageData.setLeft(5);

            // 放入第二个图片
            imageData = new ImageData();
            imageDataList.add(imageData);
            writeCellData.setImageDataList(imageDataList);
            imageData.setImage(FileUtils.readFileToByteArray(new File(imagePath)));
            imageData.setImageType(ImageData.ImageType.PICTURE_TYPE_PNG);
            imageData.setTop(5);
            imageData.setRight(5);
            imageData.setBottom(5);
            imageData.setLeft(50);
            // 设置图片的位置 假设 现在目标 是 覆盖 当前单元格 和当前单元格右边的单元格
            // 起点相对于当前单元格为0 当然可以不写
            imageData.setRelativeFirstRowIndex(0);
            imageData.setRelativeFirstColumnIndex(0);
            imageData.setRelativeLastRowIndex(0);
            // 前面3个可以不写  下面这个需要写 也就是 结尾 需要相对当前单元格 往右移动一格
            // 也就是说 这个图片会覆盖当前单元格和 后面的那一格
            imageData.setRelativeLastColumnIndex(1);*/

            // 写入数据
            EasyExcel.write(fileName, ImageDemoData.class).sheet().doWrite(list);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test0121123(){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id","3212");
        hashMap.put("status","00D");
        hashMap.put("description","测试");
        hashMap.put("auditDepartName","数字经济局功能区建设推进处");
        housingConstructionBureauPushDto.send3(hashMap,"https://www.cdhtrct.com/route/open/api/anju/openBuyHousesCallback");
    }

    @Test
    public void test15234523(){
//        providerCustomer.sendDelayMsg("延迟队列测试",3);
        String msg ="我来了";
       /* MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("x-delay",5000);//延迟5秒被删除
        Message message = new Message(msg.getBytes(), messageProperties);
        amqpTemplate.convertAndSend("PLUGIN_DELAY_EXCHANGE","delay","123132");//交换机和路由键必须和配置文件类中保持一致
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("消息发送成功【" + sdf.format(new Date()) + "】");*/
//        orderService.makeOrder();
    }

    @Test
    public void  testt12333(){
        double div = NumberUtil.div(28, 3000);
        System.out.println("div = " + div);
        String s = NumberUtil.decimalFormat("#.##%", div);
        System.out.println("s = " + s);
        //获取二期认定通过人才数
        /*List<HousesReview> housesReviewList = housesReviewMapper.selectList(new LambdaQueryWrapper<>(HousesReview.class)
            .eq(HousesReview::getProcessStatus, Constants.SUCCEED));
        //获取本月复审通过数
        Date date = DateUtil.date();
        //获得月份，从0开始计数
        int month = DateUtil.month(date);
        housesReviewList.forEach(h ->{
            int month1 = h.getPassTime().getMonth();
            System.out.println("month1 = " + month1);
            String s = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, h.getPassTime());
            System.out.println("s = " + s);
        });*/
//        List<HousesReview> collect = housesReviewList.stream().filter(h -> h.getPassTime().getMonth() == month).collect(Collectors.toList());
//        System.out.println("collect = " + collect);
    }

    /**
     * 获取公钥和私钥
     */
    @Test
    public void test0004(){
        /*Keypair keypair = Sm2.generateKeyPairHex();
        String publicKey = keypair.getPublicKey();
        System.out.println("publicKey = " + publicKey);

        String privateKey = keypair.getPrivateKey();
        System.out.println("privateKey = " + privateKey);*/
//        String substring = DesensitizedUtil.idCardNum("510503199602214055", 4, 0).substring(0, 9);
//        System.out.println("card = " + substring);

        String str ="https://gx.chengdutalent.cn:8010/upload/GXTalents/images/a6b742bc7843405da406b0159179dcfd.jpg";
        String substring = str.substring(str.lastIndexOf("/") + 1);

        String txt ="https://rcaj.cdhtgycs.cn/images/2023/07/01/"+substring;
        buyHousesService.excelZip("");
        System.out.println("txt = " + txt);

    }

    @Test
    public void TestMessageAck() throws UnsupportedEncodingException {

        String msg="A 类";
        String trim = StringUtils.trimAllWhitespace(msg);
        int length = trim.length();
        System.out.println("length = " + length);
//        Message build = MessageBuilder.withBody(msg.getBytes()).build();
//        build.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
//        build.getMessageProperties().setDelay(50000);
//        amqpTemplate.convertAndSend(TalentsDelayRabbitConfig.TALENTS_PLUGIN_DELAY_EXCHANGE,TalentsDelayRabbitConfig.TALENTS_PLUGIN_DELAY_KEY,build);
    }

    @Test
    public void  testPush() throws ParseException {
        Map<String, Object> map = WorkUtils.getInfoToMap("buy_houses","3218");
        String virtualcode = String.valueOf(map.get("virtualcode"));
        map.put("virtualcode", virtualcode == "3" ? "010" : "009");
        String cardType = String.valueOf(map.get("nationality"));
        map.put("cardType", "中国籍".equals(cardType) ? 1 : 4);
        Object createTime = map.get("createTime");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        DateFormat cst = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date format = sdf.parse(createTime.toString());
        String dateString = cst.format(format);
        map.put("qyStatus", "4");
        map.put("creatTime",dateString);
        map.put("gyStatus", "4");
        map.put("shStatus", "4");
        map.put("buyHousesMemberList", "null");
        map.put("buyHousesLogList", "null");
        String s = housingConstructionBureauPushDto.openUrl("https://171.221.172.13:8088/CCSRegistryCenter/rest", map, "253");
    }
    @Test
    public void testOut(){
        BuyHouses buyHouses = buyHousesMapper.selectById(3218);
        Map<String, Object> map = new HashMap<>();
        map.put("id",buyHouses.getId());
        map.put("reason","人才主动提交");//原因
        map.put("userName",buyHouses.getUserName());
        map.put("cardId",buyHouses.getCardId());
        map.put("cancelTime", DateUtils.dateTime("yyyy-MM-dd HH:mm:ss"));
        map.put("note","人才主动撤销");//备注
        map.put("status", "00N");
        System.out.println("JSONUtil.toJsonPrettyStr(map) = " + JSONUtil.toJsonPrettyStr(map));
        housingConstructionBureauPushDto.openUrl("https://jcfw.cdzjryb.com/CCSRegistryCenter/rest",map,"254");//正式
    }

    @Test
    public void  paChon() throws Exception {
        Set<String> set = new HashSet<>();
        set.add("1");
        set.add("2");
        set.add("1");
        String collect = set.stream().collect(Collectors.joining(","));
        System.out.println("collect = " + set);
//        R rInfo = OpenUtils.getGaoXinCardInfo("510503199602214055");
//        System.out.println("rInfo = " + rInfo);
//        String s = com.ruoyi.common.utils.StringUtils.toUpperCase("510503199602214055l");
//        System.out.println("s = " + s);
//        buyHousesService.logout("3184");

//        R gaoXinCardInfo = OpenUtils.getGaoXinCardInfo("510503199602214055");
//        System.out.println("JSONUtil.toJsonPrettyStr(gaoXinCardInfo) = " + JSONUtil.toJsonPrettyStr(gaoXinCardInfo));
       /* String appsecret ="JXYB7KpXlH9i0CL6";
        String aesKey = "74242EAFE97F18BFAE9D2682590B6614";
        String iv = "74242EAFE97F18BF";
        String id="2023062804031";
        String url ="http://162.14.100.54:9010/index.php/Api/renju/get_user_info";
        AES aes = new AES(Mode.CBC, Padding.PKCS5Padding,aesKey.getBytes(),iv.getBytes());
        String param=aes.encryptBase64(
            new JSONObject()
            .set("id_card","510503199602214056")
            .toStringPretty());
        MD5 md5 = SecureUtil.md5();
        String content=id+param+appsecret;
        String sign=md5.digestHex(content);
        JSONObject jsonObject = new JSONObject()
            .set("id",id)
            .set("param",param)
            .set("sign",sign);
        String listContent = HttpRequest.post(url)
            .body(jsonObject.toStringPretty())
            .execute()
            .body();
        System.out.println("listContent = " + listContent);*/

    }

    @Test
    public void test45656() {
        int chushi = 0 ;
        int num =5;
        String dateStr = "2023-08-13";
//        7天为一个节点
        //7
//        星期天是1
        //查询数据库中大于当前时间的所有节假日时间

        DateTime nowDate = DateUtil.parse(dateStr);//开始时间
        int i1 = DateTime.of(nowDate).dayOfWeek();
        if (i1==1 || i1==7){

        }


        System.out.println("i1 = " + i1);
    }

    @Test
    public void  testtttt() throws IOException {

        List<AuditLog> auditLogList = auditLogMapper.selectList(new LambdaQueryWrapper<>(AuditLog.class)
            .isNotNull(AuditLog::getAuditId)
            .isNull(AuditLog::getStep)
            .orderByAsc(AuditLog::getOtherId));
        List<HisProcess> list = new ArrayList<>();
        auditLogList.forEach(a ->{
            HisProcess hisProcess = new HisProcess();
            BuyHouses buyHouses = buyHousesMapper.selectById(a.getOtherId());
            if (ObjectUtil.isNotNull(buyHouses)) {
                hisProcess.setStartUser(buyHouses.getUserName());
                hisProcess.setCompanyName(buyHouses.getCompanyName());
                hisProcess.setUserId(ObjectUtil.isNull(buyHouses.getUserId()) ? null : buyHouses.getUserId().toString());
                hisProcess.setCardId(buyHouses.getCardId());
                if (a.getStatus().equals("5") || a.getStatus().equals("3") || a.getStatus().equals("4")) {
                    hisProcess.setStep("1");
                    hisProcess.setProcessId("2");
                    hisProcess.setType("1");
                    hisProcess.setIsNext(true);
                    hisProcess.setDescription("受理");
                } else if (a.getStatus().equals("8") || a.getStatus().equals("6") || a.getStatus().equals("7")) {
                    hisProcess.setStep("2");
                    hisProcess.setProcessId("3");
                    hisProcess.setType("2");
                    hisProcess.setIsNext(true);
                    hisProcess.setDescription("初审");
                } else if (a.getStatus().equals("10") || a.getStatus().equals("11") || a.getStatus().equals("12") || a.getStatus().equals("9")) {
                    hisProcess.setStep("3");
                    hisProcess.setProcessId("4");
                    hisProcess.setType("1");
                    hisProcess.setIsNext(false);
                    hisProcess.setDescription("审定");
                }
                if ("5,8,10".indexOf(a.getStatus()) >= 0) {
                    hisProcess.setStatus("2");
                } else {
                    hisProcess.setStatus("1");
                }
                if (a.getAuditId().equals("17")) {
                    hisProcess.setAudit("123");
                } else if (a.getAuditId().equals("18")) {
                    hisProcess.setAudit("122");
                } else if (a.getAuditId().equals("19")) {
                    hisProcess.setAudit("124");
                } else if (a.getAuditId().equals("20")) {
                    hisProcess.setAudit("126");
                } else if (a.getAuditId().equals("22")) {
                    hisProcess.setAudit("115");
                } else if (a.getAuditId().equals("39")) {
                    hisProcess.setAudit("125");
                } else if (a.getAuditId().equals("47")) {
                    hisProcess.setAudit("124");
                }
                hisProcess.setBusinessId(a.getOtherId());
                hisProcess.setCreateTime(a.getCreateTime());
                hisProcess.setEndTime(a.getCreateTime());
                hisProcess.setCheckType("2");
                list.add(hisProcess);
            }
        });
        hisProcessMapper.insertBatch(list);
    }

    @Test
    public void test4564(){
        String str ="{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"status\":\"2\",\"list\":[{\"step\":\"受理\",\"processVoList\":[{\"id\":null,\"name\":\"安居资格认定\",\"processKey\":null,\"step\":\"1\",\"type\":null,\"checkType\":\"部门\",\"processCheck\":null,\"cc\":null,\"isNext\":null,\"audit\":\"生物城政务中心\",\"param\":null,\"paramName\":null,\"params\":null,\"ruleId\":null,\"businessId\":null,\"startUser\":null,\"timeout\":null,\"checked\":\"2\",\"audit1\":null,\"bean\":null,\"description\":\"受理\",\"auditLogList\":[{\"createBy\":null,\"createTime\":\"2023-06-28 11:01:27\",\"updateBy\":null,\"updateTime\":null,\"id\":null,\"auditId\":null,\"adminUserName\":\"生物城管委会\",\"otherId\":null,\"reply\":\"1.请完善劳动合同内容；2.人才卡影像资料未显示生物城。\",\"status\":\"受理退件\",\"roleName\":null,\"pushLog\":null,\"auditType\":null,\"processKey\":null,\"audit\":null,\"step\":null},{\"createBy\":null,\"createTime\":\"2023-07-06 09:39:17\",\"updateBy\":null,\"updateTime\":null,\"id\":null,\"auditId\":null,\"adminUserName\":\"生物城管委会\",\"otherId\":null,\"reply\":\"\",\"status\":\"受理成功\",\"roleName\":null,\"pushLog\":null,\"auditType\":null,\"processKey\":null,\"audit\":null,\"step\":null},{\"createBy\":null,\"createTime\":\"2023-08-25 12:12:11\",\"updateBy\":null,\"updateTime\":\"2023-08-25 12:12:11\",\"id\":null,\"auditId\":null,\"adminUserName\":\"胡城菓\",\"otherId\":null,\"reply\":\"申请表公司地址更改为生物城地址，家属信息看不到\",\"status\":\"受理失败\",\"roleName\":null,\"pushLog\":null,\"auditType\":null,\"processKey\":null,\"audit\":null,\"step\":null}],\"auditLogList1\":null,\"size\":null,\"actProcessVoList\":null,\"createTime\":\"2023-02-03 11:50:56\",\"companyName\":null,\"cardId\":null,\"userId\":null,\"projectName\":null}]},{\"step\":\"初审\",\"processVoList\":[{\"id\":null,\"name\":\"安居资格认定\",\"processKey\":null,\"step\":\"2\",\"type\":null,\"checkType\":\"部门\",\"processCheck\":null,\"cc\":null,\"isNext\":null,\"audit\":\"生物城政务中心\",\"param\":null,\"paramName\":null,\"params\":null,\"ruleId\":null,\"businessId\":null,\"startUser\":null,\"timeout\":null,\"checked\":\"\",\"audit1\":null,\"bean\":null,\"description\":\"初审\",\"auditLogList\":[{\"createBy\":null,\"createTime\":\"2023-07-06 09:44:19\",\"updateBy\":null,\"updateTime\":null,\"id\":null,\"auditId\":null,\"adminUserName\":\"生物城管委会\",\"otherId\":null,\"reply\":\"\",\"status\":\"初审成功\",\"roleName\":n{\"code\":200,\"msg\":\"操作成功\",\"data\":{\"id\":3173,\"userName\":\"何定帮\",\"insidepageUrl\":\"https://www.cdhtrct.com/route/rctxcx/form/view/rct/M00/00/25/rCJnO2SY8cOAKoo7AAHDhebmb8I327.jpg\",\"cardId\":\"510922199508251854\",\"commitmentUrl\":\"https://www.cdhtrct.com/route/rctxcx/form/view/rct/M00/00/25/rCJnKmSam7CAJHN4AAK1XK96LHQ456.pdf\",\"companyAddress\":\"四川省成都市高新区科园南路5号蓉药大厦1栋4层附2、3号\",\"companyName\":\"海创药业股份有限公司\",\"declarationUrl\":\"https://www.cdhtrct.com/route/rctxcx/form/view/rct/M00/00/25/rCJnKmSZNtSAO43dAAAl-PUSMDI702.pdf\",\"district\":\"4\",\"education\":\"大学本科\",\"frontUrl\":\"https://www.cdhtrct.com/route/rctxcx/form/view/rct/M00/00/25/rCJnKmSY8QaAJ3OQAAhn8KQTwlg241.jpg\",\"gyStatus\":\"1\",\"homeRecordUrl\":\"https://www.cdhtrct.com/route/rctxcx/form/view/rct/M00/00/2B/rCJnO2TkKcuAH57NAAKHnFyAaLM984.jpg\",\"homepageUrl\":\"https://www.cdhtrct.com/route/rctxcx/form/view/rct/M00/00/25/rCJnO2SY8b-ALT1BAAFrUp-tOsg974.jpg\",\"laborContractUrl\":\"https://www.cdhtrct.com/route/rctxcx/form/view/rct/M00/00/27/rCJnKmSjywGAH2vxAB1X-f89j5M978.pdf\",\"licenseUrl\":\"https://www.cdhtrct.com/route/rctxcx/form/view/rct/M00/00/24/rCJnKmSKxvGANt4KAAVHUULAcE8677.jpg\",\"maritalStatus\":\"1\",\"maritalUrl\":\"https://www.cdhtrct.com/route/rctxcx/form/view/rct/M00/00/24/rCJnKmSKx4iAFSNYAAGNOx-Cjbg491.jpg\",\"nationality\":\"中国籍\",\"phone\":\"18709852102\",\"qyStatus\":\"1\",\"reverseUrl\":\"https://www.cdhtrct.com/route/rctxcx/form/view/rct/M00/00/25/rCJnKmSY8RuADtHpAAnHPcnZGn0050.jpg\",\"sex\":\"男\",\"shStatus\":\"1\",\"socialCode\":\"915101000624182263\",\"socialSecurityUrl\":\"https://www.cdhtrct.com/route/rctxcx/form/view/rct/M00/00/25/rCJnKmSZNqCAGcqOAAcNe7XjdDI305.jpg\",\"status\":\"2\",\"type\":\"D类\",\"userId\":4551,\"createTime\":\"2023-06-27 16:18:38\",\"updateTime\":\"2023-08-22 11:22:05\",\"passTime\":\"2023-06-27 16:18:38\",\"pictureInformationUrl\":\"https://www.cdhtrct.com/route/rctxcx/form/view/rct/M00/00/27/rCJnO2Sjyw-AUJ36AAGiuPnuxzU221.jpg\",\"workAddress\":\"高新南区\",\"processKey\":\"apply_house\",\"processStatus\":\"1\",\"buyHousesMemberList\":[{\"createBy\":\"o6Jx05JVTFmBeXEKGWQ3JSJS5ox8\",\"creat";
        SysOperLog sysOperLog = new SysOperLog();
        sysOperLog.setOperParam(str);
        sysOperLogMapper.insert(sysOperLog);
    }
}


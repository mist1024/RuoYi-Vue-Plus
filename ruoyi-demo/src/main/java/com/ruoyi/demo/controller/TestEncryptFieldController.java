package com.ruoyi.demo.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.demo.domain.TestDemo;
import com.ruoyi.demo.mapper.TestDemoMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试数据库字段加解密
 *
 * @author 老马
 * @date 2023-01-06 14:48
 */
@SaIgnore
@RestController
@RequestMapping("/demo/encrypt")
public class TestEncryptFieldController {

    @Resource
    private TestDemoMapper demoMapper;

    /**
     * 配置内容
     * encrypt-field:
         algorithm: base64
     */
    @GetMapping("/base64")
    public R<TestDemo> base64() {
        //插入
        TestDemo testDemo = new TestDemo();
        testDemo.setTestKey("testkey");
        testDemo.setValue("testvalue");
        demoMapper.insert(testDemo);
        //查询
        TestDemo testDemo1 = this.demoMapper.selectById(testDemo.getId());
        return R.ok(testDemo1);
    }

    /**
     * 配置内容
     * encrypt-field:
         algorithm: aes
         encodeKey: A84DC2F738843E1A4170F053F5025F9A74164FFC158CB245570728CF00731803
         ivKey: 00000000000000000000000000000000
         encodeType: hex
     */
    @GetMapping("/aes")
    public R<TestDemo> aes() {
        //插入
        TestDemo testDemo = new TestDemo();
        testDemo.setTestKey("testkey");
        testDemo.setValue("testvalue");
        demoMapper.insert(testDemo);
        //查询
        TestDemo testDemo1 = this.demoMapper.selectById(testDemo.getId());
        return R.ok(testDemo1);
    }


    /**
     * 配置内容
     * encrypt-field:
         algorithm: rsa
         publicKey: MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDt41GIcWvY6/tpjOLoWY78Oy00uWcNbMRG/8DRoS79/h2D+pV8uxV+0ezaN+fBFCZnK8TdJcPeU4EnNRUh/8HEY33KFvZ700n+Gj5BHUUDKzx3UVFNuF49UI/yoJ8rz6VQQHO79KK89VwmSMO77Tfee1ofe0STY6IwMt/MwaoKwIDAQAB
         privateKey: MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIO3jUYhxa9jr+2mM4uhZjvw7LTS5Zw1sxEb/wNGhLv3+HYP6lXy7FX7R7No358EUJmcrxN0lw95TgSc1FSH/wcRjfcoW9nvTSf4aPkEdRQMrPHdRUU24Xj1Qj/KgnyvPpVBAc7v0orz1XCZIw7vtN957Wh97RJNjojAy38zBqgrAgMBAAECgYAkXx4qvI6rFNryw88+Am6JpMioUghHb2ioE9QCYomqohnA+DocS71JLN8qwo3lijp7gJGzzKEeC8Aoc+oKAZfBQQwPP0K/ql+NcLdlqNAr+XyzkZrLJD/BfluQ6mXI23tjonWnPPZ1Et9HC3zXRQPiuh7Ff6UoqatTMI3OIbSyuQJBAPbaJpVYmIsWJdpvaAC6hcGgR/vbG9yX8VphPn8/2wsm2MmQrRkUoKkudE/veX3KQjGikDwo9+bT6aBl+nn2DUUCQQCImSGZFNcGqhMLyRGVogP6fh0YZSEQAhFbZ69nQmplqbie+dREhODH5Vs5F0C2aL5iSR/UaIDkNEA1auX7x56vAkAFWjON927PTTqi4tmBconl6eDFsDmJbe34xLUDM1I/iqcWr8FhEtZs9Knm9c1PkewfgWPZOhYt9hhRtwRYUqJ1AkAPvQ0I9US9KNVe80DKa8tnjiZODEDd9k8HqA+mpxlZM0/pSUGyz1iSz5NOJaa4HaNp8aDwOUY4hOis/u8Wrm5TAkEA00YeUsaXlMyMF/5pjols44tXb54AjAC2mH66pz9JsKg7pKpWVOpEV5rMY58CGZHWau69vGLZnCd1coeMw77YAQ==
         encodeType: base64
     */
    @GetMapping("/rsa")
    public R<TestDemo> rsa() {
        //插入
        TestDemo testDemo = new TestDemo();
        testDemo.setTestKey("testkey");
        testDemo.setValue("testvalue");
        demoMapper.insert(testDemo);
        //查询
        TestDemo testDemo1 = this.demoMapper.selectById(testDemo.getId());
        return R.ok(testDemo1);
    }


    /**
     * 配置内容
     * encrypt-field:
         algorithm: sm2
         publicKey: MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAESU5V56NTde1KYcp63wHEXxUyh2kwEVgVNhlQiqUQMbJJWyIDny7CW2lt71XGmm8sjmSkbGZYMcrM27H2wzp0Gw==
         privateKey: MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgM6U/o1oR8swRomlNffyqE2tglhuUcWtPEFYrCsvGUa2gCgYIKoEcz1UBgi2hRANCAARJTlXno1N17UphynrfAcRfFTKHaTARWBU2GVCKpRAxsklbIgOfLsJbaW3vVcaabyyOZKRsZlgxyszbsfbDOnQb
         encodeType: base64
     */
    @GetMapping("/sm2")
    public R<TestDemo> sm2() {
        //插入
        TestDemo testDemo = new TestDemo();
        testDemo.setTestKey("testkey");
        testDemo.setValue("testvalue");
        demoMapper.insert(testDemo);
        //查询
        TestDemo testDemo1 = this.demoMapper.selectById(testDemo.getId());
        return R.ok(testDemo1);
    }

    /**
     * 配置内容
     * encrypt-field:
         algorithm: sm4
         encodeKey: 8qwo86o2psmakww2qe2mv0ivk5gru2op
         encodeType: base64
     */
    @GetMapping("/sm4")
    public R<TestDemo> sm4() {
        //插入
        TestDemo testDemo = new TestDemo();
        testDemo.setTestKey("testkey");
        testDemo.setValue("testvalue");
        demoMapper.insert(testDemo);
        //查询
        TestDemo testDemo1 = this.demoMapper.selectById(testDemo.getId());
        return R.ok(testDemo1);
    }

}

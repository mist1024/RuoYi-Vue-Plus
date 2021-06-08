package com.ruoyi.demo.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisLockManager;
import com.ruoyi.demo.vo.TestTreeVo;
import org.redisson.api.RLock;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

/**
 * redis 分布式锁测试
 */
@RestController
@RequestMapping("/demo/redisLock")
public class TestRedisLockController {

	 @Resource
	 private  RedisLockManager redisLockManager;

	/**
	 * 测试锁
	 * @param key
	 * @param time
	 * @return
	 */
	@GetMapping("/getLock")
	 public AjaxResult<Void>  testLock(String key,Long time){
		 try {
			 RLock rLock =  redisLockManager.getLock(key,time, TimeUnit.SECONDS);
			 System.out.println(LocalTime.now().toString() +"获取到锁："+key);
			 Thread.sleep(3000);
			 redisLockManager.unLock(rLock);
		 } catch (InterruptedException e) {
			 System.out.println(e.getMessage());
		 }

		 return AjaxResult.success();
	 }
}

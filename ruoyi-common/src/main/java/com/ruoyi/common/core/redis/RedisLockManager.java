package com.ruoyi.common.core.redis;

import cn.hutool.core.lang.Assert;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisLockManager {

	@Resource
	private  RedissonClient redissonClient;
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 获取锁（不用设置超时时间，一直等待）
	 * @param key
	 * @return
	 */
	public RLock getLock(String key){
		Assert.isTrue(StringUtils.isNotEmpty(key));
		RLock lock = redissonClient.getLock(key);
		lock.tryLock();
		return lock;
	}

	/**
	 *  设置过期时间
	 * @param key
	 * @param time 过期时间
	 * @param expireUnit 时间单位
	 * @return
	 * @throws InterruptedException
	 */
	public RLock getLock(String key,Long time, TimeUnit expireUnit) throws InterruptedException {
		RLock lock = redissonClient.getLock(key);
        lock.tryLock(time,expireUnit);
		return lock;
	}

	/**
	 * 设置过期时间
	 * @param key
	 * @param waitTime 获取锁等待时间
	 * @param leaseTime 保留锁的时间
	 * @param unit 时间单位
	 * @return
	 * @throws InterruptedException
	 */
	public RLock getLock(String key,long waitTime, long leaseTime, TimeUnit unit)throws InterruptedException{
		RLock lock = redissonClient.getLock(key);
		lock.tryLock(waitTime,leaseTime,unit);
		return lock;
	}

	/**
	 *
	 * @param key ： 锁的key值 不能为空
	 * @param count ：countDownLatch 的数量 必须大于等于0
	 * @return
	 */
	public RCountDownLatch countdownLatch(String key,long count){
	    Assert.isTrue(StringUtils.isNotEmpty(key));
		Assert.isTrue(count>=0);
		RCountDownLatch rCountDownLatch =  redissonClient.getCountDownLatch(key);
		rCountDownLatch.trySetCount(count);
		return rCountDownLatch ;
	}

	/**
	 * 获取公平锁
	 * @param key
	 * @param waitTime 获取锁等待时间
	 * @param leaseTime 持有锁的时间
	 * @param unit
	 * @return
	 * @throws InterruptedException
	 */
	public RLock getFairLock(String key,long waitTime, long leaseTime, TimeUnit unit)throws InterruptedException{
		RLock lock = redissonClient.getFairLock(key);
		lock.tryLock(waitTime,leaseTime,unit);
		return lock;
	}

	/**
	 * 获取公平锁
	 * @param key
	 * @param leaseTime 持有锁的时间
	 * @param unit
	 * @return
	 * @throws InterruptedException
	 */
	public RLock getFairLock(String key, long leaseTime, TimeUnit unit)throws InterruptedException{
		RLock lock = redissonClient.getFairLock(key);
		lock.tryLock(leaseTime,unit);
		return lock;
	}

	/**
	 * 释放锁(统一释放)
	 * @param lock
	 */
	public void unLock(RLock lock){
		lock.unlock();
	}
}

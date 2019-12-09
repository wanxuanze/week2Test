package com.bawei.cms.test;

import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bawei.cms.bean.User;
import com.bawei.cms.utils.StringUtils;

@SuppressWarnings("all")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring-context.xml")
public class UserTest {

	//注入
	@Resource
	RedisTemplate redisTemplate;
	
	//随机性别
	public static String getRandomSex() {
		return new Random().nextBoolean()?"男":"女";
	}
	
	//随机手机号码
	public static String getPhone() {
		String str="";
		for(int i=0;i<9;i++) {
			str += new Random().nextInt(9);
		}
		String phone = "13"+str;
		return phone;
	}
	
	//jdk序列化方式存入redis
	@Test
	public void testUserByJdk() {
		
		//开始时间
		long beginTime = System.currentTimeMillis();
		
		for(int i=1;i<=50000;i++) {
			
			User user = new User();
			user.setId(i);
			user.setName(StringUtils.getRandomChar(3));
			user.setSex(getRandomSex());
			user.setPhone(getPhone());
			user.setEmail(StringUtils.getEmail());
			user.setBirthday(StringUtils.randomDate("1949-01-01 00:00:00", "2001-01-01 00:00:00"));
			
			ValueOperations opsForValue = redisTemplate.opsForValue();
			opsForValue.set(i+"", user);
			
		}
		
		//结束时间
		long endTime = System.currentTimeMillis();
		//耗时
		long time = endTime-beginTime;
		System.out.println("jdk序列化方式 耗时"+time+"毫秒");
	}
	
	
	//使用json类型存入redis
	@Test
	public void testUserByJson() {
		
		long beginTime = System.currentTimeMillis();
		
		for(int i=1;i<=50000;i++) {
			
			User user = new User();
			user.setId(i);
			user.setName(StringUtils.getRandomChar(3));
			user.setSex(getRandomSex());
			user.setPhone(getPhone());
			user.setEmail(StringUtils.getEmail());
			user.setBirthday(StringUtils.randomDate("1949-01-01 00:00:00", "2001-01-01 00:00:00"));
			
			ValueOperations opsForValue = redisTemplate.opsForValue();
			opsForValue.set(i+"", user);
			
		}
		
		long endTime = System.currentTimeMillis();
		long time = endTime-beginTime;
		System.out.println("json序列化方式 耗时"+time+"毫秒");
	}
	
	//使用hash类型存入redis
	@Test
	public void testUserByHash() {
		
		long beginTime = System.currentTimeMillis();
		
		for(int i=1;i<=50000;i++) {
			
			User user = new User();
			user.setId(i);
			user.setName(StringUtils.getRandomChar(3));
			user.setSex(getRandomSex());
			user.setPhone(getPhone());
			user.setEmail(StringUtils.getEmail());
			user.setBirthday(StringUtils.randomDate("1949-01-01 00:00:00", "2001-01-01 00:00:00"));
			
			BoundHashOperations boundHashOps = redisTemplate.boundHashOps("user_hash");
			boundHashOps.put(1+"", user.toString());
			
		}
		
		long endTime = System.currentTimeMillis();
		long time = endTime-beginTime;
		System.out.println("hash序列化方式 耗时"+time+"毫秒");
	}
	
}

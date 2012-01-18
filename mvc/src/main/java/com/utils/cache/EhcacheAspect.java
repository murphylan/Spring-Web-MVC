package com.utils.cache;

import java.io.Serializable;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class EhcacheAspect {

	@Pointcut("@annotation(Ehcache)")
	public void simplePointcut() {
	}

	@AfterReturning(pointcut = "simplePointcut()")
	public void simpleAdvice() {
		System.out.println("AOP后处理成功 ");
	}

	@Around("simplePointcut()")
	public Object aroundLogCalls(ProceedingJoinPoint joinPoint)
			throws Throwable {
		String targetName = joinPoint.getTarget().getClass().toString();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		// 得到标注的Ehcache类
		Ehcache flag = joinPoint.getTarget().getClass().getMethod(methodName)
				.getAnnotation(Ehcache.class);
		String cacheName = flag.cacheName();
		Cache cache = CacheManager.getInstance().getCache(cacheName);
		if (cache == null) {
			cache = CacheManager.getInstance().getCache("testCache");
		}
		Object result;
		String cacheKey = getCacheKey(targetName, methodName, arguments);
		Element element = cache.get(cacheKey);
		// 判断缓存是否为空
		if (element != null) {
			cache.remove(cacheKey);
		}
		if ((arguments != null) && (arguments.length != 0)) {
			result = joinPoint.proceed(arguments);
		} else {
			result = joinPoint.proceed();
		}
		// 判断是增加缓存还是删除缓存，默认是增加缓存
		if (flag.addOrdel()) {
			element = new Element(cacheKey, (Serializable) result);
			cache.put(element);
			return element.getValue();
		}
		return result;
	}

	/**
	 * 获得cache key的方法，cache key是Cache中一个Element的唯一标识 cache key包括
	 * 包名+类名+方法名，如com.co.cache.service.UserServiceImpl.getAllUser
	 */
	private String getCacheKey(String targetName, String methodName,
			Object[] arguments) {
		StringBuffer sb = new StringBuffer();
		sb.append(targetName).append(".").append(methodName);
		if ((arguments != null) && (arguments.length != 0)) {
			for (int i = 0; i < arguments.length; i++) {
				sb.append(".").append(arguments[i]);
			}
		}
		return sb.toString();
	}
}

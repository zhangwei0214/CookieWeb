package me.ben.web.online;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 通过session的数量统计在线人数
 * 如果是集群环境， onlineNumber需要存在共享缓存中(e.g. memcached),修改这个实现类即可
 * @author Administrator
 *
 */
public class OnlineCounter {
	
	private static AtomicInteger onlineNumber = new AtomicInteger(0);
	
	public static int addOne(){
		return onlineNumber.incrementAndGet();
	}
	public static int removeOne(){
		return onlineNumber.decrementAndGet();
	}
	public static int getOnlineNumber(){
		return onlineNumber.get();
	}
	
}

package com.vic.redis.distributedlock.test;

import com.vic.redis.distributedlock.RedisTool;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date: 2018/8/29 09:36
 * @Description:
 */
public class TestAblility {
    private static JedisPoolConfig config = new JedisPoolConfig();
    static {
        //设置最大连接总数
        config.setMaxTotal(1);
        //设置最大空闲数
        config.setMaxIdle(1);
        //设置最小空闲数
        config.setMinIdle(1);
        config.setMaxWaitMillis(10000);
        //在获取连接的时候检查有效性, 默认false
        config.setTestOnBorrow(true);
        //在空闲时检查有效性, 默认false
        config.setTestOnReturn(true);
        //是否启用pool的jmx管理功能, 默认true
        config.setJmxEnabled(true);
        //Idle时进行连接扫描
        config.setTestWhileIdle(true);
        //是否启用后进先出, 默认true
        config.setLifo(true);
        //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        config.setTimeBetweenEvictionRunsMillis(30000);
        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        config.setNumTestsPerEvictionRun(10);
        //表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
        config.setMinEvictableIdleTimeMillis(60000);
        //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        config.setBlockWhenExhausted(true);
        //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
        config.setSoftMinEvictableIdleTimeMillis(1800000);
    }
    public static void main(String[] args) {
        Set<String> sentinels = new HashSet<String>();
        String hostAndPort1 = "132.232.45.67:26379";
        String hostAndPort2 = "132.232.45.67:36379";
        String hostAndPort3 = "132.232.45.67:46379";
        sentinels.add(hostAndPort1);
        sentinels.add(hostAndPort2);
        sentinels.add(hostAndPort3);
        String clusterName = "host6379" ;
        JedisSentinelPool redisSentinelJedisPool = null;
        long start = System.currentTimeMillis();
        for(int i=0;i<400;i++) {
            redisSentinelJedisPool = new JedisSentinelPool(clusterName, sentinels,config);
//            ClientThead ct = new ClientThead(redisSentinelJedisPool,String.valueOf(i),String.valueOf(i));
            Thread ct = new Thread(new ClientThead(redisSentinelJedisPool,String.valueOf(i),String.valueOf(i)));
            ct.setName("这是客户端"+i);
            ct.start();
        }
        System.out.println("花费的时间："+ String.valueOf(System.currentTimeMillis()-start));
//        redisSentinelJedisPool.close();
    }

}
//模拟多个客户端
class ClientThead implements Runnable{
    JedisSentinelPool redisSentinelJedisPool = null;
    String lockKey;
    String requestId;
    public ClientThead(JedisSentinelPool redisSentinelJedisPool,String lockKey, String requestId){
        this.redisSentinelJedisPool = redisSentinelJedisPool;
        this.lockKey = lockKey;
        this.requestId = requestId;
    }
    public void run() {
        Jedis jedis = null;
        try {
            jedis = redisSentinelJedisPool.getResource();
            long start = System.currentTimeMillis();
            if(RedisTool.tryGetDistributedLock(jedis,String.valueOf(lockKey),String.valueOf(requestId),120000)){
                System.out.println("+++++++++++++ requestId: "+requestId+"获取到锁： "+lockKey+ "获取锁所花费的时间："+String.valueOf(System.currentTimeMillis()-start));
            }else{
                System.out.println("************* requestId: "+requestId+"获取到锁失败： "+lockKey+ "获取锁所花费的时间："+String.valueOf(System.currentTimeMillis()-start));
            }
            Thread.sleep(3000);
//            RedisTool.releaseDistributedLock(jedis,lockKey,requestId);
            System.out.println("------------- requestId: "+requestId+"释放锁： "+lockKey);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }
}

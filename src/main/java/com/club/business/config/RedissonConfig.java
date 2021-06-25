//package com.club.business.config;
//
//import com.club.business.util.redisson.LockUtil;
//import com.club.business.util.redisson.RedissonLocker;
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//
///**
// * Redisson配置类
// *
// * @author Tom
// * @date 2019-11-15
// */
//@Configuration
//public class RedissonConfig {
//
//    @Value("${spring.redis.host}")
//    private String host;
//    @Value("${spring.redis.port}")
//    private String port;
//
//    /**
//     * RedissonClient,单机模式
//     * @return
//     * @throws IOException
//     */
//    @Bean(destroyMethod = "shutdown")
//    public RedissonClient redisson() throws IOException {
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://" + host + ":" + port).setPassword("123456");
//        return Redisson.create(config);
//    }
//
//    @Bean
//    public RedissonLocker redissonLocker(RedissonClient redissonClient){
//        RedissonLocker locker = new RedissonLocker(redissonClient);
//        /**设置LockUtil的锁处理对象*/
//        LockUtil.setLocker(locker);
//        return locker;
//    }
//}

package com.club.business;

import com.club.business.contants.CodeTypeEnum;
import com.club.business.util.RedisCodeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class BusinessApplicationTests {

//    @Autowired
//    private RedisCodeUtils redisCodeUtils;
//
//    /**
//     * RedisCodeUtils自动生成编码测试类
//     *
//     * @throws Exception
//     */
//    @Test
//    void contextLoads() throws Exception{
//        long startMillis = System.currentTimeMillis();
//        String orderIdPrefix = redisCodeUtils.getOrderIdPrefix(new Date());
//        String aLong = redisCodeUtils.getCode(CodeTypeEnum.USER.getKey());
//        System.out.println(aLong);
//        long endMillis = System.currentTimeMillis();
//        System.out.println("生成速度:"+(endMillis-startMillis)+",单位毫秒");
//    }

}

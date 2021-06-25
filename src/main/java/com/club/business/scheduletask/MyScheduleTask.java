package com.club.business.scheduletask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.time.LocalDateTime;

/**
 * 定时任务配置类
 *
 * @Configuration 主要用于标记配置类,兼备Component的效果
 * @EnableScheduling 开启定时任务
 * @author Tom
 * @date 2019-12-18
 */
@Configuration
@EnableScheduling
public class MyScheduleTask {

    private static final Logger log = LoggerFactory.getLogger(MyScheduleTask.class);

    /**
     * 添加定时任务(指定定时任务执行时间间隔,例如：15秒)
     * 或使用fixedRate @Scheduled(fixedRate=5000)也表示每5秒执行一次
     */
//    @Scheduled(cron = "0/15 * * * * ?")
    private void configureTasks() {
        /**
         * 此处可添加执行任务中的业务逻辑
         */
        log.info("执行静态定时任务时间: " + LocalDateTime.now());
    }
}

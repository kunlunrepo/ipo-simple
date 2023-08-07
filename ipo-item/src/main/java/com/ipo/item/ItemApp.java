package com.ipo.item;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * description : 接口性能优化
 *
 * @author kunlunrepo
 * date :  2023-07-21 10:17
 */
@SpringBootApplication
@Slf4j
@MapperScan("com.ipo.item.dao")
public class ItemApp {
    public static void main(String[] args) {
        log.info("【ipo-item】启动中。。。");
        SpringApplication.run(ItemApp.class, args);
        log.info("【ipo-item】启动成功");
    }
}

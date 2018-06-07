package com.mcloud.storageweb.redisTest;

import com.mcloud.storageweb.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Author: vellerzheng
 * @Description:
 * @Date:Created in 15:43 2018/6/5
 * @Modify By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    RedisUtil rs;

    @Test
    public void RedisTest() throws Exception {
        rs.set("hello", "11");
        System.out.println(rs.get("hello"));
        rs.remove("hello");

        rs.addZSet("kraft",123.32,"djfa");
         rs.incrementZSetScore("kraft","djfa",123.32);
         Double num = rs.getZSetScore("kraft","djfa");

            System.out.println(num);
        rs.removeZSet("kraft");
    }
}

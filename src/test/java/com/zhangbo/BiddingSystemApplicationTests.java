package com.zhangbo;

import com.zhangbo.mapper.MenuMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BiddingSystemApplicationTests {
    @Autowired
    private MenuMapper menuMapper;
    @Test
    void text() {
      List<String> list= menuMapper.selectPermsByUserId("1");
        System.out.println(list);
    }

}

package com.zhangbo;

import com.zhangbo.mapper.MenuMapper;
import com.zhangbo.until.HumpUntil;
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
        HumpUntil humpUntil=new HumpUntil();
        System.out.println(humpUntil.hump_underline("purchaseName"));
    }

}

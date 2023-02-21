package com.zhangbo.until;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Random;

public class CodeKey {
    @Autowired
    private  RedisCache redisCache;

    public static String getCode() {
        //随机生成一个大写字母
        char c = (char) (new Random().nextInt(26) + 'A');
        //随机生成一个数字
        char c2 = (char) (new Random().nextInt(10) + '0');
        //随机生成一个小写字母
        char c1 = (char) (new Random().nextInt(26) + 'a');
        //随机生成一个数字
        char c3 = (char) (new Random().nextInt(10) + '0');
        //随机生成一个大写字母
        char c4 = (char) (new Random().nextInt(26) + 'A');
        //拼接起来
        String code = c + "" + c2 + "" + c1 + "" + c3 + "" + c4;
        return code;
    }
}

package com.zhangbo.until;

import com.google.common.base.CaseFormat;

public class HumpUntil {
    // 下划线转驼峰
    public String underline_hump (String s1){
      return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, s1);
    }
    // 驼峰转下划线
    public String hump_underline (String s1){
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, s1);
    }
}

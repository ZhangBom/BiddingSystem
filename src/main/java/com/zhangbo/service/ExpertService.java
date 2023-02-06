package com.zhangbo.service;

import com.zhangbo.pojo.TabExpert;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;

public interface ExpertService {
    Result findAll(PageQuery pageQuery);

    Result expert_update(TabExpert expert);
}

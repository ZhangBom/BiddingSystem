package com.zhangbo.service;

import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;

public interface ContactService {
    Result findAll(PageQuery pageQuery);
}

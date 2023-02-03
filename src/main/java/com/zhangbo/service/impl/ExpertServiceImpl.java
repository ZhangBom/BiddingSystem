package com.zhangbo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbo.mapper.ExpertMapper;
import com.zhangbo.pojo.TabExpert;
import com.zhangbo.service.ExpertService;
import org.springframework.stereotype.Service;

@Service
public class ExpertServiceImpl extends ServiceImpl<ExpertMapper, TabExpert> implements ExpertService {
}

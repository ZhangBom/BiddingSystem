package com.zhangbo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbo.mapper.OptMapper;
import com.zhangbo.mapper.StructureMapper;
import com.zhangbo.pojo.TabOpt;
import com.zhangbo.pojo.TabPurchase;
import com.zhangbo.pojo.TabStructure;
import com.zhangbo.service.StructureService;
import com.zhangbo.until.Result;
import com.zhangbo.until.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StructureServiceImpl extends ServiceImpl<StructureMapper, TabStructure> implements StructureService {
    @Autowired
    private StructureMapper structureMapper;
    @Autowired
    private OptMapper optMapper;

    @Override
    public Result findAll(String tabName) {
        QueryWrapper<TabStructure> wrapper = new QueryWrapper<>();
        wrapper.eq("tab_name", tabName);
        List<TabStructure> list=structureMapper.selectList(wrapper);
        return Result.resultFactory(Status.STATUS,list);
    }

    @Override
    public Result findAllopt() {
        List<TabOpt> list=optMapper.selectList(null);
        return Result.resultFactory(Status.STATUS,list);
    }
}

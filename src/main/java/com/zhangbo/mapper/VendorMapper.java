package com.zhangbo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangbo.pojo.TabVendor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface VendorMapper extends BaseMapper<TabVendor> {

}

package com.zhangbo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangbo.pojo.TableVendor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VendorMapper extends BaseMapper<TableVendor> {
}

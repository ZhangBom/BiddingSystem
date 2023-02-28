package com.zhangbo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangbo.pojo.TabApplication;
import com.zhangbo.pojo.TabArticle;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplicationMapper extends BaseMapper<TabApplication> {
}

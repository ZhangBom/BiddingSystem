package com.zhangbo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangbo.pojo.Menu;

import java.util.List;


public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(String id);
}

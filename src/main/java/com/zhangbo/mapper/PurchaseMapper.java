package com.zhangbo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangbo.pojo.TabPurchase;
import com.zhangbo.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PurchaseMapper extends BaseMapper<TabPurchase> {
}

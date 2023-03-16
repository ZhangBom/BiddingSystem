package com.zhangbo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbo.mapper.AdminMapper;
import com.zhangbo.pojo.TabAdmin;
import com.zhangbo.service.AdminService;
import com.zhangbo.until.GetUser;
import com.zhangbo.until.Result;
import com.zhangbo.until.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, TabAdmin> implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Override
    public Result getInfo() {
        QueryWrapper<TabAdmin> wrapper=new QueryWrapper<>();
        wrapper.eq("admin_id",GetUser.getuserid());
        TabAdmin admin=adminMapper.selectOne(wrapper);
        return Result.resultFactory(Status.STATUS,admin);
    }

    @Override
    public Result register(TabAdmin admin) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passHash = encoder.encode(admin.getAdminName()+"123");
        admin.setAdminPass(passHash);
        save(admin);
        return Result.resultFactory(Status.OPERATION_SUCCESS);
    }
}

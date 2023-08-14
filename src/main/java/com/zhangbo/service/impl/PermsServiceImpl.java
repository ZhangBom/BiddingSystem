package com.zhangbo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbo.mapper.MenuMapper;
import com.zhangbo.mapper.RecordMapper;
import com.zhangbo.mapper.RoleMapper;
import com.zhangbo.pojo.Menu;
import com.zhangbo.pojo.TabRecord;
import com.zhangbo.pojo.TabRole;
import com.zhangbo.service.PermsService;
import com.zhangbo.until.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;


@Service
public class PermsServiceImpl extends ServiceImpl<MenuMapper, Menu> implements PermsService {
    @Autowired
    private MenuMapper mapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RecordMapper recordMapper;
    DateDiff dateDiff = new DateDiff();

    @Override
    public Result role_findAll(PageQuery pageQuery) {
        BackPage<TabRole> backPage = new BackPage<>();
        Page<TabRole> postPage = new Page<>(pageQuery.getCurrentPage(), pageQuery.getLimit());
        // page(postPage, wrapper)这里的第一个参数就是上面定义了的Page对象(分页信息)，第二个参数就是上面定义的条件构造器对象，通过调用这个方法就可以根据你的分页信息以及查询信息获取分页数据
        IPage<TabRole> postIPage = roleMapper.selectPage(postPage, null);
        //封装返回格式
        backPage.setContentList(postIPage.getRecords());
        backPage.setCurrentPage(postIPage.getCurrent());
        backPage.setTotalPage(postIPage.getPages());
        backPage.setTotalNum(postIPage.getTotal());
        return Result.resultFactory(Status.SUCCESS, backPage);
    }

    @Override
    public Result role_perms(String role_id) {
        List<Menu> list = mapper.selectPermsByRoleId(role_id);
        return Result.resultFactory(Status.SUCCESS, list);
    }

    @Override
    public Result updateRole(TabRole role) {
//        for (Object i:role.getMenus()) {
//            mapper.delete_role_menu(role.getId(), (Integer) i);
//        }
        //查询角色与菜单关联表是否有记录，有则删除，重新插入权限信息
        String role_id = String.valueOf(role.getId());
//
//        if (role_id == null) {
//            //循环menus存入表中
//            for (Object menu_id : role.getMenus()) {
//                mapper.insert_role_menu(role.getId(), (Integer) menu_id);
//            }
//        } else {
            //删除表中该role_id权限，重新插入权限信息
            mapper.delete_role_menu(role_id);
            for (Object menu_id : role.getMenus()) {
                mapper.insert_role_menu(role.getId(), (Integer) menu_id);
            }
//        }
        TabRecord record=new TabRecord();
        record.setRecordType("权限修改");
        record.setRecordOperator(GetUser.getuser().getUserContactName());
        record.setRecordId("role:"+role_id);
        record.setRecordUpdateTime(dateDiff.getNow());
        recordMapper.insert(record);
        return Result.resultFactory(Status.OPERATION_SUCCESS);
    }

    @Override
    public Result getMenu() {
        QueryWrapper<Menu> wrapper=new QueryWrapper<>();
        List<Menu> menus = mapper.selectList(null);
        return Result.resultFactory(Status.SUCCESS, menus);
    }

    @Override
    public Result addRole(TabRole role) {
        QueryWrapper<TabRole> wrapper=new QueryWrapper<>();
        wrapper.eq("role_key",role.getRoleKey());
        if(Objects.isNull(roleMapper.selectOne(wrapper))){
            mapper.add_role(role.getName(), role.getRoleKey());
            return Result.resultFactory(Status.INSERT_INFO_SUCCESS);
        }else{
            return Result.resultFactory(Status.INSERT_INFO_FAIL_ERROR);
        }
    }
}

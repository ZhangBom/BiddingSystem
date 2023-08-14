package com.zhangbo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangbo.pojo.Menu;

import java.util.List;


public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(String id);
    List<Menu> selectPermsByRoleId(String role_id);

    String selectRoleByUserId(String user_id);
    List<String> selectroelPermsByRoleId(String role_id);
    void delete_role_menu(String role_id);

    void add_role(String name,String role_key);
//查找关联表是否有角色权限信息
    String select_role_menu(long role_id);

    void insert_role_menu(long role_id, Integer menu_id);
}

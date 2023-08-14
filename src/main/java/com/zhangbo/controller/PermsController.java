package com.zhangbo.controller;

import com.zhangbo.pojo.Menu;
import com.zhangbo.pojo.TabRole;
import com.zhangbo.service.PermsService;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("/perms")
public class PermsController {
    @Autowired
    private  PermsService service;
    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping ("role_findAll")
    public Result role_findAll(@RequestBody PageQuery pageQuery){
       return service.role_findAll(pageQuery);
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("role_perms")
    public Result role_perms(@RequestParam("role_id")String role_id){
        return service.role_perms(role_id);
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping("updateRole")
    public Result updateRole(@RequestBody TabRole role){
        return service.updateRole(role);
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("getMenu")
    public Result getMenu(){
        return service.getMenu();
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("addRole")
    public Result addRole(@RequestBody TabRole role){
       return service.addRole(role);
    }
}

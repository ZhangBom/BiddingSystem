package com.zhangbo.controller;

import com.zhangbo.pojo.TabAdmin;
import com.zhangbo.service.AdminService;
import com.zhangbo.until.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @PreAuthorize("hasAnyAuthority('admin','purchase_manager')")

    @GetMapping("info")
    public Result getInfo(){
        return adminService.getInfo();
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping("register")
    public Result register(@RequestBody TabAdmin admin) {
       return adminService.register(admin);
    }
}

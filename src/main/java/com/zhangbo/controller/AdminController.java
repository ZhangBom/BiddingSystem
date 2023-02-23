package com.zhangbo.controller;

import com.zhangbo.service.AdminService;
import com.zhangbo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @GetMapping("info")
    public Result getInfo(){
        return adminService.getInfo();
    }
}

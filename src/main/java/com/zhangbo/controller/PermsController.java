package com.zhangbo.controller;

import com.zhangbo.service.PermsService;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("/perms")
public class PermsController {
    @Autowired
    private  PermsService service;
    @PostMapping ("role_findAll")
    public Result role_findAll(@RequestBody PageQuery pageQuery){
       return service.role_findAll(pageQuery);
    }
}

package com.zhangbo.controller;

import com.zhangbo.service.ContactService;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService service;
    @PostMapping("findAll")
    public Result findAll(@RequestBody PageQuery pageQuery){
        return service.findAll(pageQuery);
    }
}


package com.zhangbo.controller;

import com.zhangbo.pojo.TabPurchase;
import com.zhangbo.service.ApplicationService;
import com.zhangbo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("/application")
public class ApplicationController {
    @Autowired
    ApplicationService applicationService;
    @PostMapping ("signpurchase")
    public Result signpurchase(@RequestBody TabPurchase purchase){
        return applicationService.signpurchase(purchase);
    }
}

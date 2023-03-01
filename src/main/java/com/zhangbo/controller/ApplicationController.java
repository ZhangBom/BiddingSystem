package com.zhangbo.controller;

import com.zhangbo.pojo.TabPurchase;
import com.zhangbo.service.ApplicationService;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping("get_my_purchase")
    public Result get_my_purchase(@RequestBody PageQuery pageQuery){
        return applicationService.get_my_purchase(pageQuery);
    }
    /**
     * 文件上传
     */
    @PostMapping("application_file_upload")
    public Result purchase_file_upload(@RequestParam("file") MultipartFile file, @RequestParam("purchaseId") String purchase_id) {
        return applicationService.purchase_file_upload(file,purchase_id);
    }
}

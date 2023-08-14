package com.zhangbo.controller;

import com.zhangbo.pojo.TabPurchase;
import com.zhangbo.service.ApplicationService;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("/application")
public class ApplicationController {
    @Autowired
    ApplicationService applicationService;
    @PreAuthorize("hasAnyAuthority('expert','vendor')")
    @PostMapping ("signpurchase")
    public Result signpurchase(@RequestBody TabPurchase purchase){
        return applicationService.signpurchase(purchase);
    }
    @PreAuthorize("hasAnyAuthority('purchase_manager','vendor','expert')")
    @PostMapping("get_my_purchase")
    public Result get_my_purchase(@RequestBody PageQuery pageQuery){
        return applicationService.get_my_purchase(pageQuery);
    }
    /**
     * 投标文件上传
     */
    @PreAuthorize("hasAnyAuthority('vendor')")
    @PostMapping("application_file_upload")
    public Result purchase_file_upload(@RequestParam("file") MultipartFile file, @RequestParam("purchaseId") String purchase_id) {
        return applicationService.purchase_file_upload(file,purchase_id);
    }
    //查出该专家参与的所有项目
    @PreAuthorize("hasAnyAuthority('expert')")
    @PostMapping("expert_get_purchase")
    public Result  expert_get_purchase(@RequestBody PageQuery pageQuery){
        return applicationService.expert_get_purchase(pageQuery);
    }
    @PreAuthorize("hasAnyAuthority('expert','purchase_manager')")
    @PostMapping("get_vendor_purchase_file")
    public Result get_vendor_purchase_file(@RequestBody PageQuery pageQuery){
        return applicationService.get_vendor_purchase_file(pageQuery);
    }
    @PreAuthorize("hasAnyAuthority('purchase_manager')")
    @GetMapping("sure_score")
    public Result sure_score(@RequestParam("purchaseId") String purchase_id){
        return applicationService.sure_score(purchase_id);
    }
    @PreAuthorize("hasAnyAuthority('purchase_manager')")
    @GetMapping("Did_rejection")
    public Result Did_rejection(@RequestParam("purchaseId") String purchase_id){
        return applicationService.Did_rejection(purchase_id);
    }
}

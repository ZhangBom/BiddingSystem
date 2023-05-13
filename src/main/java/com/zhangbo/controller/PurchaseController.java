package com.zhangbo.controller;

import com.zhangbo.pojo.LoginUser;
import com.zhangbo.pojo.TabPurchase;
import com.zhangbo.service.PurchaseService;
import com.zhangbo.until.GetUser;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    /**
     * 查找所有项目
     */

    @PostMapping("purchase_findAll_json")
    public Result purchase_findAll_json(@RequestBody PageQuery pageQuery) {
        return purchaseService.findAll(pageQuery);
    }

    /**
     * 查询所有负责人
     */
    @PreAuthorize("hasAnyAuthority('purchase_manager','admin')")
    @GetMapping("purchase_ContactList")
    public Result get_purchase_ContactList() {
        return purchaseService.get_purchase_ContactList();
    }

    /**
     * 招标文件上传
     */
    @PreAuthorize("hasAnyAuthority('purchase_manager')")
    @PostMapping("purchase_file_upload")
    public Result purchase_file_upload(@RequestParam("file") MultipartFile file, @RequestParam("purchaseId") String purchase_id) {
        return purchaseService.purchase_file_upload(file, purchase_id);
    }
//    /**
//     * 文件删除
//     */
//    @PreAuthorize("hasAnyAuthority('purchase_manager')")
//    @DeleteMapping("purchase_file_delete")
//    public Result purchase_file_delete(@RequestBody String filePath) {
//        return purchaseService.purchase_file_delete(filePath);
//    }

    /**
     * 增加项目
     */
    @PreAuthorize("hasAnyAuthority('purchase_manager')")
    @PostMapping("purchase_Add")
    public Result purchase_Add(@RequestBody TabPurchase purchase) {
        return purchaseService.purchase_Add(purchase);
    }

    /**
     * 项目删除
     */
    @PreAuthorize("hasAnyAuthority('purchase_manager')")
    @DeleteMapping("purchase_delete")
    public Result purchase_delete(@RequestBody TabPurchase purchase) {
        return purchaseService.purchase_delete(purchase);
    }

    /**
     * 项目更新
     */
    @PreAuthorize("hasAnyAuthority('purchase_manager','admin')")
    @PutMapping("purchase_update")
    public Result purchase_update(@RequestBody TabPurchase purchase) {
        return purchaseService.purchase_update(purchase);
    }
    @GetMapping("purchase_top10")
    public Result purchase_top10() {
        return purchaseService.purchase_top10();
    }

    @PreAuthorize("hasAnyAuthority('purchase_manager','vendor','expert','admin')")
    @GetMapping("find_purchaseById")
    public Result find_purchaseById(@RequestParam String purchaseId) {
        return purchaseService.find_purchaseById(purchaseId);
    }
    @PreAuthorize("hasAnyAuthority('purchase_manager','admin')")
    @GetMapping("purchaseCount")
    public Result purchaseCount() {
        return purchaseService.purchaseCount();
    }

    @PreAuthorize("hasAnyAuthority('purchase_manager','admin')")
    @GetMapping("purchase_amount")
    public Result purchase_amount() {
        return purchaseService.purchase_amount();
    }

    @PreAuthorize("hasAnyAuthority('purchase_manager','admin')")
    @GetMapping("purchase_moth_amount")
    public Result purchase_moth_amount() {
        return purchaseService.purchase_moth_amount();
    }
    @PreAuthorize("hasAnyAuthority('purchase_manager','admin')")
    @GetMapping("get_purchase_moth_num")
    public Result get_purchase_moth_num(){
        return purchaseService.get_purchase_moth_num();
    }
}

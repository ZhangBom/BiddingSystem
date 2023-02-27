package com.zhangbo.controller;

import com.zhangbo.pojo.LoginUser;
import com.zhangbo.pojo.TabPurchase;
import com.zhangbo.service.PurchaseService;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("purchase_findAll_json")
    public Result purchase_findAll_json(@RequestBody PageQuery pageQuery) {
        //获取操作的用户对象
        LoginUser loginUser= (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //获取用户名
        System.out.println(loginUser.getUsername());

        return purchaseService.findAll(pageQuery);
    }

    /**
     * 查询所有联系人
     */
    @GetMapping("purchase_ContactList")
    public Result get_purchase_ContactList() {
        return purchaseService.get_purchase_ContactList();
    }

    /**
     * 文件上传
     */
    @PostMapping("purchase_file_upload")
    public Result purchase_file_upload(@RequestParam("file") MultipartFile file, @RequestParam("purchaseId") String purchase_id) {
        return purchaseService.purchase_file_upload(file,purchase_id);
    }
    /**
     * 文件删除
     */
    @DeleteMapping("purchase_file_delete")
    public Result purchase_file_delete(@RequestBody String filePath) {
        return purchaseService.purchase_file_delete(filePath);
    }
    /**
     * 增加项目
     */
    @PostMapping("purchase_Add")
    public Result purchase_Add(@RequestBody TabPurchase purchase) {
        return purchaseService.purchase_Add(purchase);
    }
    /**
     *项目删除
     */
    @DeleteMapping("purchase_delete")
    public Result purchase_delete(@RequestBody TabPurchase purchase) {
        return purchaseService.purchase_delete(purchase);
    }

    /**
     *项目更新
     */
    @PutMapping("purchase_update")
    public Result purchase_update(@RequestBody TabPurchase purchase) {
        return purchaseService.purchase_update(purchase);
    }
    @GetMapping("purchase_top10")
    public Result purchase_top10(){
        return purchaseService.purchase_top10();
    }
    @GetMapping("purchase_info")
    public Result purchase_info(){
        return purchaseService.purchase_info();
    }
}

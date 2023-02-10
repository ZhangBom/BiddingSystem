package com.zhangbo.controller;

import com.zhangbo.pojo.TabPurchase;
import com.zhangbo.service.PurchaseService;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    /**
     * 查找所有项目
     *
     * @param pageQuery
     * @return
     */
    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("purchase_findAll_json")
    public Result purchase_findAll_json(@RequestBody PageQuery pageQuery) {
        return purchaseService.findAll(pageQuery);
    }

    /**
     * 查询所有待审核项目
     *
     * @param pageQuery
     * @return
     */
    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("purchase_findAll_Audit")
    public Result purchase_findAll_Audit(@RequestBody PageQuery pageQuery) {
        return purchaseService.findAllAudit(pageQuery);
    }

    /**
     * 查询所有联系人
     *
     * @return
     */
    @GetMapping("purchase_ContactList")
    public Result get_purchase_ContactList() {
        return purchaseService.get_purchase_ContactList();
    }

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
//    @PostMapping("purchase_file_upload")
//    public Result purchase_file_upload(@RequestParam("file") MultipartFile file) {
////        @RequestParam("purchaseid") String purchaseid,@RequestParam("purchasename") String purchasename
////        System.out.println(purchaseid+purchasename);
//        return purchaseService.purchase_file_upload(file);
//    }
    @PostMapping("purchase_file_upload")
    public Result purchase_file_upload(@RequestParam("file") MultipartFile file, @RequestParam("purchaseid") String purchaseid) {
        return purchaseService.purchase_file_upload(file,purchaseid);
    }
    /**
     * 文件删除
     *
     * @param filePath
     * @return
     */
    @DeleteMapping("purchase_file_delete")
    public Result purchase_file_delete(@RequestBody String filePath) {
        return purchaseService.purchase_file_delete(filePath);
    }

    @GetMapping("purchase_file_download")
    public Result purchase_file_download(HttpServletResponse response, @RequestBody String filePath) throws UnsupportedEncodingException {
        return purchaseService.purchase_file_download(response, filePath);
    }


    /**
     * 增加项目
     *
     * @param purchase
     * @return
     */
    @PostMapping("purchase_Add")
    public Result purchase_Add(@RequestBody TabPurchase purchase) {
        return purchaseService.purchase_Add(purchase);
    }

    @PutMapping("purchase_update")
    public Result purchase_update(@RequestBody TabPurchase purchase) {
        System.out.println(purchase);
        return purchaseService.purchase_update(purchase);
    }
}

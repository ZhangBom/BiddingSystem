package com.zhangbo.controller;

import com.zhangbo.pojo.TabVendor;
import com.zhangbo.service.VendorService;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("/vendor")
public class VendorController {
    @Autowired
    private VendorService vendorService;

    /**
     * 查询所有供应商
     *
     * @param pageQuery
     * @return
     */
    @PreAuthorize("hasAnyAuthority('admin','purchase_manager')")
    @PostMapping("findAll")
    public Result vendor_findAll(@RequestBody PageQuery pageQuery) {
        return vendorService.findAll(pageQuery);
    }

    /**
     * 获取供应商级别
     *
     * @return
     */
    @GetMapping("find_vendor_level")
    public Result find_vendor_level() {
        return vendorService.find_vendor_level();
    }

    /**
     * 获取供应商类型
     *
     * @return
     */
    @GetMapping("find_vendor_type")
    public Result find_vendor_type() {
        return vendorService.find_vendor_type();
    }
    /**
     * 更新操作
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('admin','purchase_manager','vendor')")
    @PutMapping("vendor_update")
    public Result vendor_update(@RequestBody TabVendor vendor) {
        return vendorService.vendor_update(vendor);
    }
    @PreAuthorize("hasAnyAuthority('vendor')")
    @GetMapping("info")
    public Result getInfo(){
        return vendorService.getInfo();
    }

    @GetMapping("checkvendor")
    public Result checkvendor(){
        return vendorService.checkvendor();
    }
    @PreAuthorize("hasAnyAuthority('vendor')")
    @PostMapping("vendor_file_upload")
    public Result vendor_file_upload(@RequestParam("file") MultipartFile file,@RequestParam("vendorId") String vendor_id){
        return vendorService.vendor_file_upload(file,vendor_id);
    }
}

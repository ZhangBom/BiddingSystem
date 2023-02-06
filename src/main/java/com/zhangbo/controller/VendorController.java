package com.zhangbo.controller;

import com.zhangbo.pojo.TabVendor;
import com.zhangbo.service.VendorService;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 获取供应商级别
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
    @PutMapping("vendor_update")
    public Result vendor_update(@RequestBody TabVendor vendor) {
        return vendorService.vendor_update(vendor);
    }
}

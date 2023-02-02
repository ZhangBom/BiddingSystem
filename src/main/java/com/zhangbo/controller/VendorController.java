package com.zhangbo.controller;

import com.zhangbo.service.PurchaseService;
import com.zhangbo.service.VendorService;
import com.zhangbo.until.PurchaseQuery;
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
    @GetMapping("findAll")
    public Result vendor_findAll(@RequestBody PurchaseQuery purchaseQuery){
        return vendorService.findAll(purchaseQuery);
    }
}

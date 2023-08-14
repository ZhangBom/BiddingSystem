package com.zhangbo.controller;

import com.zhangbo.pojo.TabExpert;
import com.zhangbo.service.ExpertService;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("/expert")
public class ExpertController {
    @Autowired
    private ExpertService expertService;

    @PostMapping("findAll")
    public Result findAll_export(@RequestBody PageQuery pageQuery){
        return expertService.findAll(pageQuery);
    }
    @PreAuthorize("hasAnyAuthority('admin','purchase_manager','expert')")
    @PutMapping("expert_update")
    public Result expert_update(@RequestBody TabExpert expert) {
        return expertService.expert_update(expert);
    }
    @PreAuthorize("hasAnyAuthority('expert')")
    @GetMapping("info")
    public Result getInfo(){
        return expertService.getInfo();
    }
}

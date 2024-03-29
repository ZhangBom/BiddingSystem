package com.zhangbo.controller;

import com.zhangbo.pojo.TabScore;
import com.zhangbo.service.ScoreService;
import com.zhangbo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("/score")
public class ScoreController {
    @Autowired
    ScoreService scoreService;
    @PreAuthorize("hasAnyAuthority('expert')")
    @PostMapping("vendorScore")
    public Result vendorScore(@RequestBody TabScore score){
        System.out.println(score);
       return scoreService.vendorScore(score);
    }
    @PreAuthorize("hasAnyAuthority('expert','purchase_manager')")
    @GetMapping("getScore")
    public Result get_score(@RequestParam("purchaseId") String purchase_id,@RequestParam("vendorAccount") String vendor_account){
        return scoreService.get_score(purchase_id,vendor_account);
    }
}

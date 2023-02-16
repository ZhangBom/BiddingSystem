package com.zhangbo.controller;

import com.zhangbo.service.StructureService;
import com.zhangbo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("/structure")
public class StructureServiceController {
    @Autowired
    private StructureService structureService;

    @GetMapping("findAll")
    public Result findAll(@RequestParam String tabName){
        return structureService.findAll(tabName);
    }
    @GetMapping("findAllopt")
    public Result findAllopt(){
        return structureService.findAllopt();
    }
}

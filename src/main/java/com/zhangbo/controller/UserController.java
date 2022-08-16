package com.zhangbo.controller;

import com.zhangbo.pojo.User;
import com.zhangbo.service.UserService;
import com.zhangbo.until.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "login")
    public Result login(@RequestBody User user) {
        System.out.println(user+"==========================");
        return userService.login(user);
    }
    @RequestMapping("logout")
    public Result logout() {
        return userService.logout();
    }
    @PreAuthorize("hasAuthority('test2')")
    @GetMapping("hello")
    public String hello(){
        return "hello";
    }
    @PostMapping("register")
    public Result register(@RequestBody User user){
        return userService.register(user);
    }
}


package com.zhangbo.controller;

import com.zhangbo.pojo.User;
import com.zhangbo.service.UserService;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Params;
import com.zhangbo.until.Result;
import com.zhangbo.until.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin
@ResponseBody
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "login")
    public Result login(@RequestBody User user) {
        return userService.login(user);
    }

    @RequestMapping("logout")
    public Result logout() {
        return userService.logout();
    }

//    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("info")
//    public Result get_userinfo(@RequestParam(value = "token", required = true) String token) {
    public Result get_userinfo() {
        return userService.userinfo();
    }
  @PostMapping("findAll")
  public Result findAll(@RequestBody PageQuery pageQuery){
        return userService.findAll(pageQuery);
  }
    @PostMapping("register")
    public Result register(@RequestBody User user) {
        return userService.register(user);
    }
    @PutMapping("banuser")
    public Result banuser(@RequestBody User user){
        return  userService.banuser(user);
    }
    @PostMapping("user_avatar")
    public Result user_avatar(@RequestParam MultipartFile avatar){
        return userService.user_avatar(avatar);
    }
    @GetMapping("checkcode")
    public Result checkcode(){
        return userService.checkbox();
    }
    @PutMapping("updatepass")
    public Result updatepass(@RequestBody Params params){
        return userService.updatepass(params);
    }
}


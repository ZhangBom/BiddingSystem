package com.zhangbo.controller;

import com.zhangbo.pojo.TabSuggestion;
import com.zhangbo.pojo.User;
import com.zhangbo.service.UserService;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Params;
import com.zhangbo.until.Result;
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
    @GetMapping("userinfo")
//    public Result get_userinfo(@RequestParam(value = "token", required = true) String token) {
    public Result get_userinfo() {
        return userService.userinfo();
    }
    @PreAuthorize("hasAnyAuthority('admin')")
  @PostMapping("findAll")
  public Result findAll(@RequestBody PageQuery pageQuery){
        return userService.findAll(pageQuery);
  }
    @PostMapping("register")
    public Result register(@RequestBody User user) {
        return userService.register(user);
    }
    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping("ban_user")
    public Result banuser(@RequestBody User user){
        return  userService.banuser(user);
    }
    @PostMapping("user_avatar")
    public Result user_avatar(@RequestParam("file") MultipartFile avatar){
        return userService.user_avatar(avatar);
    }
    @GetMapping("check_code")
    public Result checkcode(){
        return userService.checkbox();
    }
    @PutMapping("update_pass")
    public Result updatepass(@RequestBody Params params){
        return userService.updatepass(params);
    }
    @PutMapping("user_update")
    public Result user_update(@RequestBody Params params){
        return userService.user_update(params);
    }
    @GetMapping("suggestion")
    public Result suggestion(@RequestParam("textarea") String textarea){
        return userService.suggestion(textarea);
    }
    @PostMapping("get_suggestion")
    public Result get_suggestion(@RequestBody PageQuery pageQuery){
        return userService.get_suggestion(pageQuery);
    }
    @PutMapping("deal_Suggestion")
    public Result deal_Suggestion(@RequestBody TabSuggestion suggestion){
        return userService.deal_Suggestion(suggestion);
    }
    @PreAuthorize("hasAnyAuthority('admin','purchase_manager')")
    @GetMapping("userCount")
    public Result userCount(){
        return userService.userCount();
    }
    @PreAuthorize("hasAnyAuthority('admin','purchase_manager')")
    @GetMapping("suggestionCount")
    public Result suggestionCount(){
        return userService.suggestionCount();
    }
    @PreAuthorize("hasAnyAuthority('admin','purchase_manager')")
    @GetMapping("get_user_moth_num")
    public Result get_user_moth_num(){
        return userService.get_user_moth_num();
    }
    @PreAuthorize("hasAnyAuthority('admin','purchase_manager')")
    @GetMapping("get_suggestion_moth_num")
    public Result get_suggestion_moth_num(){
        return userService.get_suggestion_moth_num();
    }
}


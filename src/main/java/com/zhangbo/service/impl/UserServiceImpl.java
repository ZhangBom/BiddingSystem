package com.zhangbo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbo.mapper.RecordMapper;
import com.zhangbo.mapper.SuggestionMapper;
import com.zhangbo.pojo.*;
import com.zhangbo.service.UserService;
import com.zhangbo.until.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.zhangbo.mapper.UserMapper;
import org.springframework.web.multipart.MultipartFile;
import com.zhangbo.until.CodeKey;
import com.zhangbo.until.GetUser;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SuggestionMapper suggestionMapper;
    @Autowired
    private RecordMapper recordMapper;

    private DateDiff dateDiff=new DateDiff();
    //头像文件夹
    private static final String IMAGEFILE = "/avatar/";

    /**
     * 用户登录
     * 用户对象
     *
     * @param user 用户
     * @return 规范化结果集
     */
    @Override
    public Result login(User user) {
        //判断用户是否存在（同时验证密码）
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getUserPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            return Result.resultFactory(Status.LOGIN_FAIL);
        }
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String jwt = JwtUtil.createJWT(loginUser.getUser().getUserId());
        //authenticate存入redis
        String redisKey = "user:" + loginUser.getUser().getUserId();
        redisCache.setCacheObject(redisKey, loginUser, 60 * 60 * 24, TimeUnit.SECONDS);
        //把token响应给前端
        HashMap<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return Result.resultFactory(Status.LOGIN_SUCCESS, map);
    }

    /**
     * 用户登出
     *
     * @return 成功状态码
     */
    @Override
    public Result logout() {

        //从redis中删除用户信息
        String redisKey = "user:" + GetUser.getuserid();
        redisCache.deleteObject(redisKey);
        return Result.resultFactory(Status.LOGOUT_SUCCESS);
    }

    /**
     * 用户注册
     *
     * @param user 用户对象
     * @return 成功状态码
     */
    @Override
    public Result register(User user) {
        if (check_name(user)) {//检查用户名是否已经注册
            if (check_phone(user)) {//检查电话是否已经注册
                if (check_email(user)) {//检查邮箱是否已经注册
                    user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));//密码加密
                    //设置用户状态为待审核
                    user.setUserStatus("0");
                    user.setUserRegisterTime(dateDiff.getNowhhhh());
                    save(user);
                    return Result.resultFactory(Status.REGISTER_SUCCESS, user);
                } else {
                    return Result.resultFactory(Status.REGISTER_FAIL_EMAIL_HAS, null);
                }
            } else {
                return Result.resultFactory(Status.REGISTER_FAIL_HAS, null);
            }
        } else {
            return Result.resultFactory(Status.REGISTER_FAIL_NAME, null);
        }
    }

    @Override
    public Result userinfo() {
//        String userid;
//        try {
//            Claims claims = JwtUtil.parseJWT(token);
//            userid = claims.getSubject();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Result.resultFactory(Status.STATUS_ERROR);
//        }
        //获取操作的用户对象
//        LoginUser loginUsers = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String userid = loginUsers.getUser().getUserId();

        //从redis中获取用户信息
        String redisKey = "user:" + GetUser.getuserid();
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if (Objects.isNull(loginUser)) {
            return Result.resultFactory(Status.STATUS_ERROR);
        }
        //封装用户信息，不会把用户所有信息返回前端。
        UserInfo userInfo = new UserInfo();
        userInfo.setName(loginUser.getUser().getUserContactName());
        userInfo.setAvatar(loginUser.getUser().getUserImage());
        userInfo.setRoles(loginUser.getPermission());
        userInfo.setUsertype(loginUser.getUser().getUserType());
        userInfo.setUserphone(loginUser.getUser().getUserPhone());
        userInfo.setUseremail(loginUser.getUser().getUserEmail());
        userInfo.setPerms(loginUser.getPerms());
        return Result.resultFactory(Status.STATUS, userInfo);
    }

    @Override
    public Result findAll(PageQuery pageQuery) {
        HumpUntil humpUntil = new HumpUntil();
        BackPage<User> tabPurchaseBackPage = new BackPage<>();
        //构建查询条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(pageQuery.getConditions())) {

            wrapper.like(humpUntil.hump_underline(pageQuery.getConditions()), pageQuery.getTitle());
        }
        if (StringUtils.isNotEmpty(pageQuery.getUsertype())) {
            wrapper.eq("user_type", pageQuery.getUsertype());
        }
        if (StringUtils.isNotEmpty(pageQuery.getSort())) {
            pageQuery.setSort("ascuser_id");
        } else {
            pageQuery.setSort(humpUntil.hump_underline(pageQuery.getSort()));
        }
//        排序
        if (pageQuery.getSort().startsWith("asc")) {
            //截取字符串，按传入字段升序
            wrapper.orderByAsc(pageQuery.getSort().substring(3));
        } else {
            //截取字符串，按传入字段降序
            wrapper.orderByDesc(pageQuery.getSort().substring(4));
        }
        // 构造分页信息，其中的Page<>(page, PAGE_RECORDS_NUM)的第一个参数是当前页数（从第几页开始查），而第二个参数是每页的记录数（查多少条）
        Page<User> postPage = new Page<>(pageQuery.getCurrentPage(), pageQuery.getLimit());
        // page(postPage, wrapper)这里的第一个参数就是上面定义了的Page对象(分页信息)，第二个参数就是上面定义的条件构造器对象，通过调用这个方法就可以根据你的分页信息以及查询信息获取分页数据
        IPage<User> postIPage = page(postPage, wrapper);
        //封装返回格式
        tabPurchaseBackPage.setContentList(postIPage.getRecords());
        tabPurchaseBackPage.setCurrentPage(postIPage.getCurrent());
        tabPurchaseBackPage.setTotalPage(postIPage.getPages());
        tabPurchaseBackPage.setTotalNum(postIPage.getTotal());
        return Result.resultFactory(Status.SUCCESS, tabPurchaseBackPage);
    }
    /**
     * 用户账号封禁与解封
     * @return
     */
    @Override
    public Result banuser(User user) {
        if (GetUser.getuser().getUserType().equals("管理员")) {
            updateById(user);
            TabRecord record = new TabRecord();
            record.setRecordType("账号管理");
            record.setRecordOperator(GetUser.getuser().getUserContactName());
            record.setRecordId("user:" + user.getUserId());
            record.setRecordUpdateTime(dateDiff.getNow());
            recordMapper.insert(record);
            if (user.getUserStatus().equals("1")){
                    //判断用户是否在线
                    String redisKey = "user:" + user.getUserId();
                    LoginUser loginUser = redisCache.getCacheObject(redisKey);
                    if (!Objects.isNull(loginUser)) {
                        redisCache.deleteObject(redisKey);
                    }
            }
            return Result.resultFactory(Status.OPERATION_SUCCESS);
        } else {
            return Result.resultFactory(Status.LOW_POWER_ADMIN);
        }
    }
    /**
     * 验修改头像
     * @return
     */
    @Override
    public Result user_avatar(MultipartFile file) {
        //获取操作的用户对象
        LoginUser loginUsers = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUsers.getUser();
        //从redis中获取用户信息
        String redisKey = "user:" + user.getUserId();
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if (Objects.isNull(loginUser)) {
            return Result.resultFactory(Status.MODIFY_HEAD_FAIL);
        }
        //上传前删除原有图片
        COSUtil.deletefile(user.getUserImage());
        String url = COSUtil.uploadfile(file, IMAGEFILE);
        user.setUserImage(url);
        loginUser.setUser(user);
        updateById(user);
        //删除缓存
        redisCache.deleteObject(redisKey);
        //插入缓存
        redisCache.setCacheObject(redisKey, loginUser, 60 * 60 * 24, TimeUnit.SECONDS);
        return Result.resultFactory(Status.MODIFY_HEAD_SUCCESS, url);
    }
    /**
     * 验证码
     * @return
     */
    @Override
    public Result checkbox() {
        String code = CodeKey.getCode();
        String redisKey = "code:" + GetUser.getuserid();
        //将验证码存入redis,120秒后失效
        redisCache.setCacheObject(redisKey, code, 60 * 60 * 24, TimeUnit.SECONDS);
        String text = "您正在尝试修改密码！非本操作请忽略\n 您的验证码为 \n" + code;
        //验证码发送至用户邮箱
        MailUtils.sendMail(GetUser.getuser().getUserEmail(), text, "xx医院招标采购管理系统验证码");
        return Result.resultFactory(Status.CHECKED_HAS, code);
    }
    /**
     * 用户密码修改
     * @param params
     * @return
     */
    @Override
    public Result updatepass(Params params) {
        //匹配验证码
        String redisKey = "code:" + GetUser.getuserid();
        //MD5加密密码
        String newpass = passwordEncoder.encode(params.getNewpass());
        if (params.getCheckcode().equals(redisCache.getCacheObject(redisKey))) {
            User user = GetUser.getuser();
            user.setUserPassword(newpass);
            //更新密码
            updateById(user);
            return Result.resultFactory(Status.MODIFY_PASSWORD_SUCCESS);
        } else {
            return Result.resultFactory(Status.CHECKED_ERROR);
        }
    }
    /**
     * 用户信息更新
     * @param params
     * @return
     */
    @Override
    public Result user_update(Params params) {
        //匹配验证码
        String redisKey = "code:" + GetUser.getuserid();
        if (params.getCheckcode().equals(redisCache.getCacheObject(redisKey))) {
            //取出redis中的用户信息
            String redisuserKey = "user:" + GetUser.getuserid();
            User user = userMapper.selectById(GetUser.getuserid());
            user.setUserEmail(params.getUserEmail());
            user.setUserContactName(params.getUserName());
            user.setUserPhone(params.getUserPhone());
            user.setUserContactName(params.getUserName());
            updateById(user);
            LoginUser loginUser = redisCache.getCacheObject(redisuserKey);
            loginUser.setUser(user);
            redisCache.setCacheObject(redisuserKey, loginUser, 60 * 60 * 24, TimeUnit.SECONDS);
            return Result.resultFactory(Status.MODIFY_INFO_SUCCESS);
        } else {
            return Result.resultFactory(Status.CHECKED_ERROR);
        }
    }

    /**
     * 投诉与反馈建议
     * @param textarea
     * @return
     */
    @Override
    public Result suggestion(String textarea) {
        TabSuggestion tabSuggestion = new TabSuggestion();
        tabSuggestion.setContent(textarea);
        tabSuggestion.setUserId(GetUser.getuserid());
        tabSuggestion.setSuggestionTime(dateDiff.getNow());
        tabSuggestion.setStatus("未处理");
        suggestionMapper.insert(tabSuggestion);
        return Result.resultFactory(Status.SUGGESTION_SUCCESS);
    }
    /**
     * 查询投诉与反馈建议
     * @param pageQuery
     * @return
     */
    @Override
    public Result get_suggestion(PageQuery pageQuery) {
        BackPage<TabSuggestion> suggestionBackPage = new BackPage<>();
        //构建查询条件
        QueryWrapper<TabSuggestion> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("suggestion_time");
        // 构造分页信息，其中的Page<>(page, PAGE_RECORDS_NUM)的第一个参数是当前页数（从第几页开始查），而第二个参数是每页的记录数（查多少条）
        Page<TabSuggestion> postPage = new Page<>(pageQuery.getCurrentPage(), pageQuery.getLimit());
        // page(postPage, wrapper)这里的第一个参数就是上面定义了的Page对象(分页信息)，第二个参数就是上面定义的条件构造器对象，通过调用这个方法就可以根据你的分页信息以及查询信息获取分页数据
        IPage<TabSuggestion> postIPage = suggestionMapper.selectPage(postPage, wrapper);
        //封装返回格式
        suggestionBackPage.setContentList(postIPage.getRecords());
        suggestionBackPage.setCurrentPage(postIPage.getCurrent());
        suggestionBackPage.setTotalPage(postIPage.getPages());
        suggestionBackPage.setTotalNum(postIPage.getTotal());
        return Result.resultFactory(Status.SUCCESS, suggestionBackPage);

    }
    /**
     * 查询投诉与反馈建议处理
     * @param suggestion
     * @return
     */
    @Override
    public Result deal_Suggestion(TabSuggestion suggestion) {
        suggestion.setStatus("已处理");
        //获取当前时间
        suggestion.setRecordTime(dateDiff.getNow());
        MailUtils.sendMail(suggestion.getUserId(), "您的反馈我们已经处理，请前往投诉处理公告处查看处理详情", "xx人民医院");
        suggestionMapper.updateById(suggestion);
        return Result.resultFactory(Status.OPERATION_SUCCESS);
    }
    /**
     * 统计用户数
     * @return
     */
    @Override
    public Result userCount() {
        return Result.resultFactory(Status.SUCCESS, userMapper.selectCount(null));
    }
    /**
     * 统计投诉与反馈数量
     * @return
     */
    @Override
    public Result suggestionCount() {
        return Result.resultFactory(Status.SUCCESS, suggestionMapper.selectCount(null));
    }
    /**
     * 统计投诉与反馈数量（每月）
     * @return
     */
    @Override
    public Result get_suggestion_moth_num() {
        List<Map<String, Object>> moth_amount = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            moth_amount.add(suggestion_amount(i));
        }
        return Result.resultFactory(Status.SUCCESS, moth_amount);
    }
    /**
     * 统计用户数（每月）
     * @return
     */
    @Override
    public Result get_user_moth_num() {
        List<Map<String, Object>> moth_amount = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            moth_amount.add(amount(i));
        }
        return Result.resultFactory(Status.SUCCESS, moth_amount);
    }
    /**
     * 统计用户数（月）
     * @param index 月份
     * @return
     */
    public Map<String, Object> amount(int index) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        wrapper.select("count(*) as user_num");
        if (index < 10) {
            wrapper.like("user_register_time", year + "-0" + index);
        } else {
            wrapper.like("user_register_time", year + "-" + index);
        }
        return userMapper.selectMaps(wrapper).get(0);
    }
    public Map<String, Object> suggestion_amount(int index) {
        QueryWrapper<TabSuggestion> wrapper = new QueryWrapper<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        wrapper.select("count(*) as suggestion_num");
        if (index < 10) {
            wrapper.like("suggestion_time", year + "年0" + index + "月");
        } else {
            wrapper.like("suggestion_time", year + "年" + index + "月");
        }
        return suggestionMapper.selectMaps(wrapper).get(0);
    }
    /**
     * 检查用名是否存在
     */
    public boolean check_name(User user) {
        return getOne(new QueryWrapper<User>().eq("user_name", user.getUserName())) == null;
    }

    /**
     * 检查用户手机是否存在
     */
    public boolean check_phone(User user) {
        return getOne(new QueryWrapper<User>().eq("user_phone", user.getUserPhone())) == null;
    }

    /**
     * 检查用户邮箱是否存在
     */
    public boolean check_email(User user) {
        return getOne(new QueryWrapper<User>().eq("user_email", user.getUserEmail())) == null;
    }
}

package com.zhangbo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbo.mapper.*;
import com.zhangbo.pojo.*;
import com.zhangbo.service.ApplicationService;
import com.zhangbo.until.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, TabApplication> implements ApplicationService {
    @Autowired
    private VendorMapper vendorMapper;
    @Autowired
    private ApplicationMapper applicationMapper;
    @Autowired
    private  ArticleMapper articleMapper;
    @Autowired
    private PurchaseMapper purchaseMapper;

    private static final String TENDERFILE = "/tenderfile/";
    @Override
    public Result signpurchase(TabPurchase purchase) {
        String mail_path,mail_content;
        DateDiff dateDiff=new DateDiff();
        TabApplication tab_application=new TabApplication();
        tab_application.setPurchaseId(purchase.getPurchaseId());
        tab_application.setPurchaseContact(purchase.getPurchaseContact());
        tab_application.setPurchasePhone(purchase.getPurchasePhone());
        tab_application.setPurchaseName(purchase.getPurchaseName());
        tab_application.setPurchaseAccount(purchase.getPurchaseAccount());
        tab_application.setPurchaseExplain(purchase.getPurchaseExplain());
        String str=purchase.getPurchaseRegistrationDeadline().replaceAll("\\s*","");
        //设置开标时间
        tab_application.setPurchaseBidsTime(dateDiff.DateWeekend(str));
        //获取报名时间
        tab_application.setApplicationTime(dateDiff.getNow());

        QueryWrapper<TabApplication> wrapper=new QueryWrapper<>();
        wrapper.eq("purchase_id",purchase.getPurchaseId());
        if (GetUser.getuser().getUserType().equals("专家")){
            //将专家账号信息添加到对应信息备用
            tab_application.setExpertAccount(GetUser.getuserid());
            //构建邮件发送体
            mail_path=GetUser.getuser().getUserEmail();
            mail_content="您已经报名参加我院"+purchase.getPurchaseName()+"项目的评标,开标时间为"+tab_application.getPurchaseBidsTime()
                    +"项目负责人:"+tab_application.getPurchaseContact()+"电话:"+tab_application.getPurchasePhone()+"请您准时参加，确保开标日能顺利评标";

            wrapper.like("expert_account",GetUser.getuserid());
        }else if(GetUser.getuser().getUserType().equals("供应商")){
            //获取对应供应商
            QueryWrapper<TabVendor> vendor_wrapper=new QueryWrapper<>();
            vendor_wrapper.eq("vendor_account",GetUser.getuserid());
            TabVendor vendor=vendorMapper.selectOne(vendor_wrapper);
            //将供应商信息添加到对应信息备用
            tab_application.setVendorId(vendor.getVendorId());
            tab_application.setVendorAccount(vendor.getVendorAccount());
            tab_application.setExpertAccount("expert_account");
            //构建邮件发送体
            mail_path=GetUser.getuser().getUserEmail();
            mail_content=vendor.getVendorName()+"您已经报名参加我院"+purchase.getPurchaseName()+"项目的投标,开标时间为"+tab_application.getPurchaseBidsTime()
                    +"项目负责人:"+tab_application.getPurchaseContact()+"电话:"+tab_application.getPurchasePhone()+",招标文件下载地址为"+purchase.getPurchaseFile()
                    +"请及时下载"+"请在开标时间"+tab_application.getPurchaseBidsTime()+"之前将投标文件上传值至我院招标采购系统"+"系统地址";
            wrapper.like("vendor_id",vendor.getVendorId());
        }else {
            //不是供应商或专家，拒绝报名
            return Result.resultFactory(Status.SIGN_PURCHASE_FAIL);
        }
        //未重复报名，可参加
        if(Objects.isNull(applicationMapper.selectOne(wrapper))){
            //更新信息
           save(tab_application);
            //发送报名成功邮件
           boolean mail=MailUtils.sendMail(mail_path,mail_content,"xx医院"+tab_application.getPurchaseName());
           if (mail){
               return Result.resultFactory(Status.SIGN_PURCHASE_SUCCESS);
           }
            return Result.resultFactory(Status.SIGN_PURCHASE_SUCCESS_MAIL_FAIL);
        }else {
            //重复报名返回提示
            return Result.resultFactory(Status.SIGN_PURCHASE_REPEAT);
        }
    }

    @Override
    public Result get_my_purchase(PageQuery pageQuery) {
        BackPage<TabApplication> tabApplicationBackPage = new BackPage<>();
        QueryWrapper<TabApplication> wrapper =new QueryWrapper<>();
        //判断用户角色是供应商or专家or项目负责人
        User user=GetUser.getuser();
        if(user.getUserType().equals("供应商")){
            wrapper.eq("vendor_account",user.getUserId());
        }else if(user.getUserType().equals("专家")){
            wrapper.eq("expert_account",user.getUserId());
        }else if(user.getUserType().equals("项目负责人")) {
            wrapper.eq("purchase_phone",user.getUserPhone());
            wrapper.eq("vendor_account","");
            wrapper.eq("expert_account","");
        }
        //按时间排序
        wrapper.orderByDesc("application_time");
        // 构造分页信息，其中的Page<>(page, PAGE_RECORDS_NUM)的第一个参数是当前页数（从第几页开始查），而第二个参数是每页的记录数（查多少条）
        Page<TabApplication> postPage = new Page<>(pageQuery.getCurrentPage(), pageQuery.getLimit());

        // page(postPage, wrapper)这里的第一个参数就是上面定义了的Page对象(分页信息)，第二个参数就是上面定义的条件构造器对象，通过调用这个方法就可以根据你的分页信息以及查询信息获取分页数据
        IPage<TabApplication> postIPage = page(postPage, wrapper);
        //封装返回格式
        tabApplicationBackPage.setContentList(postIPage.getRecords());
        tabApplicationBackPage.setCurrentPage(postIPage.getCurrent());
        tabApplicationBackPage.setTotalPage(postIPage.getPages());
        tabApplicationBackPage.setTotalNum(postIPage.getTotal());
        return Result.resultFactory(Status.SUCCESS, tabApplicationBackPage);
    }

    @Override
    public Result purchase_file_upload(MultipartFile file, String purchase_id) {
        QueryWrapper<TabVendor> wrapper=new QueryWrapper<>();
        wrapper.eq("vendor_account",GetUser.getuserid());
        String vendor_id=vendorMapper.selectOne(wrapper).getVendorId();
        //判断该项目是否已有相关文件
        TabApplication application = applicationMapper.selectOne(new LambdaQueryWrapper<TabApplication>().eq(TabApplication::getPurchaseId, purchase_id).eq(TabApplication::getVendorId, vendor_id));
        if (Objects.isNull(application.getPurchaseFilePath())) {
            //  否,上传文件 返回地址
            String filepath = COSUtil.uploadfile(file, TENDERFILE);
            //  将地址更新
            application.setPurchaseFilePath(filepath);
            updateById(application);
            return Result.resultFactory(Status.SUBMIT_SUCCESS);
        } else {
            //是 获取文件地址删除文件，重新上传，返回新地址
            if (COSUtil.deletefile(application.getPurchaseFilePath())) {
                String filepath = COSUtil.uploadfile(file, TENDERFILE);
                //  将地址更新
                application.setPurchaseFilePath(filepath);
                updateById(application);
                return Result.resultFactory(Status.SUBMIT_SUCCESS);
            } else {
                return Result.resultFactory(Status.SERVER_FAIL);
            }
        }
    }

    @Override
    public Result expert_get_purchase(PageQuery pageQuery) {
        BackPage<TabApplication> backPage=new BackPage<>();
        QueryWrapper<TabApplication> wrapper =new QueryWrapper<>();
        wrapper.eq("expert_account",GetUser.getuserid());
        // 构造分页信息，其中的Page<>(page, PAGE_RECORDS_NUM)的第一个参数是当前页数（从第几页开始查），而第二个参数是每页的记录数（查多少条）
        Page<TabApplication> postPage = new Page<>(pageQuery.getCurrentPage(), pageQuery.getLimit());

        // page(postPage, wrapper)这里的第一个参数就是上面定义了的Page对象(分页信息)，第二个参数就是上面定义的条件构造器对象，通过调用这个方法就可以根据你的分页信息以及查询信息获取分页数据
        IPage<TabApplication> postIPage = page(postPage, wrapper);
        //封装返回格式
        backPage.setContentList(postIPage.getRecords());
        backPage.setCurrentPage(postIPage.getCurrent());
        backPage.setTotalPage(postIPage.getPages());
        backPage.setTotalNum(postIPage.getTotal());
        return Result.resultFactory(Status.SUCCESS, backPage);
    }

    @Override
    public Result get_vendor_purchase_file(PageQuery pageQuery) {
        BackPage<TabApplication> backPage=new BackPage<>();
        QueryWrapper<TabApplication> wrapper =new QueryWrapper<>();
        wrapper.eq("purchase_id",pageQuery.getPurchaseId());
//        if(GetUser.getuser().getUserType().equals("专家")) {
            wrapper.eq("expert_account", "expert_account");
//        }
//        else {
//            wrapper.ne("expert_account", "expert_account");
//        }
        wrapper.orderByDesc("vendor_score");
        // 构造分页信息，其中的Page<>(page, PAGE_RECORDS_NUM)的第一个参数是当前页数（从第几页开始查），而第二个参数是每页的记录数（查多少条）
        Page<TabApplication> postPage = new Page<>(pageQuery.getCurrentPage(), pageQuery.getLimit());
        // page(postPage, wrapper)这里的第一个参数就是上面定义了的Page对象(分页信息)，第二个参数就是上面定义的条件构造器对象，通过调用这个方法就可以根据你的分页信息以及查询信息获取分页数据
        IPage<TabApplication> postIPage = page(postPage, wrapper);
        //封装返回格式
        backPage.setContentList(postIPage.getRecords());
        backPage.setCurrentPage(postIPage.getCurrent());
        backPage.setTotalPage(postIPage.getPages());
        backPage.setTotalNum(postIPage.getTotal());
        return Result.resultFactory(Status.SUCCESS, backPage);
    }

    @Override
    public Result sure_score(String purchase_id) {
        QueryWrapper<TabApplication> wrapper=new QueryWrapper<>();
        wrapper.eq("purchase_id",purchase_id);
        wrapper.eq("expert_account","expert_account");
        wrapper.orderByDesc("vendor_score");
        List<TabApplication> list=applicationMapper.selectList(wrapper);
        if(list.size()<3){
            return Result.resultFactory(Status.DID_REJECTION);
        }else {
            TabApplication application = list.get(0);
            //获取中标供应商信息
            TabVendor vendor = vendorMapper.selectById(application.getVendorId());
            //发送中标通知
            MailUtils.sendMail(vendor.getVendorEmail(), "恭喜您成为" + application.getPurchaseName() + "的中标供应商，请联系项目负责人商定合同签订事宜。", "XX人民医院" + application.getPurchaseName());
            //添加中标结果公告
            TabArticle article = new TabArticle();
            article.setArticleTitle(application.getPurchaseName() + "中标公告");
            article.setArticleContent("<h1 style=\"text-align: center;\" >" + application.getPurchaseName() + "中标公告</h1> <p>项目名称：" + application.getPurchaseName() + "</p> <p>项目编号：" + application.getPurchaseId()
                    + "</p> <p>招&nbsp; 标 方：xx人民医院</p> <p>固定电话：01234567</p> <p>电子邮箱：xxxxxxx@gmail.com</p> <p>开标结果：已中标</p> <p>中标人："
                    + vendor.getVendorName() + "</p>  <p>&nbsp;</p> <p>&nbsp;</p>");
            article.setArticleType("结果公告");
            DateDiff dateDiff = new DateDiff();
            article.setArticleTime(dateDiff.getNow());
            articleMapper.insert(article);
            TabPurchase purchase = purchaseMapper.selectOne(new LambdaQueryWrapper<TabPurchase>().eq(TabPurchase::getPurchaseId, purchase_id));
            purchase.setPurchaseStatus("已结束");
            purchaseMapper.updateById(purchase);
            return Result.resultFactory(Status.PURCHASE_BIDDING_SUCCESS);
        }
    }

    @Override
    public Result Did_rejection(String purchase_id) {
        QueryWrapper<TabApplication> wrapper=new QueryWrapper<>();
        wrapper.eq("purchase_id",purchase_id);
        wrapper.eq("expert_account","expert_account");
        wrapper.orderByDesc("vendor_score");
        List<TabApplication> list=applicationMapper.selectList(wrapper);
        if(list.size()<3){
            //废标
            TabApplication application = list.get(0);
            //添加中标结果公告
            TabArticle article = new TabArticle();
            article.setArticleTitle(application.getPurchaseName() + "中标公告");
            article.setArticleContent("<h1 style=\"text-align: center;\" >" + application.getPurchaseName() + "中标公告</h1> <p>项目名称：" + application.getPurchaseName() + "</p> <p>项目编号：" + application.getPurchaseId()
                    + "</p> <p>招&nbsp; 标 方：xx人民医院</p> <p>固定电话：01234567</p> <p>电子邮箱：xxxxxxx@gmail.com</p> <p>开标结果：废标</p> <p>中标人：无"
                    + "</p>  <p>&nbsp;</p> <p>&nbsp;</p>");
            article.setArticleType("结果公告");
            DateDiff dateDiff = new DateDiff();
            article.setArticleTime(dateDiff.getNow());
            articleMapper.insert(article);
            TabPurchase purchase = purchaseMapper.selectOne(new LambdaQueryWrapper<TabPurchase>().eq(TabPurchase::getPurchaseId, purchase_id));
            purchase.setPurchaseStatus("已结束");
            purchaseMapper.updateById(purchase);
            return Result.resultFactory(Status.PURCHASE_BIDDING_SUCCESS);
        }else {
            //判断项目是否违规
//            是，废标
            return Result.resultFactory(Status.DID_REJECTION);
        }
    }
}

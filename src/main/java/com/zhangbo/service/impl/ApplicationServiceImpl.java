package com.zhangbo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbo.mapper.ApplicationMapper;
import com.zhangbo.mapper.VendorMapper;
import com.zhangbo.pojo.*;
import com.zhangbo.service.ApplicationService;
import com.zhangbo.until.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, TabApplication> implements ApplicationService {
    @Autowired
    private VendorMapper vendorMapper;
    @Autowired
    private ApplicationMapper applicationMapper;

    private static final String TENDERFILE = "/tenderfile/";
    @Override
    public Result signpurchase(TabPurchase purchase) {
        DateDiff dateDiff=new DateDiff();
        //获取对应供应商
        QueryWrapper<TabVendor> vendorwrapper=new QueryWrapper<>();
        vendorwrapper.eq("vendor_account",GetUser.getuserid());
        TabVendor vendor=vendorMapper.selectOne(vendorwrapper);

        QueryWrapper<TabApplication> wrapper=new QueryWrapper<>();
        wrapper.eq("vendor_id",vendor.getVendorId());
        wrapper.eq("purchase_id",purchase.getPurchaseId());
        //未重复报名，可参加
        if(Objects.isNull(applicationMapper.selectOne(wrapper))){
            TabApplication tab_application=new TabApplication();
            tab_application.setPurchaseId(purchase.getPurchaseId());
            tab_application.setPurchaseContact(purchase.getPurchaseContact());
            tab_application.setPurchasePhone(purchase.getPurchasePhone());
            String str=purchase.getPurchaseRegistrationDeadline().replaceAll("\\s*","");
            tab_application.setPurchaseBidsTime(dateDiff.DateWeekend(str));
            tab_application.setApplicationTime(dateDiff.getNow());
            tab_application.setVendorId(vendor.getVendorId());
            tab_application.setVendorAccount(vendor.getVendorAccount());
            save(tab_application);
            //发送报名成功邮件
           boolean mail=MailUtils.sendMail(vendor.getVendorEmail(),"您已经报名参加我院"+purchase.getPurchaseName()+"项目的投标,开标时间为"+tab_application.getPurchaseBidsTime()
                    +"项目负责人:"+tab_application.getPurchaseContact()+"电话:"+tab_application.getPurchasePhone()+",招标文件下载地址为"+purchase.getPurchaseFile()
                    +"请及时下载"+"请在开标时间"+tab_application.getPurchaseBidsTime()+"之前将投标文件上传值至我院招标采购系统"+"系统地址","xx医院");
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
            wrapper.eq("expert_id",user.getUserId());
        }else {
            wrapper.eq("purchase_phone",user.getUserPhone());
        }
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
            System.out.println(application);
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
}

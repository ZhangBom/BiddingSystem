package com.zhangbo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbo.mapper.ApplicationMapper;
import com.zhangbo.mapper.VendorMapper;
import com.zhangbo.pojo.TabApplication;
import com.zhangbo.pojo.TabPurchase;
import com.zhangbo.pojo.TabVendor;
import com.zhangbo.service.ApplicationService;
import com.zhangbo.until.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, TabApplication> implements ApplicationService {
    @Autowired
    private VendorMapper vendorMapper;
    @Autowired
    private ApplicationMapper applicationMapper;
    @Override
    public Result signpurchase(TabPurchase purchase) {
        DateDiff dateDiff=new DateDiff();
        String id= GetUser.getuserid();
        //获取对应供应商
        QueryWrapper<TabVendor> vendorwrapper=new QueryWrapper<>();
        vendorwrapper.eq("vendor_account",id);
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
}

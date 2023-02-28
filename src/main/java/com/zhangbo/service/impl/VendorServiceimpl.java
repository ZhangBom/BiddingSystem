package com.zhangbo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbo.mapper.VendorMapper;
import com.zhangbo.pojo.TabVendor;
import com.zhangbo.service.VendorService;
import com.zhangbo.until.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class VendorServiceimpl extends ServiceImpl<VendorMapper, TabVendor> implements VendorService {
    @Autowired
    private VendorMapper vendorMapper;
    //供应商资质文件夹
    private static final String VENDORFILE = "/vendorfile/";
    @Override
    public Result findAll(PageQuery pageQuery) {
        BackPage<TabVendor> tableVendorBackPage = new BackPage<>();
        //构建查询条件
        QueryWrapper<TabVendor> wrapper = new QueryWrapper<>();
        wrapper.eq("vendor_status", "待审核");
        if (StringUtils.isNotEmpty(pageQuery.getConditions())) {
            HumpUntil humpUntil = new HumpUntil();
            wrapper.eq(humpUntil.hump_underline(pageQuery.getConditions()), pageQuery.getTitle());
        }
        if (StringUtils.isNotEmpty(pageQuery.getVendortype())) {
            wrapper.eq("vendor_type", pageQuery.getVendortype());
        }
        if (StringUtils.isNotEmpty(pageQuery.getVendorlevel())) {
            wrapper.eq("vendor_level", pageQuery.getVendorlevel());
        }
        // 构造分页信息，其中的Page<>(page, PAGE_RECORDS_NUM)的第一个参数是当前页数（从第几页开始查），而第二个参数是每页的记录数（查多少条）
        Page<TabVendor> postPage = new Page<>(pageQuery.getCurrentPage(), pageQuery.getLimit());

        // page(postPage, wrapper)这里的第一个参数就是上面定义了的Page对象(分页信息)，第二个参数就是上面定义的条件构造器对象，通过调用这个方法就可以根据你的分页信息以及查询信息获取分页数据
        IPage<TabVendor> postIPage = page(postPage, wrapper);
        //封装返回格式
        tableVendorBackPage.setContentList(postIPage.getRecords());
        tableVendorBackPage.setCurrentPage(postIPage.getCurrent());
        tableVendorBackPage.setTotalPage(postIPage.getPages());
        tableVendorBackPage.setTotalNum(postIPage.getTotal());
        return Result.resultFactory(Status.SUCCESS, tableVendorBackPage);
    }

    @Override
    public Result find_vendor_level() {
        QueryWrapper<TabVendor> wrapper = new QueryWrapper<>();
        wrapper.select("vendor_level").groupBy("vendor_level");
        List<TabVendor> list = vendorMapper.selectList(wrapper);
        return Result.resultFactory(Status.SUCCESS, list);
    }

    @Override
    public Result find_vendor_type() {
        QueryWrapper<TabVendor> wrapper = new QueryWrapper<>();
        wrapper.select("vendor_type").groupBy("vendor_type");
        List<TabVendor> list = vendorMapper.selectList(wrapper);
        return Result.resultFactory(Status.SUCCESS, list);
    }

    @Override
    public Result vendor_update(TabVendor vendor) {
        updateById(vendor);
        return Result.resultFactory(Status.DELETE_INFO_SUCCESS);
    }

    @Override
    public Result getInfo() {
        QueryWrapper<TabVendor> wrapper = new QueryWrapper<>();
        wrapper.eq("vendor_account",GetUser.getuserid());
        TabVendor vendor = vendorMapper.selectOne(wrapper);
        return Result.resultFactory(Status.SUCCESS, vendor);
    }

    @Override
    public Result checkvendor() {
        //获取报名用户id，查询供应商
        String id=GetUser.getuserid();
        QueryWrapper<TabVendor> wrapper=new QueryWrapper<>();
        wrapper.eq("vendor_account",id);
        TabVendor vendor=vendorMapper.selectOne(wrapper);
        //判断审核状态，账号状态是否正常
        if(vendor.getVendorAccountStatus().equals(0) && vendor.getVendorStatus().equals("审核通过")){
            return Result.resultFactory(Status.SUCCESS,true);
        }else {
            return Result.resultFactory(Status.SIGN_PURCHASE_FAIL,false);
        }
    }
}

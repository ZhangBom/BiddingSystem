package com.zhangbo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbo.mapper.PurchaseMapper;
import com.zhangbo.mapper.UserMapper;
import com.zhangbo.pojo.TabPurchase;
import com.zhangbo.pojo.User;
import com.zhangbo.service.PurchaseService;
import com.zhangbo.until.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang.StringUtils;
import java.util.List;

@Service
public class PurchaseServiceimpl extends ServiceImpl<PurchaseMapper, TabPurchase> implements PurchaseService {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PurchaseMapper purchaseMapper;

    @Autowired
    private RedisCache redisCache;
    //自定义文件夹名称项目招标文件夹
    private static final String PURCHASEFILE = "/purchasefile/";
    //自定义文件夹名称项目标书文件夹
    private static final String TENDER = "/tenderfile/";

    @Override
    public Result findAll(PageQuery pageQuery) {
        BackPage<TabPurchase> tabPurchaseBackPage = new BackPage<>();
        //构建查询条件
        QueryWrapper<TabPurchase> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(pageQuery.getConditions())) {
            HumpUntil humpUntil = new HumpUntil();
            wrapper.eq(humpUntil.hump_underline(pageQuery.getConditions()), pageQuery.getTitle());
        }
        //是否为审核页面发起的请求，是：添加条件
        if(pageQuery.getAudit().equals("true")){
            wrapper.ne("purchase_status", "审核通过");
        }
        if (StringUtils.isNotEmpty(pageQuery.getPurchaseType())) {
            wrapper.eq("purchase_type", pageQuery.getPurchaseType());
        }
        if (StringUtils.isNotEmpty(pageQuery.getPurchaseTenderMethod())) {
            wrapper.eq("purchase_tender_method", pageQuery.getPurchaseTenderMethod());
        }
        if (StringUtils.isNotEmpty(pageQuery.getSort())) {
            if (pageQuery.getSort().equals("+purchaseBudget")) {
                wrapper.orderByAsc("purchase_budget");
            } else {
                wrapper.orderByDesc("purchase_budget");
            }
        }
        // 构造分页信息，其中的Page<>(page, PAGE_RECORDS_NUM)的第一个参数是当前页数（从第几页开始查），而第二个参数是每页的记录数（查多少条）
        Page<TabPurchase> postPage = new Page<>(pageQuery.getCurrentPage(), pageQuery.getLimit());

        // page(postPage, wrapper)这里的第一个参数就是上面定义了的Page对象(分页信息)，第二个参数就是上面定义的条件构造器对象，通过调用这个方法就可以根据你的分页信息以及查询信息获取分页数据
        IPage<TabPurchase> postIPage = page(postPage, wrapper);
        //封装返回格式
        tabPurchaseBackPage.setContentList(postIPage.getRecords());
        tabPurchaseBackPage.setCurrentPage(postIPage.getCurrent());
        tabPurchaseBackPage.setTotalPage(postIPage.getPages());
        tabPurchaseBackPage.setTotalNum(postIPage.getTotal());
        return Result.resultFactory(Status.SUCCESS, tabPurchaseBackPage);
    }

    @Override
    public Result get_purchase_ContactList() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_type", "领导");
        List<User> list = userMapper.selectList(wrapper);
        return Result.resultFactory(Status.STATUS, list);
    }

    @Override
    public Result purchase_Add(TabPurchase purchase) {
        //设置项目状态为待审核
        purchase.setPurchaseStatus("待审核");
        save(purchase);
        return Result.resultFactory(Status.ADD_INFO_SUCCESS);
    }

    @Override
    public Result purchase_file_upload(MultipartFile file, String purchase_id) {
        //判断该项目是否已有相关文件
        TabPurchase tabPurchase = purchaseMapper.selectOne(new LambdaQueryWrapper<TabPurchase>().eq(TabPurchase::getPurchaseId, purchase_id));
        if (tabPurchase.getPurchaseFile().equals("未上传") || tabPurchase.getPurchaseFile().equals("")) {
            System.out.println(tabPurchase);
            //  否,上传文件 返回地址
            String filepath = COSUtil.uploadfile(file, PURCHASEFILE);
            //  将地址更新
            tabPurchase.setPurchaseFile(filepath);
            updateById(tabPurchase);
            return Result.resultFactory(Status.SUBMIT_SUCCESS);
        } else {
            //是 获取文件地址删除文件，重新上传，返回新地址
            if (COSUtil.deletefile(tabPurchase.getPurchaseFile())) {
                String filepath = COSUtil.uploadfile(file, PURCHASEFILE);
                //  将地址更新
                tabPurchase.setPurchaseFile(filepath);
                updateById(tabPurchase);
                return Result.resultFactory(Status.SUBMIT_SUCCESS);
            } else {
                return Result.resultFactory(Status.SERVER_FAIL);
            }
        }
    }

    @Override
    public Result purchase_file_delete(String filePath) {
        if (COSUtil.deletefile(filePath)) {
            return Result.resultFactory(Status.SUBMIT_SUCCESS);
        } else {
            return Result.resultFactory(Status.SERVER_FAIL);
        }
    }
//    @Override
//    public Result purchase_file_download(String filePath) {
//        return Result.resultFactory(Status.SUBMIT_SUCCESS, COSUtil.deletefile(filePath));
//    }

    @Override
    public Result purchase_update(TabPurchase purchase) {
        updateById(purchase);
        return Result.resultFactory(Status.OPERATION_SUCCESS);
    }

    @Override
    public Result purchase_delete(TabPurchase purchase) {
        try {
            if (purchase.getPurchaseFile().equals("未上传")) {
                purchaseMapper.deleteById(purchase.getPurchaseId());
            } else {
                //获取项目文件地址删除文件
                COSUtil.deletefile(purchase.getPurchaseFile());
                purchaseMapper.deleteById(purchase.getPurchaseId());
            }
            return Result.resultFactory(Status.DELETE_INFO_SUCCESS);
        } catch (Exception e) {
            return Result.resultFactory(Status.DELETE_FAIL);
        }
    }
}

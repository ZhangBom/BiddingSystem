package com.zhangbo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangbo.mapper.ApplicationMapper;
import com.zhangbo.mapper.PurchaseMapper;
import com.zhangbo.mapper.RecordMapper;
import com.zhangbo.mapper.UserMapper;
import com.zhangbo.pojo.*;
import com.zhangbo.service.PurchaseService;
import com.zhangbo.until.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang.StringUtils;

import java.util.*;

@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, TabPurchase> implements PurchaseService {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PurchaseMapper purchaseMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    ApplicationMapper applicationMapper;
    //自定义文件夹名称项目招标文件夹
    private static final String PURCHASEFILE = "/purchasefile/";
    DateDiff dateDiff = new DateDiff();
    //自定义文件夹名称项目标书文件夹
    private static final String TENDER = "/tenderfile/";

    @Override
    public Result findAll(PageQuery pageQuery) {
        BackPage<TabPurchase> tabPurchaseBackPage = new BackPage<>();
        //构建查询条件
        QueryWrapper<TabPurchase> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(pageQuery.getConditions())) {
            if (pageQuery.getConditions().equals("purchaseAccount")) {
                pageQuery.setTitle(GetUser.getuser().getUserId());
            }
            HumpUntil humpUntil = new HumpUntil();
            wrapper.like(humpUntil.hump_underline(pageQuery.getConditions()), pageQuery.getTitle());
        }
        //是否为审核页面发起的请求，是：添加条件
        if (pageQuery.getAudit().equals("true")) {
            wrapper.ne("purchase_status", "审核通过");
        } else if (pageQuery.getAudit().equals("auditAccess")) {
            wrapper.eq("purchase_status", "审核通过");
        }
        if (StringUtils.isNotEmpty(pageQuery.getPurchaseManager())) {
            wrapper.eq("purchase_phone", GetUser.getuser().getUserPhone());
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
        wrapper.eq("user_type", "项目负责人");
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
        //查询该项目
        TabPurchase tabPurchase = purchaseMapper.selectOne(new LambdaQueryWrapper<TabPurchase>().eq(TabPurchase::getPurchaseId, purchase_id));
        //操作人是否有该项目权限（否，直接返回，是，继续操作）
        if (tabPurchase.getPurchasePhone().equals(GetUser.getuser().getUserPhone())) {
            //是否已有相关文件
            if (tabPurchase.getPurchaseFile().equals("未上传") || tabPurchase.getPurchaseFile().equals("")) {
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
        } else {
            return Result.resultFactory(Status.JURISDICTION_FAIL);
        }
    }

    @Override
    public Result purchase_update(TabPurchase purchase) {
        //查询该项目
        TabPurchase tabPurchase = purchaseMapper.selectOne(new LambdaQueryWrapper<TabPurchase>().eq(TabPurchase::getPurchaseId, purchase.getPurchaseId()));
        //操作人是否有该项目权限（否，直接返回，是，继续操作）
        if (tabPurchase.getPurchasePhone().equals(GetUser.getuser().getUserPhone()) || GetUser.getuser().getUserType().equals("管理员")) {
            updateById(purchase);
            //向操作记录表添加记录
            TabRecord record = new TabRecord();
            if (purchase.getPurchaseStatus().equals("审核通过") || purchase.getPurchaseStatus().equals("审核未通过")) {
                record.setRecordType("采购项目审核");
                record.setRecordOperator(GetUser.getuser().getUserContactName());
                record.setRecordId("purchase:" + purchase.getPurchaseId());
                Calendar calendar = Calendar.getInstance();
                record.setRecordUpdateTime(String.valueOf(calendar.getTime()));
            } else {
                record.setRecordType("更新");
            }
            recordMapper.insert(record);
            if (purchase.getPurchaseStatus().equals("审核通过")){
                QueryWrapper<TabApplication> wrapper=new QueryWrapper<>();
                wrapper.eq("purchase_id",purchase.getPurchaseId());
//                查询报名表是否已有信息，有则删除
                if(Objects.isNull(applicationMapper.selectOne(wrapper))) {
                    //想报名表插入数据
                    TabApplication application = new TabApplication();
                    application.setPurchaseId(purchase.getPurchaseId());
                    application.setPurchaseContact(purchase.getPurchaseContact());
                    application.setPurchasePhone(purchase.getPurchasePhone());
                    application.setPurchaseName(purchase.getPurchaseName());
                    application.setPurchaseAccount(purchase.getPurchaseAccount());
                    application.setPurchaseExplain(purchase.getPurchaseExplain());
                    String str = purchase.getPurchaseRegistrationDeadline().replaceAll("\\s*", "");
                    //设置开标时间
                    application.setPurchaseBidsTime(dateDiff.DateWeekend(str));
                    applicationMapper.insert(application);
                }
            }
            return Result.resultFactory(Status.OPERATION_SUCCESS);
        } else {
            return Result.resultFactory(Status.JURISDICTION_FAIL);
        }
    }

    @Override
    public Result purchase_delete(TabPurchase purchase) {
        //查询该项目
        TabPurchase tabPurchase = purchaseMapper.selectOne(new LambdaQueryWrapper<TabPurchase>().eq(TabPurchase::getPurchaseId, purchase.getPurchaseId()));
        //操作人是否有该项目权限（否，直接返回，是，继续操作）
        if (tabPurchase.getPurchasePhone().equals(GetUser.getuser().getUserPhone())) {
            try {
                if (purchase.getPurchaseFile().equals("未上传")) {
                    purchaseMapper.deleteById(purchase.getPurchaseId());
                } else {
                    //获取项目文件地址删除文件
                    COSUtil.deletefile(purchase.getPurchaseFile());
                    purchaseMapper.deleteById(purchase.getPurchaseId());
                }
                //向操作记录表添加记录
                TabRecord record = new TabRecord();
                record.setRecordType("采购项目删除");
                record.setRecordOperator(GetUser.getuser().getUserContactName());
                record.setRecordId("purchase:" + purchase.getPurchaseId() + "_" + purchase.getPurchaseName());
                Calendar calendar = Calendar.getInstance();
                record.setRecordUpdateTime(String.valueOf(calendar.getTime()));
                return Result.resultFactory(Status.DELETE_INFO_SUCCESS);
            } catch (Exception e) {
                return Result.resultFactory(Status.DELETE_FAIL);
            }
        } else {
            return Result.resultFactory(Status.JURISDICTION_FAIL);
        }
    }

    @Override
    public Result purchase_top10() {
        QueryWrapper<TabPurchase> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("purchase_registration_deadline");
        wrapper.last("limit 0,10 ");
        List<TabPurchase> list = purchaseMapper.selectList(wrapper);
        return Result.resultFactory(Status.STATUS, list);
    }

    @Override
    public Result find_purchaseById(String purchaseId) {
        QueryWrapper<TabPurchase> wrapper = new QueryWrapper<>();
        wrapper.eq("purchase_id", purchaseId);
        TabPurchase purchase = purchaseMapper.selectOne(wrapper);
        return Result.resultFactory(Status.SUCCESS, purchase);
    }

    @Override
    public Result purchaseCount() {
        return Result.resultFactory(Status.SUCCESS, purchaseMapper.selectCount(null));
    }

    @Override
    public Result purchase_amount() {
        String where = "sum(purchase_budget) as amount";
        QueryWrapper<TabPurchase> wrapper = new QueryWrapper<>();
        wrapper.select(where);
        return Result.resultFactory(Status.SUCCESS, purchaseMapper.selectMaps(wrapper));
    }

    @Override
    public Result purchase_moth_amount() {
        List<Map<String, Object>> moth_amount = new ArrayList<>();
        String where = "sum(purchase_budget) as amount";
        for (int i = 1; i <= 12; i++) {
            if (amount(i, where) == null) {
                Map<String, Object> map = new HashMap<>();
                map.put("amount", 0);
                moth_amount.add(map);
            } else {
                moth_amount.add(amount(i, where));
            }
        }
        return Result.resultFactory(Status.SUCCESS, moth_amount);
    }

    @Override
    public Result get_purchase_moth_num() {
        List<Map<String, Object>> moth_num = new ArrayList<>();
        String where = "count(*) as Purchase_num";
        for (int i = 1; i <= 12; i++) {
            moth_num.add(amount(i, where));
        }
        return Result.resultFactory(Status.SUCCESS, moth_num);
    }

    public Map<String, Object> amount(int index, String where) {
        QueryWrapper<TabPurchase> wrapper = new QueryWrapper<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        wrapper.select(where);
        if (index < 10) {
            wrapper.like("purchase_registration_deadline", year + " 年 0" + index + " 月");
        } else {
            wrapper.like("purchase_registration_deadline", year + " 年 " + index + " 月");
        }
        return purchaseMapper.selectMaps(wrapper).get(0);
    }
}

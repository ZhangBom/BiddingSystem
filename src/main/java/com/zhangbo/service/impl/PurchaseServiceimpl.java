package com.zhangbo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import org.apache.commons.lang.StringUtils;
import java.util.List;

@Service
public class PurchaseServiceimpl extends ServiceImpl<PurchaseMapper,TabPurchase> implements PurchaseService {
   @Autowired
    private UserMapper userMapper;
    @Override
    public Result findAll(PurchaseQuery purchaseQuery) {

        BackPage<TabPurchase> tabPurchaseBackPage=new BackPage<>();

        //构建查询条件
        QueryWrapper<TabPurchase> wrapper=new QueryWrapper<>();
        if (StringUtils.isNotEmpty(purchaseQuery.getConditions())){
            HumpUntil humpUntil=new HumpUntil();
            wrapper.eq(humpUntil.hump_underline(purchaseQuery.getConditions()),purchaseQuery.getTitle());
        }
        if (StringUtils.isNotEmpty(purchaseQuery.getPurchaseType())){
            wrapper.eq("purchase_type",purchaseQuery.getPurchaseType());
        }
        if (StringUtils.isNotEmpty(purchaseQuery.getPurchaseTenderMethod())){
            wrapper.eq("purchase_tender_method",purchaseQuery.getPurchaseTenderMethod());
        }
        if (StringUtils.isNotEmpty(purchaseQuery.getSort())){
            System.out.println(purchaseQuery.getSort());
            if (purchaseQuery.getSort().equals("+purchaseBudget")){
                System.out.println("升序");
                  wrapper.orderByAsc("purchase_budget");
            }else {
                System.out.println("降序");
                wrapper.orderByDesc("purchase_budget");
            }
        }
        // 构造分页信息，其中的Page<>(page, PAGE_RECORDS_NUM)的第一个参数是当前页数（从第几页开始查），而第二个参数是每页的记录数（查多少条）
        Page<TabPurchase> postPage = new Page<>(purchaseQuery.getCurrentPage(), purchaseQuery.getLimit());

        // page(postPage, wrapper)这里的第一个参数就是上面定义了的Page对象(分页信息)，第二个参数就是上面定义的条件构造器对象，通过调用这个方法就可以根据你的分页信息以及查询信息获取分页数据
        IPage<TabPurchase> postIPage = page(postPage,wrapper);
        //封装返回格式
        tabPurchaseBackPage.setContentList(postIPage.getRecords());
        tabPurchaseBackPage.setCurrentPage(postIPage.getCurrent());
        tabPurchaseBackPage.setTotalPage(postIPage.getPages());
        tabPurchaseBackPage.setTotalNum(postIPage.getTotal());
        return Result.resultFactory(Status.SUCCESS,tabPurchaseBackPage);
    }

    @Override
    public Result get_purchase_ContactList() {
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("user_type","领导");
        List<User>  list=userMapper.selectList(wrapper);
        return Result.resultFactory(Status.STATUS,list);
    }

    @Override
    public Result purchase_Add(TabPurchase purchase) {
        //设置项目状态为待审核
        purchase.setPurchaseStatus("待审核");
//  save(purchase);
        return Result.resultFactory(Status.ADD_INFO_SUCCESS);
    }
    @Override
    public Result purchase_file_upload(MultipartFile file) {
        return FileUntil.file_upload(file);
    }

    @Override
    public Result purchase_file_delete(String filePath) {
        return FileUntil.purchase_file_delete(filePath);
    }

    @Override
    public Result purchase_file_download(HttpServletResponse response,String filePath) throws UnsupportedEncodingException {
        return FileUntil.file_download(response,filePath);
    }

    @Override
    public Result findAllAudit(PurchaseQuery purchaseQuery) {
        BackPage<TabPurchase> tabPurchaseBackPage=new BackPage<>();
        //构建查询条件
        QueryWrapper<TabPurchase> wrapper=new QueryWrapper<>();
        if (StringUtils.isNotEmpty(purchaseQuery.getConditions())){
            HumpUntil humpUntil=new HumpUntil();
            wrapper.eq(humpUntil.hump_underline(purchaseQuery.getConditions()),purchaseQuery.getTitle());
        }
        if (StringUtils.isNotEmpty(purchaseQuery.getPurchaseType())){
            wrapper.eq("purchase_type",purchaseQuery.getPurchaseType());
        }
        if (StringUtils.isNotEmpty(purchaseQuery.getPurchaseTenderMethod())){
            wrapper.eq("purchase_tender_method",purchaseQuery.getPurchaseTenderMethod());
        }
        wrapper.eq("purchase_status","待审核");
        if (StringUtils.isNotEmpty(purchaseQuery.getSort())){
            if (purchaseQuery.getSort().equals("+purchaseBudget")){
                System.out.println("升序");
                wrapper.orderByAsc("purchase_budget");
            }else {
                System.out.println("降序");
                wrapper.orderByDesc("purchase_budget");
            }
        }

        // 构造分页信息，其中的Page<>(page, PAGE_RECORDS_NUM)的第一个参数是当前页数（从第几页开始查），而第二个参数是每页的记录数（查多少条）
        Page<TabPurchase> postPage = new Page<>(purchaseQuery.getCurrentPage(), purchaseQuery.getLimit());

        // page(postPage, wrapper)这里的第一个参数就是上面定义了的Page对象(分页信息)，第二个参数就是上面定义的条件构造器对象，通过调用这个方法就可以根据你的分页信息以及查询信息获取分页数据
        IPage<TabPurchase> postIPage = page(postPage,wrapper);
        //封装返回格式
        tabPurchaseBackPage.setContentList(postIPage.getRecords());
        tabPurchaseBackPage.setCurrentPage(postIPage.getCurrent());
        tabPurchaseBackPage.setTotalPage(postIPage.getPages());
        tabPurchaseBackPage.setTotalNum(postIPage.getTotal());
        return Result.resultFactory(Status.SUCCESS,tabPurchaseBackPage);
    }

    @Override
    public Result purchase_update(TabPurchase purchase) {
        updateById(purchase);
        return Result.resultFactory(Status.OPERATION_SUCCESS);
    }

}

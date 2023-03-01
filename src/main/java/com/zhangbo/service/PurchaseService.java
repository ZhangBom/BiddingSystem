package com.zhangbo.service;

import com.zhangbo.pojo.TabPurchase;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;
import org.springframework.web.multipart.MultipartFile;



public interface PurchaseService {


    Result findAll(PageQuery pageQuery);

    Result get_purchase_ContactList();

    Result purchase_Add(TabPurchase purchase);

    Result purchase_file_upload(MultipartFile file,String purchase_id);

    Result purchase_file_delete(String filePath);

    Result purchase_update(TabPurchase purchase);

    Result purchase_delete(TabPurchase purchase);

    Result purchase_top10();

    Result find_purchaseById(String purchaseId);
}

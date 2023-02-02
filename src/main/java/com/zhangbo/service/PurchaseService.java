package com.zhangbo.service;

import com.zhangbo.pojo.TabPurchase;
import com.zhangbo.until.PurchaseQuery;
import com.zhangbo.until.Result;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;


public interface PurchaseService {


    Result findAll(PurchaseQuery purchaseQuery);

    Result get_purchase_ContactList();

    Result purchase_Add(TabPurchase purchase);



    Result purchase_file_upload(MultipartFile file);

    Result purchase_file_delete(String filePath);

    Result purchase_file_download(HttpServletResponse response,String filePath) throws UnsupportedEncodingException;

    Result findAllAudit(PurchaseQuery purchaseQuery);

    Result purchase_update(TabPurchase purchase);
}

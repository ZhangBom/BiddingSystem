package com.zhangbo.service;

import com.zhangbo.pojo.TabPurchase;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;


public interface PurchaseService {


    Result findAll(PageQuery pageQuery);

    Result get_purchase_ContactList();

    Result purchase_Add(TabPurchase purchase);



    Result purchase_file_upload(MultipartFile file,String purchaseid);

    Result purchase_file_delete(String filePath);

    Result purchase_file_download(HttpServletResponse response,String filePath) throws UnsupportedEncodingException;

    Result findAllAudit(PageQuery pageQuery);

    Result purchase_update(TabPurchase purchase);
}

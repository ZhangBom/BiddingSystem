package com.zhangbo.service;

import com.zhangbo.pojo.TabPurchase;
import com.zhangbo.until.PageQuery;
import com.zhangbo.until.Result;
import org.springframework.web.multipart.MultipartFile;

public interface ApplicationService {
    Result signpurchase(TabPurchase purchase);

    Result get_my_purchase(PageQuery pageQuery);

    Result purchase_file_upload(MultipartFile file, String purchase_id);
}

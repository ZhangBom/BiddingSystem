package com.zhangbo.service;

import com.zhangbo.pojo.TabScore;
import com.zhangbo.until.Result;

public interface ScoreService {
    Result vendorScore(TabScore score);

    Result get_score(String purchase_id, String vendor_account);
}

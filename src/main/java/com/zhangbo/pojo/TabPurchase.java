package com.zhangbo.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("tab_purchase")
public class TabPurchase {
    @TableId
    private String purchaseId;
    private String purchaseName;
    private String purchaseExplain;
    private Integer purchaseBudget;
    private String purchaseStartTime;
    private String purchaseEndTime;
    private Integer purchaseCharge;
    private String purchaseDeposit;
    private String purchaseSubmission;
    private String purchaseBidding;
    private String purchaseBidOpeningMethod;
    private String purchaseTenderMethod;
    private String purchaseRegistrationDeadline;
    private String purchaseBidsTime;
    private String purchaseType;
    private String purchaseContact;
    private String purchasePhone;
    private String purchaseEmail;
    private String purchaseFile;
    private String purchaseStatus;
}
package com.zhangbo.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("tab_purchase_alter")
public class TabPurchaseAlter {

  private String purchaseId;
  private String purchaseName;
  private String purchaseExplain;
  private Integer purchaseBudget;
  private String purchaseStartTime;
  private String purchaseEndTime;
  private long purchaseCharge;
  private String purchaseDeposit;
  private String purchaseSubmission;
  private String purchaseBidding;
  private String purchaseBidOpeningMethod;
  private String purchaseTenderMethod;
  private String purchaseAcquisitionTime;
  private String purchaseObtained;
  private String purchaseBidderEligibilityCriteria;
  private String purchaseContact;
  private String purchasePhone;
  private String purchaseEmail;
  private String purchaseTenderer;
}
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
@TableName("tab_outcome")
public class TabOutcome {
  @TableId
  private String purchaseId;
  private String purchaseName;
  private String purchaseContract;
  private String purchaseTenderer;
  private String purchaseTenderMethod;
  private String purchaseCompanyName;
  private String purchaseVendorContact;
  private String purchaseVendorPhone;
  private String purchaseVendorEmail;
}

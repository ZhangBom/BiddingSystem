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
@TableName("tab_application")
public class TabApplication {
  private static final long serialVersionUID = -1;
  @TableId
  private String applicationId;
  private String purchaseId;
  private String expertAccount;
  private String applicationTime;
  private String purchaseContact;
  private String purchaseBidsTime;
  private String vendorId;
  private String purchasePhone;
  private String vendorAccount;
  private String purchaseName;
  private String purchaseExplain;
  private String purchaseFilePath;
  private int vendorScore;
}

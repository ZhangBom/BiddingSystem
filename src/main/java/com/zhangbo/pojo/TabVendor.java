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
@TableName("tab_vendor")
public class TabVendor {
  private static final long serialVersionUID = -1;
  @TableId
  private String vendorId;
  private String vendorName;
  private String vendorType;
  private String vendorPhone;
  private String vendorEmail;
  private String vendorLevel;
  private String vendorAptitude;
  private String vendorAccount;
  private String vendorContactName;
  private String vendorContactPhone;
  private String vendorContactEmail;
  private String vendorStatus;
  private String vendorAccountStatus;
  private String vendorAdd;
}

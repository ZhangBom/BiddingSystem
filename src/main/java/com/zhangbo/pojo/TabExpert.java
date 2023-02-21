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
@TableName("tab_expert")
public class TabExpert {
  private static final long serialVersionUID = -1;
  @TableId
  private String expertId;
  private String expertName;
  private String expertPs;
  private String expertGraduation;
  private String expertOcupation;
  private String expertSite;
  private String expertPhone;
  private String expertEmail;
  private String expertIntroduce;
  private String expertAccount;
  private String expertStatus;
  private String expertAccountStatus;
  private String expertIndustry;


}
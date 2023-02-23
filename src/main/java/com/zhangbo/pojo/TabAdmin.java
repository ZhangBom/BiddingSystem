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
@TableName("tab_admin")
public class TabAdmin {
  private static final long serialVersionUID = -1;
  @TableId
  private String adminId;
  private String adminName;
  private String employeesPhone;
  private String employeesId;
  private String employeesName;
  private String employeesEmail;


}

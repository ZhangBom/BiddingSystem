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
@TableName("tab_role")
public class TabRole {
 @TableId
  private long id;
  private String name;
  private String roleKey;
  private String status;
  private long delFlag;
  private long createBy;
  private java.sql.Timestamp createTime;
  private long updateBy;
  private java.sql.Timestamp updateTime;
  private String remark;

}

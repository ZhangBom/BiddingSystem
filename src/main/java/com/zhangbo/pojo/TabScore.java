package com.zhangbo.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("tab_score")
public class TabScore {
@TableId
  private String scoreId;
  private String expertAccount;
  private String vendorAccount;
  private String purchaseId;
  private int vendorScore;
  @TableField(exist = false)
  private int score;
}

package com.zhangbo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("tab_record")
public class TabRecord {
  @TableId(type = IdType.AUTO)
  private long resourceId;
  private String recordId;
  private String recordType;
  private String recordUpdateTime;
  private String recordOperator;
}

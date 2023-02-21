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
@TableName("tab_structure")
public class TabStructure {
  private static final long serialVersionUID = -1;
  @TableId
  private String strId;
  private String tabName;
  private String fieldName;
  private String fieldNameCh;
}

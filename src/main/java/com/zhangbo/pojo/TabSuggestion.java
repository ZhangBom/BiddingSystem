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
@TableName("tab_suggestion")
public class TabSuggestion {
@TableId
  private String id;
  private String content;
  private String userId;
  private String suggestionTime;
  private String record;
  private String recordTime;
  private String status;


}

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
@TableName("tab_article")
public class TabArticle {
  private static final long serialVersionUID = -1;
  @TableId
  private String articleId;
  private String articleTitle;
  private String articleContent;
  private String articleTime;
}

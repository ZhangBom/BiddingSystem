package com.zhangbo.until;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @title: PageBean 分页查询数据实体类
 * @projectname: mavenProject
 * @description: idd_annotation
 * @author： 张波
 * @date： 2022/12/04 下午 4:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackPage<T> {
    private static final long serialVersionUID=1L;
    /**
     * 总页数
     */
    private long totalPage;

    /**
     * 当前页数
     */
    private long currentPage;
    /**
     * 总数
     */
    private long totalNum;

    /**
     * 内容
     */
    private List<T> contentList;
}


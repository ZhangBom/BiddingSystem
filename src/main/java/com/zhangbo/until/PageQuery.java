package com.zhangbo.until;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 搜索工具类
 *  用于接收多个页面的的不同查询条件
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageQuery {
    private Integer currentPage;//当前页
    private Integer limit;//条数
    private String title;//条件
    private String conditions;//按什么搜
    private String sort;  //排序
    private String purchaseType;  //采购类别
    private String purchaseTenderMethod; //招标方式
    private String vendortype;
    private String vendorlevel;
}

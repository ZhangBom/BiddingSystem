package com.zhangbo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInfo {
    String name;
    String avatar;
    String usertype;
    String userphone;
    String useremail;
    List roles;

}

package com.zhangbo.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("tab_user")
public class User implements Serializable {
    private static final long serialVersionUID = -1;
    @TableId
    private String userId;
    private String userName;
    private String userPassword;
    private String userPhone;
    private String userType;
    private String userEmail;
    private String userCompanyName;
    private String userStatus;
    private String userContactName;
    private String userImage;
    private String userRegisterTime;
}

package com.zhangbo.until;
/**
 * @author Administrator
 */
public enum Status {
    /**
     * 消息提示码
     */
    SUCCESS(200, "请求数据成功"),
    FAIL(0, "请求数据失败"),
    SUBMIT_SUCCESS(200,"提交成功"),
    SUBMIT_FAIL(0,"提交失败"),
    OPERATION_SUCCESS(200,"操作成功"),
    //login
    LOGIN_SUCCESS(200, "登录成功"),
    LOGIN_FAIL_NOT_EXIST(0,"用户不存在"),
    LOGIN_FAIL(0, "密码错误"),
    //register
    REGISTER_SUCCESS(200, "注册成功"),
    REGISTER_FAIL(0, "注册失败"),
    REGISTER_FAIL_HAS(0, "该手机号已被注册"),
    REGISTER_FAIL_NAME(0, "该用户名已被注册"),
    REGISTER_FAIL_EMAIL_HAS(0, "该邮箱已被注册"),
    ADD_INFO_SUCCESS(200, "添加信息成功"),
    ADD_INFO_FAIL_ERROR(0, "添加信息失败"),

    //modifyINFO
    MODIFY_PASSWORD_SUCCESS(200,"修改密码成功"),
    MODIFY_OLD_PASSWORD_ERROR(0,"原密码错误"),
    MODIFY_PASSWORD_FAIL(0,"修改失败"),
    MODIFY_INFO_SUCCESS(200,"修改信息成功"),
    MODIFY_INFO_FAIL(0,"修改信息失败"),
    MODIFY_HEAD_SUCCESS(200,"修改头像成功"),
    MODIFY_HEAD_FAIL(0,"修改头像失败"),
    //goods
    RELEASE_SUCCESS(200,"物品信息发表成功"),
    RELEASE_FALL(0,"发布失败"),
    DELETE_SUCCESS(200,"删除成功"),
    DELETE_FAIL(0,"删除失败"),
    //collection
    COLLECTION_SUCCESS(200,"收藏成功"),
    COLLECTION_FAIL(0,"收藏失败"),
    CANCEL_COLLECTION_SUCCESS(200,"已取消收藏"),
    CANCEL_COLLECTION_FAIL(0,"取消失败"),
    //uploadPicture
    UPLOAD_PICTURE_SUCCESS(200,"上传图片成功"),
    UPLOAD_PICTURE_FAIL(0,"上传图片失败"),
    USER_DOEST_NOT(0,"用户不存在"),
    //server
    SERVER_FAIL(0, "服务器出小差了"),
    LOW_AUTHORITY(50,"权限验证失败，请重新登录"),
    USER_STATUS_FAIL(0,"用户邮箱未激活"),
    USER_ACTIVE_SUCCES(200, "激活成功"),
    USER_ACTIVE_ERROR(0, "该邮箱已经激活过了"),
    UPDATE_SUCCESS(200, "修改成功"),
    UPDATE_FAIL(0, "修改失败"),
    //admin
    ADMIN_REGISTER_SUCCESS(0,"管理员注册成功"),
    ADMIN_REGISTER_FAIL(0,"管理员注册失败"),
    ADMIN_REGISTER_FAIL_HAS(0,"该登录名已存在"),
    LOW_POWER_ADMIN(50,"管理员权限不足"),
    //check
    CHECKED_SUCCESS(200,"验证码正确"),
    CHECKED_FAIL(0,"验证码发送失败"),
    CHECKED_ERROR(0,"验证码错误"),
    CHECKED_TIMEOUT(0,"验证码超时"),
    CHECKED_HAS(200,"验证码已发送"),
    CHECKED_FAIL_ERROR(0,"验证码错误"),
    //查询
    STATUS(200,"查询成功"),
    STATUS_ERROR(0,"查询失败"),
    DELETE_INFO_SUCCESS(200,"删除成功"),
    DELETE_CATEGORY_INFO_ERROR(0,"删除失败！该分类还有线路"),
    DELETE_SELLER_INFO_ERROR(0,"删除失败！该商家还有经营线路"),
    INSERT_INFO_SUCCESS(200,"添加成功"),
    INSERT_INFO_FAIL_ERROR(0,"添加失败"),
    UNAUTHORIZED_ACCESS(403,"未授权访问"),
    LOGOUT_SUCCESS(200,"退出成功");
//    Unauthorized access
    public final int statusCode;
    public final String statusMsg;

    Status(int code, String msg) {
        this.statusCode = code;
        this.statusMsg = msg;
    }
}
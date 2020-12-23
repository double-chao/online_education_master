package com.lcc.servicebase.exceptionhandler;

/***
 * 错误码和错误信息定义类
 * 1. 错误码定义规则为5位数字
 * 2. 前两位表示业务场景，最后三位表示错误码。例如：100001。10:通用 001:系统未知异常
 * 3. 维护错误码后需要维护错误描述，将他们定义为枚举形式
 * 错误码列表：
 *  10: 通用
 *      001：参数格式校验
 *      002：发送短信验证码频率过快
 *  11: 课程
 *  12: 文章
 *  13：课程科目
 *  14: 视频
 *  15: 用户
 *
 *
 */
public enum CodeEnum {
    UNKNOWN_EXCEPTION(20002, "系统未知异常"),
    OPERATE_EXCEPTION(20003, "操作异常"),
    DATA_CHECK_EXCEPTION(20004, "数据校验异常"),

    GET_REDISSON_LOCK(10001, "redisson获取锁失败"),
    CREATED_WEIXIN_CODE(10002, "创建微信二维码失败"),
    UPLOAD_FILE_FAILED_EXCEPTION(10003, "上传文件失败"),

    COURSE_IS_BUY_FAILED(11001, "查询课程购买失败"),
    COURSE_NOT_EXITS(11002, "课程不存在"),
    COURSE_INFO_NOT_EXITS(11003, "课程信息不存在"),
    DELETED_COURSE_FAILED(11004, "删除课程失败"),
    INSERT_COURSE_FAILED(11004, "添加课程失败"),
    UPDATE_COURSE_FAILED(11004, "修改课程失败"),

    ARTICLE_CATEGORY_EXITS(12001, "文章分类已存在"),

    SUBJECT_EXCEL_FILE_NULL(13001, "课程科目文件数据为空"),
    SELECT_CHAPTER_NOT_EXITS(13002, "所选课程章节不存在"),
    DELETED_CHAPTER_FAILED(13003, "删除课程章节失败"),

    DELETED_VIDEO_FAILED(14001, "删除视频失败"),
    GET_VIDEO_PROOF_FAILED(14002, "获取视频凭证失败"),
    UPLOAD_VIDEO_FAILED(14003, "上传视频失败"),

    LOGIN_EXPIRED_EXCEPTION(15001, "登录已过期"),
    USER_NO_LOGIN_EXCEPTION(15002, "用户未登录"),
    GET_USER_INFO_FAILED_EXCEPTION(15003, "获取用户数据失败"),
    USERNAME_OR_PASSWORD_NOT_NULL_EXCEPTION(15004, "账号或密码不能为空"),
    PHONE_NOT_REGISTER_EXCEPTION(15005, "手机号未注册"),
    PASSWORD_ERROR(15006, "密码错误"),
    USER_FORBID(15007, "该用户被禁用"),
    PHONE_REGISTER(15008, "手机号已注册"),
    ACCOUNT_PASSWORD_NICKNAME_CODE_NOT_NULL(15009, "账号、密码、昵称、验证码不能为空"),
    PHONE_CODE_ERROR_EXCEPTION(15010, "手机验证码错误"),
    LOGIN_FAILED(15011, "登录失败");

    private int code;
    private String msg;

    CodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

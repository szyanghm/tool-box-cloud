package com.tool.box.enums;

import com.tool.box.common.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公共返回枚举
 *
 * @Author v_haimiyang
 * @Date 2023/6/28 14:45
 * @Version 1.0
 */
@Getter
@AllArgsConstructor
public enum SystemCodeEnum implements ErrorType {


    OK(2000, "处理成功"),

    SYSTEM_ERROR(-1, "系统异常"),
    OPERATE_ERROR(5500, "操作失败"),

    CONTENT_TYPE_ERROR(40001, "Content-Type设置错误"),
    REQUEST_METHOD_ERROR(40002, "请求方式错误"),
    REQUEST_PARAMETER_ERROR(40003, "请求参数错误"),
    TASK_START_EXCEPTION(6000, "定时任务启动异常"),

    TASK_CREATE_EXCEPTION(6001, "创建定时任务失败"),
    TASK_DEL_EXCEPTION(6002, "删除定时任务失败"),
    TASK_PARAMETER_EXCEPTION(6003, "定时任务参数异常"),
    TASK_REFRESH_EXCEPTION(6004, "定时任务刷新异常"),
    TASK_CRON_ERROR(6005, "定时任务表达式错误"),
    TASK_FEIGN_SERVICE_FAIL(6006, "远程调用service服务失败，定时任务初始化调度器失败"),
    TASK_NOT_ENABLED(6007, "服务，关闭定时任务服务"),
    SYSTEM_BUSY(100001, "系统繁忙,请稍候再试"),
    SYSTEM_BUSY_RESUBMIT(100002, "系统繁忙,请勿重复提交"),

    FEIGN_DECODE_ERROR(110401, "feign远程调用异常"),

    GATEWAY_NOT_FOUND_SERVICE(110404, "服务未找到"),
    GATEWAY_ERROR(110500, "网关异常"),
    GATEWAY_CONNECT_TIME_OUT(110002, "网关超时"),

    ARGUMENT_NOT_VALID(120000, "请求参数校验不通过"),
    INVALID_TOKEN(120001, "无效token"),

    TOKEN_EXCEPTION(120002, "token 异常 认证失败"),

    TOKEN_EXPIRED_EXCEPTION(120003, "jwt token 过期"),

    DUPLICATE_PRIMARY_KEY(130000, "唯一键冲突"),

    USER_CHECK_FAILED(600001, "user check failed"),

    USER_ALREADY_EXISTS(600002, "用户已存在"),
    USER_DOES_NOT_EXIST(600003, "用户不存在"),
    ACCOUNT_OR_PASSWORD_ERROR(600004, "账号或者密码不正确!"),
    PASSWORD_ERROR(600005, "密码错误,请重新输入!"),

    PASSWORD_ENCRYPT_FAILED(600006, "密码加密失败!"),

    PASSWORD_DECRYPT_FAILED(600007, "密码解密失败!"),

    USER_LOCK_ING(600008, "user is lock...."),

    USER_NOT_PERMISSIONS(600009, "用户无权限"),

    INSUFFICIENT_ACCESS_TO_RESOURCES(600010, "访问资源权限不足!"),

    SECURITY_MANAGER_NOT_CONFIGURED(600011, "SecurityManager property must be set."),

    WEB_SECURITY_MANAGER_NOT_IMPLEMENTED(600012, "The security manager does not implement the WebSecurityManager interface."),

    USER_LOGIN_EXPIRED(600013, "登录已过期，请重新登录"),

    LOGIN_OUT_FAIL(600020, "退出登录失败，token为空"),

    USER_EXISTS(300001, "user Already exists"),

    NETTY_PING_MESSAGE(100015, "netty ping message"),

    RECEIVE_PONG_MESSAGE_ADDRESS(100016, "receive pong message, address,获取Channel的远程IP地址"),

    UPLOADING_FILES_CANNOT_EXCEED_5M(100060, "文件大小不能超过5M"),

    UPLOAD_NOT_FOUND(100061, "上传的文件对象不存在..."),

    FILE_TYPE_NOT(100062, "文件类型未定义不能上传"),

    IMAGE_TO_BASE64_EXCEPTION(100063, "image转base64异常"),

    REMOVE_BUCKET_INFO_FAIL(100064, "根据存储桶删除信息失败"),

    GET_BY_NAME_BUCKET_INFO_FAIL(100065, "根据存储桶文件名获取信息失败"),

    CREATE_BUCKET_FAIL(100066, "创建存储桶失败"),

    CHECK_BUCKET_IT_EXIST_FAIL(100067,"检查存储桶是否存在失败"),

    FILE_UPLOAD_FAILED(100068,"文件上传失败,请重新上传或联系管理员"),

    MINIO_GETPRESIGNEDOBJECTURL_IS_FAIL(100069,"获取文件外链地址失败"),

    MINIO_DOWN_LOAD_FILE_EXCEPTION(100070,"文件下载异常"),
    MINIO_REMOVE_FILES_FAIL(100071,"批量删除文件失败"),
    MINIO_REMOVE_FILE_FAIL(100072,"删除文件失败"),

    NORMAL_MESSAGE(100086, "Normal message"),

    FLOW_DEPLOY_ERROR(5001, "流程部署失败!"),
    FLOW_START_ERROR(5002, "流程启动失败!"),
    FLOW_TASK_COMPLETE_ERROR(5002, "任务完成失败！"),

    QUERY_ERROR(8001, "查询失败!"),
    DELETE_ERROR(8002, "删除失败!"),
    UPDATE_ERROR(8003, "更新失败!"),


    ;


    /**
     * 错误类型码
     */
    private final Integer code;
    /**
     * 错误类型描述信息
     */
    private final String msg;

}

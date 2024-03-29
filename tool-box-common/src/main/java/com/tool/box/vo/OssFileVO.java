package com.tool.box.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tool.box.common.Contents;
import com.tool.box.utils.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * @Author v_haimiyang
 * @Date 2024/3/21 16:02
 * @Version 1.0
 */
@Data
public class OssFileVO {

    /**
     * 文件地址
     */
    private String fileKey;

    /**
     * 文件地址
     */
    private String filePath;
    /**
     * 域名地址
     */
    private String domain;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 原始文件名
     */
    private String originalName;
    /**
     * 文件hash值
     */
    public String hash;
    /**
     * 文件大小
     */
    private long fileSize;
    /**
     * 文件上传时间
     */
    @JsonFormat(timezone = Contents.GMT8, pattern = DateUtils.DEFAULT_DATE_PATTERN)
    private Date putTime;
    /**
     * 文件contentType
     */
    private String contentType;

}

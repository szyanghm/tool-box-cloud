package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 附件表
 *
 * @author v_haimiyang
 * @since 2024-03-25
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
@TableName("t_oss_file")
@ApiModel(value = "OssFile对象", description = "附件表")
public class OssFile extends BaseModel<OssFile> {

    @ApiModelProperty("业务附件key")
    private String fileKey;

    @ApiModelProperty("业务附件类型")
    private String fileType;

    @ApiModelProperty("文件名")
    private String fileName;

    @ApiModelProperty("原始文件名")
    private String originalName;

    @ApiModelProperty("文件地址")
    private String filePath;

    @ApiModelProperty("域名地址")
    private String domain;

    @ApiModelProperty("文件大小")
    private long fileSize;

    @ApiModelProperty("文件类型")
    private String contentType;

    @ApiModelProperty("文件hash值")
    private String hash;

}

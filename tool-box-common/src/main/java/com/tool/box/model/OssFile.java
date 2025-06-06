package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "OssFile对象", description = "附件表")
public class OssFile extends BaseModel<OssFile> {

    @Schema(description = "业务附件key")
    private String fileKey;

    @Schema(description = "附件地址")
    private String fileUrl;

    @Schema(description = "业务附件类型")
    private String fileType;

    @Schema(description = "文件名")
    private String fileName;

    @Schema(description = "原始文件名")
    private String originalName;

    @Schema(description = "域名地址")
    private String domain;

    @Schema(description = "文件大小")
    private long fileSize;

    @Schema(description = "文件类型")
    private String contentType;

    @Schema(description = "文件hash值")
    private String hash;

}

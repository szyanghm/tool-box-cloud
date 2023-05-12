package com.tool.box.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author v_haimiyang
 * @Date 2023/4/20 16:49
 * @Version 1.0
 */
@Getter
@Setter
@ToString
public class BaseModel<T extends Model<T>> extends Model<T> {

    /**
     * 主键ID
     */
    //@ApiModelProperty(value = "主键")
    @TableId(type = IdType.AUTO)
    private Long id;

    //@ApiModelProperty(value = "创建人", hidden = true)
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    //@ApiModelProperty(value = "修改人", hidden = true)
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    //@ApiModelProperty(value = "创建时间", hidden = true)
    //@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    //@ApiModelProperty(value = "修改时间")
    //@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 是否删除（0：未删除；1：已删除）
     */
    @TableField(value = "is_delete", fill = FieldFill.INSERT)
    //@ApiModelProperty("是否删除（0：未删除；1：已删除）")
    private Integer isDelete;

//    @Override
//    protected Serializable pkVal() {
//        return this.id;
//    }
}

package com.mt.common.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by wangfan on 2020-03-14 11:29:04
 */
@ApiModel(description = "字典项")
@TableName("sys_dictionary_data")
public class DictionaryData implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典项id")
    @TableId(value = "dict_data_id", type = IdType.AUTO)
    private Long dictDataId;

    @ApiModelProperty(value = "字典id")
    private Long dictId;

    @ApiModelProperty(value = "字典项标识")
    private String dictDataCode;

    @ApiModelProperty(value = "字典项名称")
    private String dictDataName;

    @ApiModelProperty(value = "排序号")
    private Long sortNumber;

    @ApiModelProperty(value = "备注")
    private String comments;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否删除,0否,1是")
    @TableLogic
    private Long deleted;

    @ApiModelProperty(value = "字典代码")
    @TableField(exist = false)
    private String dictCode;

    @ApiModelProperty(value = "字典名称")
    @TableField(exist = false)
    private String dictName;

    public Long getDictDataId() {
        return dictDataId;
    }

    public void setDictDataId(Long dictDataId) {
        this.dictDataId = dictDataId;
    }

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public String getDictDataCode() {
        return dictDataCode;
    }

    public void setDictDataCode(String dictDataCode) {
        this.dictDataCode = dictDataCode;
    }

    public String getDictDataName() {
        return dictDataName;
    }

    public void setDictDataName(String dictDataName) {
        this.dictDataName = dictDataName;
    }

    public Long getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Long sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getDeleted() {
        return deleted;
    }

    public void setDeleted(Long deleted) {
        this.deleted = deleted;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    @Override
    public String toString() {
        return "DictionaryData{" +
                ", dictDataId=" + dictDataId +
                ", dictId=" + dictId +
                ", dictDataCode=" + dictDataCode +
                ", dictDataName=" + dictDataName +
                ", sortNumber=" + sortNumber +
                ", comments=" + comments +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                ", dictCode=" + dictCode +
                ", dictName=" + dictName +
                "}";
    }

}

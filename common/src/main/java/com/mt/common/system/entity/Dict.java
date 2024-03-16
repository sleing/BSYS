package com.mt.common.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 王某某
 */
@ApiModel(description = "字典")
@TableName("sys_dict")
public class Dict implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典id")
    @TableId(value = "dict_id", type = IdType.AUTO)
    private Integer dictId;

    @ApiModelProperty(value = "父级id 0是顶级")
    private Integer parentId;

    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "排序号")
    private Integer sortNumber;

    @ApiModelProperty(value = "备注")
    private String comments;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty("子菜单")
    @TableField(exist = false)
    private List<Dict> children;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getDictId() {
        return dictId;
    }

    public void setDictId(Integer dictId) {
        this.dictId = dictId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
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

    public List<Dict> getChildren() {
        return children;
    }

    public void setChildren(List<Dict> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Dict{" +
                "dictId=" + dictId +
                ", parentId=" + parentId +
                ", dictName='" + dictName + '\'' +
                ", sortNumber=" + sortNumber +
                ", comments='" + comments + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", children=" + children +
                '}';
    }
}

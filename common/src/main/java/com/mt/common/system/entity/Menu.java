package com.mt.common.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.List;

/**
 * Created by AutoGenerator on 2018-12-24 16:10
 */
@ApiModel(description = "菜单")
@TableName("sys_menu")
public class Menu implements GrantedAuthority {
    public static final Long TYPE_MENU = 0L;  // 菜单类型
    public static final Long TYPE_BTN = 1L;  // 按钮类型

    @ApiModelProperty("菜单id")
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

    @ApiModelProperty("上级id,0是顶级")
    private Long parentId;

    @ApiModelProperty("菜单名称")
    private String title;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("图标颜色")
    private String color;

    @ApiModelProperty("菜单路由关键字")
    private String path;

    @ApiModelProperty("菜单组件地址")
    private String component;

    @ApiModelProperty("打开位置")
    private String target;

    @ApiModelProperty("嵌套路由左侧选中")
    private String uid;

    @ApiModelProperty("是否隐藏,0否,1是(仅注册路由不显示左侧菜单)")
    private Long hide;

    @ApiModelProperty("排序号")
    private Long sortNumber;

    @ApiModelProperty("权限标识")
    private String authority;

    @ApiModelProperty("菜单类型,0菜单,1按钮")
    private Long menuType;


    @ApiModelProperty("打开方式:0 组件,1 内链,2 外链")
    private Long openType;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("是否删除,0否,1是")
    @TableLogic
    private Long deleted;

    @ApiModelProperty("上级菜单名称")
    @TableField(exist = false)
    private String parentTitle;

    @ApiModelProperty("子菜单")
    @TableField(exist = false)
    private List<Menu> children;

    @ApiModelProperty("权限树回显选中状态,0未选中,1选中")
    @TableField(exist = false)
    private Boolean checked;

    @ApiModelProperty("权限树控制展开")
    @TableField(exist = false)
    private Boolean open;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Long getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Long sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Long getHide() {
        return hide;
    }

    public void setHide(Long hide) {
        this.hide = hide;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Long getMenuType() {
        return menuType;
    }

    public void setMenuType(Long menuType) {
        this.menuType = menuType;
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

    public String getParentTitle() {
        return parentTitle;
    }

    public void setParentTitle(String parentTitle) {
        this.parentTitle = parentTitle;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getOpenType() {
        return openType;
    }

    public void setOpenType(Long openType) {
        if(openType == null)
        {
            openType = 0L ;
        }
        this.openType = openType;
    }

    @Override
    public String toString() {
        return "Menu{" +
                ", menuId=" + menuId +
                ", parentId=" + parentId +
                ", title=" + title +
                ", path=" + path +
                ", component=" + component +
                ", icon=" + icon +
                ", sortNumber=" + sortNumber +
                ", target=" + target +
                ", uid=" + uid +
                ", hide=" + hide +
                ", authority=" + authority +
                ", menuType=" + menuType +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                ", parentTitle=" + parentTitle +
                "}";
    }

}

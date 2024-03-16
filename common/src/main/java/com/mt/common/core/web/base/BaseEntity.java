package com.mt.common.core.web.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.mt.common.core.annotation.DColumn;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class BaseEntity implements Serializable {
    /**
     * 唯一标识
     */
    @DColumn(index = 0, label = "标识", comment = "唯一标识", pageShow = false)
    @Column(name = "eid", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(value = "eid", type = IdType.AUTO)
    @Id
    protected Long Eid;


    @DColumn(index = 1, label = "名称", comment = "名称")
    @Column(name = "name", nullable = true, unique = false)
    protected String name = "";

    @DColumn(index = 10000, label = "状态", comment = "状态")
    @Column(name = "status", nullable = true, unique = false)
    protected String status = "";

    @DColumn(index = 10001, label = "备注", comment = "备注")
    @Column(name = "remark", length = 2000, nullable = true)
    protected String remark;

    /**
     * 创建人
     */
    @DColumn(index = 10001, label = "创建人ID", comment = "创建人ID", pageShow = false)
    @Column(name = "creator_id", nullable = true)
    protected Long creatorId;

    @DColumn(index = 1, label = "创建人", comment = "创建人")
    @Column(name = "creator_name", nullable = true, unique = false)
    private String creatorName = "";

    /**
     * 创建时间
     */
    @DColumn(index = 10003, label = "创建时间", comment = "创建时间", pageShow = false)
    @Column(name = "create_datetime", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, nullable = true)
    @JsonSerialize(using = DateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date createDatetime;

    public Long getEid() {
        return Eid;
    }

    public void setEid(Long eid) {
        Eid = eid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static String FIELD_ID = "Eid";
    public static String FIELD_NAME = "name";
    public static String FIELD_STATUS = "status";
    public static String FIELD_REMARK = "remark";
    public static String FIELD_CREATORID = "creatorId";
    public static String FIELD_CREATORNAME = "creatorName";
    public static String FIELD_CREATEDATETIME = "createDatetime";

}






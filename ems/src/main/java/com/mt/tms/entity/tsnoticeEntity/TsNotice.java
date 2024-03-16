package com.mt.tms.entity.tsnoticeEntity;

import javax.persistence.*;
import java.io.Serializable;
import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;



import java.util.Date;
import com.mt.tms.entity.tsdepartmentEntity.TsDepartment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@DEntity(label="通知管理",comment="",moduleLabel="通知管理")
@Entity(name="tms_ts_notice")
@Table(name = "tms_ts_notice" , indexes = { @Index(name = "index_notice_department_id", columnList = "notice_department_id"),@Index(name = "index_audit_statu", columnList = "audit_statu")  })
@ApiModel(description = "通知管理:")
public class TsNotice extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="通知主题",comment="单位名称",component="文本",where=false)
	@ApiModelProperty(value = "通知主题:单位名称")
	@Column(name="title",length=255,nullable=false,unique=false)
	private String title;

	@DColumn(index=4,label="通知时间",comment="",component="日期选择",where=false)
	@ApiModelProperty(value = "通知时间:")
	@Column(name="notice_date",length=255,nullable=true,unique=false)
	@JsonSerialize(using = DateSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date noticeDate;

	@DColumn(index=5,label="通知部门",foreignEntity="TsDepartment",comment="",component="对象选择",where=false)
	@ApiModelProperty(value = "通知部门:")
	@Column(name="notice_department_id",length=255,nullable=true,unique=false)
	private Long noticeDepartmentId;

	@Transient
	private TsDepartment noticeDepartment;

	@Transient
	@ApiModelProperty(value = "通知部门名称:")
	@DColumn(index=5,label="通知部门",foreignEntity="TsDepartment",comment="")
	private String noticeDepartmentName;

	@DColumn(index=6,label="通知内容",comment="",component="文本",where=false)
	@ApiModelProperty(value = "通知内容:")
	@Column(name="content",length=255,nullable=true,unique=false)
	private String content;

	@DColumn(index=7,label="审核状态",codeTable="审核状态",codeTableOptions="未审核,审核通过,审核未通过",comment="",component="字典下拉单选",where=false)
	@ApiModelProperty(value = "审核状态:")
	@Column(name="audit_statu",length=255,nullable=true,unique=false)
	private String auditStatu;


	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getNoticeDate() {
		return this.noticeDate;
	}

	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}

	public Long getNoticeDepartmentId() {
		return this.noticeDepartmentId;
	}

	public void setNoticeDepartmentId(Long noticeDepartmentId) {
		this.noticeDepartmentId = noticeDepartmentId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuditStatu() {
		return this.auditStatu;
	}

	public void setAuditStatu(String auditStatu) {
		this.auditStatu = auditStatu;
	}

	public TsDepartment getNoticeDepartment() {
		return this.noticeDepartment;
	}

	public void setNoticeDepartment(TsDepartment noticeDepartment) {
		if(noticeDepartment == null){
		}
		else
		{
		this.noticeDepartmentId = noticeDepartment.getEid();
		this.noticeDepartment = noticeDepartment;		
		}
}

	public String getNoticeDepartmentName() {
		return this.noticeDepartmentName;
	}

	public void setNoticeDepartmentName(String noticeDepartmentName) {
		this.noticeDepartmentName = noticeDepartmentName;
	}



}

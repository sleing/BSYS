package com.mt.common.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mt.common.core.annotation.DColumn;
import com.mt.common.core.annotation.DEntity;
import com.mt.common.core.web.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;


@DEntity(label="系统参数",comment="",moduleLabel="系统参数")
@Entity(name="sys_config_data")
@TableName("sys_config_data")
@Table(name = "sys_config_data" , indexes = { @Index(name = "index_type", columnList = "type")  })
@ApiModel(description = "系统参数:")
public class Config extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@DColumn(index=3,label="参数名称",comment="参数名称")
	@ApiModelProperty(value = "参数名称:参数名称")
	@Column(name="name",length=200,nullable=true,unique=true)
	private String name;

	@DColumn(index=4,label="参数值",comment="参数值")
	@ApiModelProperty(value = "参数值:参数值")
	@Column(name="value",length=200,nullable=false,unique=false)
	private String value;

	@DColumn(index=5,label="参数说明",comment="参数说明")
	@ApiModelProperty(value = "参数说明:参数说明")
	@Column(name="description",length=200,nullable=false,unique=false)
	private String description;

	@DColumn(index=6,label="参数选项",comment="参数选项")
	@ApiModelProperty(value = "参数选项:参数选项")
	@Column(name="options",length=200,nullable=false,unique=false)
	private String options;

	@DColumn(index=7,label="类别",codeTable="参数类别",codeTableOptions="流程配置,通用配置,生产配置，实验室配置，过磅配置，备品物资，系统服务，其他设置",comment="类别")
	@ApiModelProperty(value = "类别:类别")
	@Column(name="type",length=255,nullable=false,unique=false)
	private String type;

	@DColumn(index=8,label="是否前台参数",comment="是否前台参数")
	@ApiModelProperty(value = "是否前台参数:是否前台参数")
	@Column(name="is_front_config",length=255,nullable=false,unique=false)
	private Integer isFrontConfig;

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOptions() {
		return this.options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer  getIsFrontConfig() {
		return this.isFrontConfig;
	}

	public void setIsFrontConfig(Integer isFrontConfig) {
		this.isFrontConfig = isFrontConfig;
	}

	@Override
	public String toString() {
		return "Config{" +
				"name='" + name + '\'' +
				", value='" + value + '\'' +
				", description='" + description + '\'' +
				", options='" + options + '\'' +
				", type='" + type + '\'' +
				", isFrontConfig=" + isFrontConfig +
				'}';
	}
}

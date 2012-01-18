package com.orm;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.annotations.GenericGenerator;

/**
 * 统一定义id的entity基类.
 * 
 * @author san
 */
@SuppressWarnings("unused")
@MappedSuperclass
public class BaseEntity implements Serializable{

	private static final long serialVersionUID = 3980743350790156736L;
	
	@Id
	@Column(length = 32)
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	@GeneratedValue(generator = "hibernate-uuid")
	protected String id;
	
	@Version
	private Long version;
	
	@Column(insertable=true,updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date insertDate=new Date();
	
	@Column(insertable=false,updatable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate=new Date();
	
	private String createBy;
	
	private String updateBy;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
}

package san.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.orm.BaseEntity;

/**
 * 权限.
 * @author sanshang
 */
@Entity
@Table(name = "start_authority_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Authority extends BaseEntity {

	/**
	 * SpringSecurity中默认的角色/授权名前缀.
	 */
	private static final long serialVersionUID = 6095359542417102553L;

	public static final String AUTHORITY_PREFIX = "ROLE_";

	@NotNull
	@Size(min = 1, max = 25,message="Size between 1 and 25")
	private String name;
	
	private String remark;
	private String path;

	public Authority() {
	}

	public Authority(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	public String getPrefixedName() {
		return AUTHORITY_PREFIX + name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

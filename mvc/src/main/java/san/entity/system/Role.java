package san.entity.system;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.collect.Lists;
import com.orm.BaseEntity;
import com.utils.CollectionUtils;

/**
 * 角色.
 * @author sanshang
 */
@Entity
@Table(name = "start_role_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends BaseEntity {

	private static final long serialVersionUID = 5395931495850318552L;

	@NotEmpty
	private String name;
	@ManyToMany
	@JoinTable(name = "start_role_authority", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "authority_id") })
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<Authority> authorityList = Lists.newArrayList();

	public Role() {

	}

	public Role(String id, String name) {
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

	public List<Authority> getAuthorityList() {
		return authorityList;
	}

	public void setAuthorityList(List<Authority> authorityList) {
		this.authorityList = authorityList;
	}

	@Transient
	public String getAuthNames() throws Exception {
		return CollectionUtils.fetchPropertyToString(authorityList, "name",
				", ");
	}

	@Transient
	@SuppressWarnings("unchecked")
	public List<Serializable> getAuthIds() throws Exception {
		return CollectionUtils.fetchPropertyToList(authorityList, "id");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

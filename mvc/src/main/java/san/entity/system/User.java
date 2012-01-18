package san.entity.system;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.collect.Lists;
import com.orm.BaseEntity;
import com.utils.CollectionUtils;

/**
 * 用户.
 * @author sanshang
 */
@Entity
@Table(name = "start_user_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends BaseEntity {
	private static final long serialVersionUID = -3424444444831221520L;
	@NotEmpty(message="Login name must not be null.")
	private String loginName;
	
	@Size(min=6, message="The length must greater than 6.")
	private String password;
	@NotEmpty(message="Name must not be null.")
	private String name;
	
	@NotEmpty(message="Emial must not be null.")
	@Email
	private String email;
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "start_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<Role> roleList = Lists.newArrayList();//有序的关联对象集合

	//字段非空且唯一, 用于提醒Entity使用者及生成DDL.
	@Column(nullable = false, unique = true)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 * @throws Exception 
	 */
	//非持久化属性.
	@Transient
	public String getRoleNames() throws Exception {
		return CollectionUtils.fetchPropertyToString(roleList, "name", ", ");
	}

	/**
	 * 用户拥有的角色id字符串, 多个角色id用','分隔.
	 * @throws Exception 
	 */
	//非持久化属性.
	@Transient
	@SuppressWarnings("unchecked")
	public List<Serializable> getRoleIds() throws Exception {
		return  CollectionUtils.fetchPropertyToList(roleList, "id");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
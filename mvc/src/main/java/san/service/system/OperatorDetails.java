package san.service.system;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import san.entity.system.Role;

/**
 * 扩展SpringSecurity的WebAuthenticationDetails类, 增加登录时间属性和角色属性.
 * 
 * @author sanshang
 */
public class OperatorDetails extends User {
	private static final long serialVersionUID = 1919464185097508773L;

	private String loginTime;
	
	private List<Role> roleList;

	public OperatorDetails(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, Collection<GrantedAuthority> authorities)
			throws IllegalArgumentException {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

    public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}


	public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}

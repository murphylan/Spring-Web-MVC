package san.service.system;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import san.entity.system.Authority;
import san.entity.system.Role;
import san.entity.system.User;

import com.google.common.collect.Sets;

/**
 * 实现SpringSecurity的UserDetailsService接口,实现获取用户Detail信息的回调函数.
 * 
 * @author sanshang
 */
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserService userService;

	/**
	 * 获取用户Details信息的回调函数.
	 */
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

		User user = userService.findUserByLoginName(username);
		if (user == null)
			throw new UsernameNotFoundException("用户" + username + " 不存在");

		Set<GrantedAuthority> grantedAuths = obtainGrantedAuthorities(user);

		// -- 以下属性, 暂时全部设为true. --//
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		OperatorDetails userDetails = new OperatorDetails(user.getLoginName(),
				user.getPassword(), enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, grantedAuths);
		// 加入登录时间信息和用户角色
		userDetails.setLoginTime(DateFormatUtils.format(new Date(),
				"yyyy-MM-dd HH:mm:ss"));
		userDetails.setRoleList(user.getRoleList());
		return userDetails;
	}

	/**
	 * 获得用户所有角色的权限集合.
	 */
	private Set<GrantedAuthority> obtainGrantedAuthorities(User user) {
		Set<GrantedAuthority> authSet = Sets.newHashSet();
		for (Role role : user.getRoleList()) {
			for (Authority authority : role.getAuthorityList()) {
				authSet.add(new GrantedAuthorityImpl(authority
						.getPrefixedName()));
			}
		}
		return authSet;
	}
}

package san.service.system;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import san.dao.system.AuthorityDao;
import san.dao.system.RoleDao;
import san.dao.system.UserDao;
import san.entity.system.Authority;
import san.entity.system.Role;
import san.entity.system.User;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.utils.encode.EncodeUtils;
import com.web.page.Page;
import com.web.spring.ServiceException;
import com.web.spring.SpringSecurityUtils;

/**
 * 安全相关实体的管理类, 包括用户,角色,资源与授权类.
 * 
 * @author sanshang
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private AuthorityDao authorityDao;

	// -- User Manager --//
	@Transactional(readOnly = true)
	public User getUser(String id) {
		return userDao.get(id);
	}

	public void saveUser(User entity) {
		if (entity.getPassword().length() < 32) {
			String psw = EncodeUtils.getMd5PasswordEncoder(
					entity.getPassword(), entity.getLoginName());
			entity.setPassword(psw);
		}
		entity.setCreateBy(SpringSecurityUtils.getCurrentUserName());
		userDao.save(entity);
	}

	public void saveUser(User user, List ids, List<String> teams) throws Exception {
		if (user.getPassword().length() < 32 && user.getPassword().length()>0) {
			String psw = EncodeUtils.getMd5PasswordEncoder(user.getPassword(),
					user.getLoginName());
			user.setPassword(psw);
		}
		List<Role> list = roleDao.findByIds(ids);
		user.setRoleList(list);
		user.setCreateBy(SpringSecurityUtils.getCurrentUserName());
		userDao.save(user);
	}

	/**
	 * 删除用户,如果尝试删除超级管理员将抛出异常.
	 */
	public void deleteUser(String id) {
		if (isSupervisor(id)) {
			// logger.warn("操作员{}尝试删除超级管理员用户",
			// SpringSecurityUtils.getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(String id) {
		return id == "1";
	}

	/**
	 * 查询用户.
	 */
	@Transactional(readOnly = true)
	public Page<User> getAllUsers(final Page<User> page) {
		return userDao.findPage(page);
	}
	
	@Transactional(readOnly = true)
	public List<User> getAllUser() {
		return userDao.getAll();
	}

	@Transactional(readOnly = true)
	public User findUserByLoginName(String loginName) {
		return userDao.findUniqueBy("loginName", loginName);
	}

	/**
	 * 检查用户名是否唯一.
	 * 
	 * @return loginName在数据库中唯一或等于oldLoginName时返回true.
	 */
	@Transactional(readOnly = true)
	public boolean isLoginNameUnique(String newLoginName, String oldLoginName) {
		return userDao
				.isPropertyUnique("loginName", newLoginName, oldLoginName);
	}
	
	@Transactional(readOnly = true)
	public boolean isRoleNameUnique(String newRoleName, String oldRoleName) {
		return roleDao
				.isPropertyUnique("name", newRoleName, oldRoleName);
	}

	// -- Role Manager --//
	@Transactional(readOnly = true)
	public Role getRole(String id) {
		return roleDao.get(id);
	}

	@Transactional(readOnly = true)
	public List<Role> getAllRole() {
		return roleDao.getAll("id", true);
	}

	/**
	 * 查询角色.
	 */
	@Transactional(readOnly = true)
	public Page<Role> getAllRoles(final Page<Role> page) {
		return roleDao.findPage(page);
	}

	public void saveRole(Role entity) {
		roleDao.save(entity);
	}

	@SuppressWarnings("unchecked")
	public void saveRole(Role role, List ids) throws Exception {
		List<Authority> list = authorityDao.findByIds(getAuthIds(ids));
		role.setAuthorityList(list);
		roleDao.save(role);
	}

	public List<String> getAuthIds(List object) throws Exception {
		List<String> list = new ArrayList<String>();
		for (Object obj : object) {
			list.add(obj.toString());
		}
		return list;
	}
	
	public void deleteRole(String id) {
		roleDao.delete(id);
	}

	// -- Authority Manager --//
	@Transactional(readOnly = true)
	public Authority getAuthority(String id) {
		return authorityDao.get(id);
	}

	@Transactional(readOnly = true)
	public Page<Authority> getAllAuthority(final Page<Authority> page) {
		return authorityDao.findPage(page);
	}

	@Transactional(readOnly = true)
	public List<Authority> getAllAuthority() {
		return authorityDao.getAll();
	}

	@Transactional(readOnly = true)
	public Map<String, String> getAllAuthorityForMap() {
		Map<String, String> map =Maps.newLinkedHashMap();
		List<Authority> authorityList = Lists.newArrayList();
		authorityList=authorityDao.find("from Authority t order by t.name");
		if (!CollectionUtils.isEmpty(authorityList)) {
			for (Authority o : authorityList) {
				map.put(o.getId(), o.getName());
			}
		}
		return map;
	}

	@Transactional(readOnly = true)
	public Map<String, String> getAllRoleForMap() {
		Map<String, String> map = Maps.newHashMap();
		List<Role> roleList = roleDao.getAll();
		if (!CollectionUtils.isEmpty(roleList)) {
			for (Role o : roleList) {
				map.put(o.getId(), o.getName());
			}
		}
		return map;
	}
	
	public void saveAuthority(Authority entity) {
		authorityDao.save(entity);
	}

	public void deleteAuthority(String id) {
		authorityDao.delete(id);
	}
	
	public List<User> getUserList(String roleName){
		return roleDao.getUserList(roleName);
	}
	
	public boolean authorityHasUse(String id) throws  Exception{
		String sql="select count(authority_id) from start_role_authority where authority_id='"+id+"'";
		BigDecimal  count=(BigDecimal) authorityDao.getSession().createSQLQuery(sql).uniqueResult();
		if(count.intValue()==0)
		    return false;
		else
			return true;
	}
}

package san.dao.system;

import java.util.List;

import org.springframework.stereotype.Repository;

import san.entity.system.Role;
import san.entity.system.User;

import com.orm.HibernateDao;

/**
 * 角色对象的泛型DAO.
 * 
 * @author sanshang
 */
@Repository
public class RoleDao extends HibernateDao<Role, String> {

	private static final String QUERY_USER_BY_ROLEID = "select u from User u left join u.roleList r where r.id=?";

	private static final String QUERY_USER_BY_ROLENAME = "select u from User u left join u.roleList r where r.name=?";

	/**
	 * 重载函数,因为Role中没有建立与User的关联,因此需要以较低效率的方式进行删除User与Role的多对多中间表.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void delete(String id) {
		Role role = get(id);
		// 查询出拥有该角色的用户,并删除该用户的角色.
		List<User> users = createQuery(QUERY_USER_BY_ROLEID, role.getId())
				.list();
		for (User u : users) {
			u.getRoleList().remove(role);
		}
		super.delete(role);
	}

	@SuppressWarnings("unchecked")
	public List<User> getUserList(String roleName) {
		List<User> users = this.createQuery(QUERY_USER_BY_ROLENAME, roleName)
				.list();
		return users;
	}
}

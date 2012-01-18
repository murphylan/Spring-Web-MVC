package san.dao.system;

import org.springframework.stereotype.Repository;

import san.entity.system.User;

import com.orm.HibernateDao;

/**
 * 用户对象的泛型DAO类.
 * 
 * @author sanshang
 */
@Repository
public class UserDao extends HibernateDao<User, String> {
}

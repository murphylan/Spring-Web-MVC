package san.dao.system;

import org.springframework.stereotype.Repository;

import san.entity.system.Authority;

import com.orm.HibernateDao;

/**
 * 授权对象的泛型DAO.
 * 
 * @author sanshang
 */
@Repository
public class AuthorityDao extends HibernateDao<Authority, String> {
}

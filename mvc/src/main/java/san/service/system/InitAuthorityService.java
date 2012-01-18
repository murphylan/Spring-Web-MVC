package san.service.system;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.ServletContextResource;

import san.dao.dictionary.DictionaryDao;
import san.dao.system.AuthorityDao;
import san.dao.system.RoleDao;
import san.dao.system.UserDao;
import san.dao.tree.TreeDao;
import san.entity.dictionary.Dictionary;
import san.entity.system.Authority;
import san.entity.system.Role;
import san.entity.system.User;
import san.entity.tree.Tree;

import com.google.common.collect.Maps;
import com.utils.encode.EncodeUtils;
import com.utils.jaxb.CollectionWrapper;
import com.utils.jaxb.Jaxb2Test;
import com.utils.jaxb.XmlAuthority;
import com.utils.jaxb.XmlRole;
import com.utils.jaxb.XmlUser;

/**
 * 初始化权限.
 * 
 * @author sanshang
 */
@Service("initAuthorityService")
@Transactional
public class InitAuthorityService {
	@Autowired
	ServletContext servletContext;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private TreeDao treeDao;
	@Autowired
	private AuthorityDao authorityDao;
	@Autowired
	private DictionaryDao dictionaryDao;
	@Autowired
	private UserService userService;
	

	public void initAll() throws Exception {
		deleteAllAuthority();
		deleteAllRole();
		deleteAllUser();
		deleteAllDictionary();
		deleteAllTree();
		deleteAllArticle();
		// 初始化
		initAuthority();
		initTree();
		//initDictionary();
	}

	/*
	 * 删除所有用户
	 */
	public void deleteAllUser() throws SQLException {
		userDao.batchExecute("delete from User");
	}

	/*
	 * 删除所有角色
	 */
	public void deleteAllRole() throws SQLException {
		userDao.executeUpdateBySql("delete from start_user_role");
		userDao.batchExecute("delete from Role");
	}

	/*
	 * 删除所有资源
	 */
	public void deleteAllAuthority() throws SQLException {
		userDao.executeUpdateBySql("delete from start_role_authority");
		userDao.batchExecute("delete from Authority");
	}

	/*
	 * 删除所有字典表数据
	 */
	public void deleteAllDictionary() throws SQLException {
		userDao.batchExecute("delete from Dictionary ");
	}

	/*
	 * 删除tree
	 */
	public void deleteAllTree() throws SQLException {
		userDao.batchExecute("delete from Tree ");
	}
	
	/*
	 * 删除tree
	 */
	public void deleteAllArticle() throws SQLException {
		userDao.batchExecute("delete from Article ");
	}

	@SuppressWarnings("unchecked")
	public void initAuthority() throws Exception {
		Jaxb2Test jaxb2 = new Jaxb2Test();
		Resource resource = new ServletContextResource(servletContext,
				"/WEB-INF/classes/initAuthority.xml");
		javax.xml.transform.Source xmlSource = new javax.xml.transform.stream.StreamSource(
				resource.getInputStream());
		HttpHeaders headers = new HttpHeaders();
		Class<Object> clazz = (Class<Object>) Class
				.forName("com.utils.jaxb.CollectionWrapper");
		CollectionWrapper temp = (CollectionWrapper) jaxb2.readFromSource(
				clazz, headers, xmlSource);
		// Init authority
		for (XmlAuthority o : temp.getAuthority()) {
			//System.out.println("Init Authority" + "--->" + o.getName() + "---"
			//		+ o.getPath());
			Authority a = new Authority();
			a.setName(o.getName());
			a.setPath(o.getPath());
			authorityDao.save(a);
		}
		
		// Init role
		for(XmlRole o: temp.getRole()){
			Role r = new Role();
			r.setName(o.getName());
			List<Authority> a = authorityDao.getAll();
			r.setAuthorityList(a);
			roleDao.save(r);
		}
		
		// Init user
		for(XmlUser o:temp.getUser()){
			User u = new User();
			u.setLoginName(o.getLoginName());
			u.setName(o.getName());
			u.setPassword(o.getPassword());
			u.setEmail(o.getEmail());
			List<Role> r = roleDao.getAll();
			u.setRoleList(r);
			userService.saveUser(u);
		}
		
		
	}

	public void initTree() throws Exception {
		Tree tree = new Tree();
		tree.setName("Total");
		tree.setParentId("-1");
		tree.setType("drive");
		treeDao.save(tree);
	}
	
	public void initDictionary() throws Exception {
		Dictionary a = new Dictionary();
		a = new Dictionary();
		a.setName("wuhan");
		a.setDescription("wuhan");
		a.setType("university");
		dictionaryDao.save(a);
	}
}

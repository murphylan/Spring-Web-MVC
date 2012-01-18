package san.dao.tree;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Repository;

import san.entity.tree.Tree;

import com.orm.HibernateDao;

@Repository
public class TreeDao extends HibernateDao<Tree, String> {
	/**
	 * 自动生成TreeDao类.
	 */
	private static final long serialVersionUID = 1534234168809865691L;

	public Long findUnique(String values) throws IOException {
		String hql = "select count(id) from Tree where parentId=?";
		Long count = findUnique(hql, values);
		return count;
	}

	public List<Tree> findBy(String values) throws IOException {
		String hql = "from Tree where name like ? ";
		List<Tree> list = this.find(hql, '%' + values + '%');
		return list;
	}

	public List<Tree> findTreeOrderBy(String values) throws IOException {
		String hql = "from Tree where parentId=? order by name asc";
		List<Tree> list = this.find(hql, values);
		return list;
	}

	public List<Tree> findByPrarentIdGE(Object[] values) throws IOException {
		String hql = "from Tree where parentId=? and position>=? and id!=? order by position asc";
		List<Tree> list = this.find(hql, values);
		return list;
	}

	public List<Tree> findByPrarentIdLT(Object[] values) throws IOException {
		String hql = "from Tree where parentId=? and position<? and id!=? order by position desc";
		List<Tree> list = this.find(hql, values);
		return list;
	}

}

package san.dao.article;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import san.entity.article.Article;

import com.orm.HibernateDao;
import com.web.page.Page;
@Repository
public class ArticleDao extends HibernateDao<Article,String>{
	/**
	 * 自动生成ArticleDao类.
	 */
	 private static final long serialVersionUID = -4655151658669028187L;

	public Long getTreeIdCount(String treeId)throws IOException {
	  String hql="select count(id) from Article where treeId=? ";	
      return this.findUnique(hql,treeId);
	}
	
	public Page<Article> findArticlePageByQBC(Page<Article> page, Article article){
		Criteria criteria=this.createCriteria();
		criteria.addOrder(Order.desc("insertDate"));	
		criteria.add(Restrictions.eq("treeId", article.getTreeId()));
		if(StringUtils.isNotBlank(article.getTitle())){
			criteria.add(Restrictions.ilike("title", article.getTitle(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(article.getAuthor())){
			criteria.add(Restrictions.ilike("author", article.getAuthor(),MatchMode.ANYWHERE));
		}
		
		return this.findPage(page, criteria);
	}
	
	public Long getCount() throws IOException {
		String hql = "select count(id) from Article ";
		Long count = findUnique(hql);
		return count;
	}
	
	public List<Article> findByOrder(String value){
		 String hql=" from Article where treeId=? order by title ";
		 return this.find(hql, value);
	}

}

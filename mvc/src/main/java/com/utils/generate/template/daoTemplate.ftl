package san.dao.${entityPackage};

import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.orm.HibernateDao;
import com.web.page.Page;
import san.entity.${entityPackage}.${entityName};

@Repository
public class ${entityName}Dao extends HibernateDao<${entityName},String>{
	/**
	 * 自动生成${entityName}Dao类.
	 */
	 private static final long serialVersionUID = ${serialVersionUID}L;
	 
	 public Page<${entityName}> findPageFilter(Page<${entityName}> page, ${entityName} ${entityName?uncap_first})throws Exception  {
		Criteria criteria = this.createCriteria();
		criteria.addOrder(Order.desc("insertDate"));
		if (StringUtils.isNotBlank(${entityName?uncap_first}.getCreateBy())) {
			criteria.add(Restrictions.ilike("createBy", ${entityName?uncap_first}.getCreateBy(),
					MatchMode.ANYWHERE));
		}
		return this.findPage(page, criteria);
	}
}

package san.service.${entityPackage};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.web.page.Page;

import san.dao.${entityPackage}.${entityName}Dao;
import san.entity.${entityPackage}.${entityName};
@Service
@Transactional
public class ${entityName}Service{
	/**
	 * 自动生成${entityName}Service类.
	 */
	private static final long serialVersionUID = ${serialVersionUID}L;
	@Autowired
	private ${entityName}Dao ${entityName?uncap_first}Dao;
	
	@Transactional(readOnly = true)
	public Page<${entityName}> getAll(Page<${entityName}> page) throws Exception {
		return ${entityName?uncap_first}Dao.getAll(page);
	}

	@Transactional(readOnly = true)
	public Page<${entityName}> findPageFilter(Page<${entityName}> page, ${entityName} ${entityName?uncap_first})
			throws Exception {
		return ${entityName?uncap_first}Dao.findPageFilter(page, ${entityName?uncap_first});
	}
	
	@Transactional(readOnly = true)
	public ${entityName} get(String id) throws Exception {
		return ${entityName?uncap_first}Dao.get(id);
	}

	public void save(${entityName} ${entityName?uncap_first}) throws Exception {
		${entityName?uncap_first}Dao.save(${entityName?uncap_first});
	}

	public void delete(String id) throws Exception {
		${entityName?uncap_first}Dao.delete(id);
	}

}

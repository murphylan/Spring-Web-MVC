package san.dao.${entityPackage};

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import san.entity.${entityPackage}.${entityName};
import san.spring.SpringTxTestCase;
@Repository
public class Test${entityName}Dao extends SpringTxTestCase{
	/*
	 * 自动生成代码
	 */
	@Autowired
	private ${entityName}Dao ${entityName?uncap_first}Dao;
	
	@Test  
	//如果你需要真正插入数据库,将Rollback设为false
	//@Rollback(false) 
	public void save${entityName}() throws Exception{  
		${entityName} ${entityName?uncap_first} = new ${entityName}();
		${entityName?uncap_first}Dao.save(${entityName?uncap_first});
	   }  
}

package san.service.${entityPackage};

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import san.entity.${entityPackage}.${entityName};
import san.spring.SpringTxTestCase;
@Repository
public class Test${entityName}Service extends SpringTxTestCase{
	/*
	 * 自动生成代码
	 */
	@Autowired
	private ${entityName}Service ${entityName?uncap_first}Service;
	
	@Test  
	//如果你需要真正插入数据库,将Rollback设为false
	//@Rollback(false) 
	public void save${entityName}() throws Exception{ 
		${entityName} ${entityName?uncap_first} = new ${entityName}();
		${entityName?uncap_first}Service.save(${entityName?uncap_first});
	   }  
}

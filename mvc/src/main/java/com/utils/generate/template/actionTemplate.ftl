package san.web.${entityPackage};

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import san.entity.${entityPackage}.${entityName};
import san.service.${entityPackage}.${entityName}Service;
import com.web.page.Page;
import com.web.struts2.CRUDSupportAction;

@ParentPackage("default")
@Results( { @Result(name = "reload", location = "${entityName?uncap_first}.shtml", type = "redirect") })
public class ${entityName}Action extends CRUDSupportAction {
	/**
	 * 自动生成${entityName}Action类.
	 */
	private static final long serialVersionUID = ${serialVersionUID}L;
	@Autowired
	private ${entityName}Service ${entityName?uncap_first}Service;
	private Page<${entityName}> page = new Page<${entityName}>(10);//每页10条记录
	private ${entityName} ${entityName?uncap_first};
	
	@Override
	protected void prepareModel() throws Exception {
		if (${entityName?uncap_first} != null && StringUtils.isNotBlank(${entityName?uncap_first}.getId())) {
			${entityName?uncap_first} = ${entityName?uncap_first}Service.get(${entityName?uncap_first}.getId());
		} else {
			if (StringUtils.isNotBlank(getId())) {
				${entityName?uncap_first} = ${entityName?uncap_first}Service.get(getId());
			} else {
				${entityName?uncap_first} = new ${entityName}();
			}
		}
	}
	
	@Override
	public String delete() throws Exception {
		if (StringUtils.isNotBlank(getId())) {
			${entityName?uncap_first}Service.delete(getId());
		}
		return RELOAD;
	}

	@Override
	public String input() throws Exception {
		return "input";
	}

	@Override
	public String list() throws Exception {
		setPage(${entityName?uncap_first}Service.getAll(page));
		return SUCCESS;
	}

	@SuppressWarnings("finally")
	@Override
	public String save() throws Exception {
		try {
			${entityName?uncap_first}Service.save(${entityName?uncap_first});
			addActionMessage("保存成功!");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("保存失败!");
		}finally{
			return RELOAD;
		}
	}

	public Page<${entityName}> getPage() {
		return page;
	}

	public void setPage(Page<${entityName}> page) {
		this.page = page;
	}

	public ${entityName} get${entityName}() {
		return ${entityName?uncap_first};
	}

	public void set${entityName}(${entityName} ${entityName?uncap_first}) {
		this.${entityName?uncap_first} = ${entityName?uncap_first};
	}

}

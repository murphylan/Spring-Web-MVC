package san.web.${entityPackage};

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import san.entity.${entityPackage}.${entityName};
import san.service.${entityPackage}.${entityName}Service;
import san.web.account.ResourceNotFoundException;
import com.web.page.Page;

@Controller
@RequestMapping(value = "/${entityName?lower_case}")
public class ${entityName}Controller {

	/**
	 * 自动生成${entityName}Controller类.
	 */
	private static final long serialVersionUID = ${serialVersionUID}L;
	
	@Autowired
	private ${entityName}Service ${entityName?uncap_first}Service;

	@RequestMapping(value = "/list")
	public String getList(Page<${entityName}> page,${entityName} ${entityName?uncap_first},Model model) {
		try {
			page = ${entityName?uncap_first}Service.findPageFilter(page,${entityName?uncap_first});
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("page", page);
		model.addAttribute("${entityName?uncap_first}", ${entityName?uncap_first});
		return "${entityName?lower_case}/list";
	}

	@RequestMapping(value = "/input", method = RequestMethod.GET)
	public String getInput(Model model) {
		model.addAttribute("${entityName?uncap_first}", new ${entityName}());
		return "${entityName?lower_case}/input";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid ${entityName} ${entityName?uncap_first}, BindingResult result) {
		if (result.hasErrors()) {
			return "${entityName?lower_case}/input";
		}
		try {
			${entityName?uncap_first}Service.save(${entityName?uncap_first});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/${entityName?lower_case}/list";
	}

	@RequestMapping(value = "/del/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable String id) {
		try {
			${entityName?uncap_first}Service.delete(id);
		} catch (Exception e) {
			throw new ResourceNotFoundException(id);
		}
		return "redirect:/${entityName?lower_case}/list";
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public String update(@PathVariable String id, Model model) {
		try {
			${entityName} ${entityName?uncap_first} = ${entityName?uncap_first}Service.get(id);
			if (${entityName?uncap_first} == null) {
				throw new ResourceNotFoundException(id);
			}
			model.addAttribute("${entityName?uncap_first}",${entityName?uncap_first});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "${entityName?lower_case}/input";
	}

}

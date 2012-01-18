package san.web.dictionary;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import san.entity.dictionary.Dictionary;
import san.service.dictionary.DictionaryService;
import san.web.account.ResourceNotFoundException;

import com.web.page.Page;

@Controller
@RequestMapping(value = "/dictionary")
public class DictionaryController {
	
	@Autowired
	private DictionaryService dictionaryservice;

	@RequestMapping(value="/input",method = RequestMethod.GET)
	public String getCreateForm(Model model) {

		model.addAttribute("dictionary", new Dictionary());
		return "dictionary/input";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Dictionary dictionary, BindingResult result) {
		if (result.hasErrors()) {
			return "dictionary/input";
		}
		try {
			dictionaryservice.save(dictionary);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/dictionary/list";
	}

	@RequestMapping(value = "/list")
	public String getDictionaryList(Page<Dictionary> page, Model model) {
		if (null == page) {
			page = new Page<Dictionary>(10);// 每页10条记录
		}
		try {
			page.setPageSize(10);
			page = dictionaryservice.findPageByOrder(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("page", page);
		return "dictionary/list";
	}
	
	@RequestMapping(value = "/del/{id}", method = RequestMethod.GET)
	public String del(@PathVariable String id) {
		try {
			dictionaryservice.delete(id);
		} catch (Exception e) {
			throw new ResourceNotFoundException(id);
		}
		return "redirect:/dictionary/list";
	}
	
	
	@RequestMapping(value = "/delall", method = RequestMethod.POST)
	public String delAll(@RequestParam List<String> ids) {
		try {
			for (String id : ids) {
				dictionaryservice.delete(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/dictionary/list";
	}
	
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public String get(@PathVariable String id, Model model) {
		Dictionary dictionary = null;
		try {
			dictionary = dictionaryservice.get(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (dictionary == null) {
			throw new ResourceNotFoundException(id);
		}
		model.addAttribute("dictionary",dictionary);
		return "dictionary/input";
	}
	
}

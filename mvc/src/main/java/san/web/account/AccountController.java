package san.web.account;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import san.entity.account.Account;
import san.entity.dictionary.Dictionary;
import san.service.dictionary.DictionaryService;
import san.service.system.InitAuthorityService;
import san.service.system.UserService;

import com.utils.threadlocal.FakeSession;
import com.web.page.Page;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

	private Map<Long, Account> accounts = new ConcurrentHashMap<Long, Account>();

	@Autowired
	private DictionaryService dictionaryService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private InitAuthorityService initAuthorityService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String getCreateForm(Model model) {
		model.addAttribute(new Account());
		return "account/createForm";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Account account, BindingResult result) {
		try {
			initAuthorityService.initAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result.hasErrors()) {
			return "account/createForm";
		}
		this.accounts.put(account.assignId(), account);
		return "redirect:/account/" + account.getId();
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public String getView(@PathVariable Long id, Model model) {
		Account account = this.accounts.get(id);
		if (account == null) {
			throw new ResourceNotFoundException(id);
		}
		model.addAttribute(account);
		return "account/view";
	}

	@RequestMapping(value = "/list")
	public String getList(Model model) {
		for (Iterator<Entry<Long, Account>> i = accounts.entrySet().iterator(); i
				.hasNext();) {
			Entry<Long, Account> e = i.next();
			Object key = e.getKey();
			Object val = e.getValue();
			System.out.println("key is:" + key + ";val is" + val);
		}
		model.addAttribute("h", accounts);
		Page<Dictionary> page = new Page<Dictionary>(10);// 每页10条记彄1�7
		try {
			page = dictionaryService.findPageByOrder(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("page", page);
		System.out.println(messageSource
				.getMessage("welcome.title", null, null));
		return "account/list";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String handleFormUpload(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			String fileName = file.getName() + file.getOriginalFilename();
			try {
				// accountService.createFile(file.getInputStream(),
				// "d:/"+fileName);
				file.transferTo(new File("d:/" + fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "redirect:uploadSuccess";
		} else {
			return "redirect:uploadFailure";
		}
	}
	
	@RequestMapping(value = "/createauthorityconfig", method = RequestMethod.GET)
	public String CreateAuthorityConfig(Model model){
		try {
			model.addAttribute("authorityList", userService.getAllAuthority());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "account/createConfig";
	}
	
}

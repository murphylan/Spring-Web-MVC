package san.web.system;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import san.entity.account.Account;
import san.entity.system.Authority;
import san.entity.system.Role;
import san.entity.system.User;
import san.service.system.UserService;
import san.web.account.ResourceNotFoundException;

import com.web.page.AvailabilityStatus;
import com.web.page.Page;

@Controller
@RequestMapping(value = "/security")
public class SystemController {
	@Autowired
	private UserService userService;
	
	private Map<String, String> systems = new ConcurrentHashMap<String, String>();
	/*
	 * Authority
	 */
	@RequestMapping(value ="/authoritylist")
	public String getAuthorityList(Page<Authority> page, Model model) {
		if (null == page) {
			page = new Page<Authority>(10);// 每页10条记录
		}
		try {
			page.setPageSize(10);
			page.setOrderBy("name");
			page.setOrder("asc");
			page = userService.getAllAuthority(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("page", page);
		return "security/authoritylist";
	}

	@RequestMapping(value = "/authorityinput", method = RequestMethod.GET)
	public String getAuthorityInput(Model model) {
		model.addAttribute("authority", new Authority());
		return "security/authorityinput";
	}

	@RequestMapping(value = "/authorityinput", method = RequestMethod.POST)
	public String createAuthority(@Valid Authority authority,
			BindingResult result) {
		if (result.hasErrors()) {
			return "security/authorityinput";
		}
		try {
			userService.saveAuthority(authority);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/security/authoritylist";
	}

	@RequestMapping(value = "/delauthority/{id}", method = RequestMethod.GET)
	public String delAuthority(@PathVariable String id,Model model) {
		try {
			if(userService.authorityHasUse(id)){
				
			}else
			userService.deleteAuthority(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/security/authoritylist";
	}

	@RequestMapping(value = "/getauthority/{id}", method = RequestMethod.GET)
	public String getAuthority(@PathVariable String id, Model model) {
		Authority authority = null;
		try {
			authority = userService.getAuthority(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (authority == null) {
			throw new ResourceNotFoundException(id);
		}
		model.addAttribute(authority);
		return "security/authorityinput";
	}

	/*
	 * Role
	 */
	@RequestMapping(value = "/rolelist", method = {RequestMethod.GET,RequestMethod.POST})
	public String getRoleList(Page<Role> page, Model model) {
		if (null == page) {
			page = new Page<Role>(10);// 每页10条记录
		}
		try {
			page.setPageSize(10);
			page = userService.getAllRoles(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("page", page);
		return "security/rolelist";
	}

	@RequestMapping(value = "/roleinput", method = RequestMethod.GET)
	public String getRoleInput(Model model) {
		Role role = new Role();
		model.addAttribute("role", role);
		model.addAttribute("authorityMap", userService.getAllAuthorityForMap());
		return "security/roleinput";
	}

	@RequestMapping(value = "/roleinput", method = RequestMethod.POST)
	public String createRole(@RequestParam(required=false) List<String> ids, @Valid Role role,
			BindingResult result, Model model) {
		if (result.hasErrors()||CollectionUtils.isEmpty(ids)) {
			model.addAttribute("authorityMap", userService
					.getAllAuthorityForMap());
			if(CollectionUtils.isEmpty(ids)){
				model.addAttribute("idsError","Please select a authority.");
			}
			return "security/roleinput";
		}
		try {
			userService.saveRole(role, ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/security/rolelist";
	}

	@RequestMapping(value = "/getrole/{id}", method = RequestMethod.GET)
	public String getRole(@PathVariable String id, Model model) {
		Role role = null;
		try {
			role = userService.getRole(id);
			model.addAttribute("ids", role.getAuthIds());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (role == null) {
			throw new ResourceNotFoundException(id);
		}
		model.addAttribute(role);
		model.addAttribute("authorityMap", userService.getAllAuthorityForMap());
		return "security/roleinput";
	}

	@RequestMapping(value = "/delrole/{id}", method = RequestMethod.GET)
	public String delRole(@PathVariable String id) {
		try {
			userService.deleteRole(id);
		} catch (Exception e) {
			throw new ResourceNotFoundException(id);
		}
		return "redirect:/security/rolelist";
	}
	
	@RequestMapping(value = "/delallrole", method = RequestMethod.POST)
	public String delAllRole(@RequestParam List<String> ids) {
		try {
			for (String id : ids) {
				userService.deleteRole(id);
			}
		} catch (Exception e) {
		  e.printStackTrace();
		}
		return "redirect:/security/rolelist";
	}

	/*
	 * User
	 */
	@RequestMapping(value = "/userlist")
	public String getUserList(Page<User> page, Model model) {
		if (null == page) {
			page = new Page<User>(10);// 每页10条记录
		}
		try {
			page.setPageSize(10);
			page = userService.getAllUsers(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("page", page);
		return "security/userlist";
	}

	@RequestMapping(value = "/userinput", method = RequestMethod.GET)
	public String getUserInput(Model model) {
		try {
			User user = new User();
			model.addAttribute("user", user);
			model.addAttribute("roleMap", userService.getAllRoleForMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "security/userinput";
	}

	@RequestMapping(value = "/userinput", method = RequestMethod.POST)
	public String createUser(@RequestParam(required=false) List<String> ids, @RequestParam(required=false) List<String> teams, @Valid User user,
			BindingResult result, Model model) {
		
		if (result.hasErrors()||CollectionUtils.isEmpty(ids)) {
			try {
				model.addAttribute("roleMap", userService.getAllRoleForMap());
				if(CollectionUtils.isEmpty(ids)){
					model.addAttribute("roleError", "Role must not be null.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "security/userinput";
		}
		try {
			userService.saveUser(user, ids, teams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/security/userlist";
	}

	@RequestMapping(value = "/getuser/{id}", method = RequestMethod.GET)
	public String getUser(@PathVariable String id, Model model) {
		User user = null;
		try {
			user = userService.getUser(id);
			model.addAttribute("ids", user.getRoleIds());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user == null) {
			throw new ResourceNotFoundException(id);
		}
		model.addAttribute(user);
		model.addAttribute("roleMap", userService.getAllRoleForMap());
		return "security/userinput";
	}

	@RequestMapping(value = "/deluser/{id}", method = RequestMethod.GET)
	public String delUser(@PathVariable String id) {
		try {
			userService.deleteUser(id);
		} catch (Exception e) {
			throw new ResourceNotFoundException(id);
		}
		return "redirect:/security/userlist";
	}
	
	@RequestMapping(value = "/delalluser", method = RequestMethod.POST)
	public String delAllUser(@RequestParam List<String> ids) {
		try {
			for (String id : ids) {
				userService.deleteUser(id);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/security/userlist";
	}
	
	@RequestMapping(value = "/availability", method = RequestMethod.GET)
	public @ResponseBody
	AvailabilityStatus getAvailability(@RequestParam String loginName) {
		try {
			loginName=loginName.trim();
			if (!userService.isLoginNameUnique(loginName, "")) {
				return AvailabilityStatus.notAvailable(loginName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AvailabilityStatus.available();
	}
	
	@RequestMapping(value = "/role/availability", method = RequestMethod.GET)
	public @ResponseBody
	AvailabilityStatus getRoleAvailability(@RequestParam String name) {
		try {
			name=name.trim();
			if (!userService.isRoleNameUnique(name, "")) {
				return AvailabilityStatus.notAvailable(name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AvailabilityStatus.available();
	}

}

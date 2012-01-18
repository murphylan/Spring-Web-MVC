package san.web.tree;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import san.entity.tree.TreeDto;
import san.service.tree.TreeService;

@Controller
@RequestMapping(value = "/tree")
public class TreeController {

	/**
	 * 自动生成TreeController类.
	 */
	private static final long serialVersionUID = -6010243665209073258L;

	@Autowired
	private TreeService treeService;

	@RequestMapping(value = "/view")
	public String viewTree(Model model) {

		return "tree/view";
	}

	@RequestMapping(value = "/add")
	public @ResponseBody
	String add(HttpServletRequest request) {
		String jsonStr = null;
		try {
			jsonStr = treeService.save(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	@RequestMapping(value = "/del")
	public @ResponseBody
	String del(Model model, HttpServletRequest request) {
		try {
			String id = (String) request.getParameter("id");
			treeService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "scuess";
	}

	@RequestMapping(value = "/get")
	public @ResponseBody
	List<TreeDto> get(HttpServletRequest request) {
		String id = (String) request.getParameter("id");
		String operation = (String) request.getParameter("operation");

		List<TreeDto> list = null;
		try {
			list = treeService.getJson(id, operation);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(value = "/move")
	public @ResponseBody
	String move(HttpServletRequest request) {
		String jsonStr = null;
		try {
			jsonStr = treeService.move(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	@RequestMapping(value = "/search")
	public @ResponseBody
	String[] search(HttpServletRequest request) {
		String[] jsonStr = null;
		try {
			String search_str = request.getParameter("search_str");
			jsonStr = treeService.findBy(search_str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	@RequestMapping(value = "/see/{id}/{type}",method = RequestMethod.GET)
	public String see(@PathVariable String id, @PathVariable String type) {
		try {
			if ("file".equals(type)) {
				return "redirect:/article/tdetail/" + id;
			} else if ("folder".equals(type)) {
				return "redirect:/article/tlist/"+id;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}

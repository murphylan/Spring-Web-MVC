package san.web.article;

import java.io.OutputStream;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerMapping;

import san.entity.article.Article;
import san.entity.attachment.Attachment;
import san.entity.tree.Tree;
import san.service.article.ArticleService;
import san.service.attachment.AttachmentService;
import san.service.tree.TreeService;
import san.web.account.ResourceNotFoundException;

import com.web.page.Page;

@Controller
@RequestMapping(value = "/article")
public class ArticleController {

	/**
	 * 自动生成ArticleController类.
	 */
	private static final long serialVersionUID = -3136697469674509734L;

	@Autowired
	private ArticleService articleService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private TreeService treeService;
	Long PLACE_KEY;

	@RequestMapping(value = "/input/{treeId}", method = RequestMethod.GET)
	public String getInput(@PathVariable String treeId, Model model) {
		Article article = new Article();
		article.setTreeId(treeId);
		model.addAttribute("article", article);
		PLACE_KEY = (new Random()).nextLong();
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "article/input";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@Valid Article article, BindingResult result,
			MultipartHttpServletRequest request, HttpSession session,
			Model model) {
		if (result.hasErrors()) {

			return "article/input";
		}

		try {
			if (((Long) session.getAttribute("LAST_PLACE_KEY")) != null
					&& ((Long) session.getAttribute("LAST_PLACE_KEY"))
							.equals(PLACE_KEY)) {
				return "redirect:/article/list/"+article.getTreeId();
			}
			session.setAttribute("LAST_PLACE_KEY", PLACE_KEY);
			String savePath = request.getSession().getServletContext()
					.getRealPath("/")
					+ "attached/";
			MultiValueMap<String, MultipartFile> files = request
					.getMultiFileMap();
			articleService.saveArticleAndAttachment(article, files.values(),
					savePath);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/article/list/"+article.getTreeId();
	}

	@RequestMapping(value = "/del/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable String id) {
		String treeId = "";
		try {
			Article article = articleService.get(id);
			treeId = article.getTreeId();
			articleService.delete(article);
		} catch (Exception e) {
			throw new ResourceNotFoundException(id);
		}
		return "redirect:/article/list/" + treeId;
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public String update(@PathVariable String id, Model model) {
		try {
			Article article = articleService.get(id);
			Tree tree = treeService.findUniqueByPropertyName("id", article
					.getTreeId());
			PLACE_KEY = (new Random()).nextLong();
			if (article == null) {
				throw new ResourceNotFoundException(id);
			}
			model.addAttribute("article", article);
			model.addAttribute("nodeName", tree.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "article/input";
	}

	@RequestMapping(value ={"/detail/{id}", "/tdetail/{id}"}, method = RequestMethod.GET)
	public String detail(@PathVariable String id, Model model) {
		try {
			Article article = articleService.get(id);
			if (article == null) {
				throw new ResourceNotFoundException(id);
			}
			model.addAttribute("article", article);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "article/detail";
	}

	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
	public @ResponseBody
	void download(@PathVariable String id, HttpServletResponse response) {
		try {
			Attachment attachment = attachmentService.get(id);
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ attachment.getName() + "\"");// 设定输出文件头
			response.setContentType("application/octet-stream");// 定义输出类型
			OutputStream os = response.getOutputStream();// 取得输出流
			articleService.download(os, attachment.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/delattachement/{id}", method = RequestMethod.GET)
	public String delAttachement(@PathVariable String id) {
		String articleId = null;
		try {

			articleId = attachmentService.delete(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/article/get/" + articleId;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody
	String upload(@RequestParam("imgFile") MultipartFile file,
			HttpServletRequest request) {
		String jsonStr = "";
		String savePath = request.getSession().getServletContext().getRealPath(
				"/")
				+ "attached/";
		String saveUrl = request.getContextPath() + "/attached/";
		try {
			jsonStr = articleService.uploadPhoto(file, savePath, saveUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	@RequestMapping(value = "/delall", method = RequestMethod.POST)
	public String delAll(@RequestParam List<String> ids,
			@RequestParam String treeId) {
		try {
			for (String string : ids) {
				articleService.delete(string);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/article/list/" + treeId;
	}

	@RequestMapping(value ={"/list/{treeId}","/tlist/{treeId}"})
	public String getList(@PathVariable String treeId, Page<Article> page,
			Model model, Article article) {
		try {
			article.setTreeId(treeId);
			model.addAttribute("page", articleService.findArticlePageByQBC(
					page, article));
			model.addAttribute("folderId", treeId);
			model.addAttribute("article", article);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "article/list";
	}

	@RequestMapping(value = "/lucene/**")
	public String lucene(HttpServletRequest request, Page<Article> page,
			Model model) {
		try {
			String pattern = (String) request
					.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
			String searchTerm = new AntPathMatcher().extractPathWithinPattern(
					pattern, request.getRequestURI());
			model.addAttribute("page", articleService.findPage(page,
					StringUtils.substringAfter(searchTerm, "/")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "article/lucene";
	}

}

package san.web.attachment;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import san.entity.attachment.Attachment;
import san.service.attachment.AttachmentService;
import san.web.account.ResourceNotFoundException;
import com.web.page.Page;

@Controller
@RequestMapping(value = "/attachment")
public class AttachmentController {

	/**
	 * 自动生成AttachmentController类.
	 */
	private static final long serialVersionUID = -4639817333424133114L;
	
	@Autowired
	private AttachmentService attachmentService;

	@RequestMapping(value = "/list")
	public String getList(Page<Attachment> page, Model model) {
		try {
			page = attachmentService.getAll(page);
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "attachment/list";
	}

	@RequestMapping(value = "/input", method = RequestMethod.GET)
	public String getInput(Model model) {
		model.addAttribute("attachment", new Attachment());
		return "attachment/input";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Attachment attachment, BindingResult result) {
		if (result.hasErrors()) {
			return "attachment/input";
		}
		try {
			attachmentService.save(attachment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/attachment/list";
	}

	@RequestMapping(value = "/del/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable String id) {
		try {
			attachmentService.delete(id);
		} catch (Exception e) {
			throw new ResourceNotFoundException(id);
		}
		return "redirect:/article/list";
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public String update(@PathVariable String id, Model model) {
		try {
			Attachment attachment = attachmentService.get(id);
			if (attachment == null) {
				throw new ResourceNotFoundException(id);
			}
			model.addAttribute("attachment",attachment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "attachment/input";
	}

}

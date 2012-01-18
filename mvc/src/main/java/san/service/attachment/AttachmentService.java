package san.service.attachment;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import san.dao.attachment.AttachmentDao;
import san.entity.article.Article;
import san.entity.attachment.Attachment;

import com.web.page.Page;
@Service
@Transactional
public class AttachmentService{
	/**
	 * 自动生成AttachmentService类.
	 */
	private static final long serialVersionUID = 8145144100476102342L;
	@Autowired
	private AttachmentDao attachmentDao;
	
	@Transactional(readOnly = true)
	public Page<Attachment> getAll(Page<Attachment> page) throws Exception {
		return attachmentDao.getAll(page);
	}

	@Transactional(readOnly = true)
	public Attachment get(String id) throws Exception {
		return attachmentDao.get(id);
	}

	public void save(Attachment attachment) throws Exception {
		attachmentDao.save(attachment);
	}

	public String delete(String id) throws Exception {
		Attachment attachment=this.get(id);
		String path=attachment.getPath();
		File file=new File(path);
		if(file.exists()){
		   FileUtils.forceDelete(file);
		}
		Article article=attachment.getArticle();
		article.getAttachmentList().remove(attachment);
		this.delete(attachment);
		
		return article.getId();
	}
	public void delete(Attachment attachment) throws Exception {
		attachmentDao.delete(attachment);
	}
    
	
}

package san.service.article;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import san.dao.article.ArticleDao;
import san.dao.article.ArticleLuceneDao;
import san.entity.article.Article;
import san.entity.attachment.Attachment;
import san.service.attachment.AttachmentService;

import com.google.common.collect.Maps;
import com.web.page.Page;
import com.web.spring.SpringSecurityUtils;

@Service
@Transactional
public class ArticleService {
	/**
	 * 自动生成ArticleService类.
	 */
	private static final long serialVersionUID = 632487329525223093L;
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private ArticleLuceneDao articleLuceneDao;
	@Autowired
	private AttachmentService attachmentService;

	@Transactional(readOnly = true)
	public Page<Article> getAll(Page<Article> page) throws Exception {
		return articleDao.getAll(page);
	}

	@Transactional(readOnly = true)
	public Article get(String id) throws Exception {
		return articleDao.findUniqueBy("id", id);
	}

	public void save(Article article) throws Exception {
		articleDao.save(article);
	}

	public void delete(Article article) throws Exception {
		articleDao.delete(article);
	}

	public void delete(String id) throws Exception {
		articleDao.delete(id);
	}

	public List<Article> findByOrder(String value) throws Exception {
		return articleDao.findByOrder(value);
	}

	public List<Article> findBy(String propertyName, String value)
			throws Exception {
		return articleDao.findBy(propertyName, value);
	}

	public String saveArticleAndAttachment(Article article,
			Collection<List<MultipartFile>> files, String savePath)
			throws Exception {
		article.setCreateBy(SpringSecurityUtils.getCurrentUserName());
		this.save(article);
		for (List<MultipartFile> fileList : files) {
			if (!fileList.isEmpty()) {
				for (MultipartFile file : fileList) {
					if (!file.isEmpty()) {
						saveAttachmentAndFile(article, file, savePath);
					}
				}
			}
		}
		return article.getId();
	}

	public void saveAttachmentAndFile(Article article, MultipartFile file,
			String savePath) throws Exception {
		Attachment attachment = new Attachment();
		attachment.setArticle(article);
		attachment.setFileSize(file.getSize() / 1024 + "KB");
		String originalName = file.getOriginalFilename();
		attachment.setName(originalName);
		String suffix = originalName.substring(originalName.indexOf("."));
		String newname = System.currentTimeMillis() + suffix;

		attachment.setPath(savePath + newname);
		attachmentService.save(attachment);

		File dir = new File(savePath);
		FileUtils.forceMkdir(dir);
		File newfile = new File(attachment.getPath());
		file.transferTo(newfile);
	}

	public String uploadPhoto(MultipartFile file, String savePath,
			String saveUrl) throws IOException {
		String newname = "";
		if (!file.isEmpty()) {
			String originalName = file.getOriginalFilename();
			String suffix = originalName.substring(originalName.indexOf("."));
			newname = System.currentTimeMillis() + suffix;

			File newfile = new File(savePath + newname);
			FileUtils.forceMkdir(newfile);
			file.transferTo(newfile);
		}
		Map<Object, Object> map = Maps.newHashMap();
		map.put("error", 0);
		map.put("url", saveUrl + newname);
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(map);

	}

	public void download(OutputStream os, String path) throws Exception {
		InputStream inputStream = new FileInputStream(new File(path));
		int i = 0;
		byte b[] = new byte[1024];
		while ((i = inputStream.read(b)) != -1) {
			os.write(b, 0, i);
		}
		os.flush();
		os.close(); // 关闭流
	}

	@Transactional(readOnly = true)
	public Long getTreeIdCount(String treeId) throws IOException {
		return articleDao.getTreeIdCount(treeId);
	}

	public Long getCount() throws IOException {
		return articleDao.getCount();
	}

	@Transactional(readOnly = true)
	public Page<Article> findArticlePageByQBC(Page<Article> page,
			Article article) throws Exception {
		return articleDao.findArticlePageByQBC(page, article);
	}

	@Transactional(readOnly = true)
	public Page<Article> findPage(Page<Article> page, String text)
			throws Exception {
		if (StringUtils.isEmpty(text)) {
			return page;
		}
		return articleLuceneDao.findPage(page, text);
	}

	@Transactional(readOnly = true)
	public Article findUniqueBy(String propertyName, String value) {
		return articleDao.findUniqueBy(propertyName, value);
	}
}

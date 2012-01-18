package san.entity.attachment;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import san.entity.article.Article;

import com.orm.BaseEntity;

@Entity
@Table(name = "start_attachment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attachment extends BaseEntity {

	private static final long serialVersionUID = 1602793749200729665L;
	private String name;
	private String description;
	private String fileSize;
	private String path;
	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	@JoinColumn(name = "article_id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Article article;
	
	
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
}

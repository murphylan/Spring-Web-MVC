package san.entity.article;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.NotEmpty;

import san.entity.attachment.Attachment;

import com.google.common.collect.Lists;
import com.orm.BaseEntity;

@Entity
@Table(name = "start_article")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Indexed
public class Article extends BaseEntity {
	/**
	 * 文章
	 */
	private static final long serialVersionUID = -6561198679941658381L;
	
	@NotEmpty
	@Field(index=Index.TOKENIZED,store=Store.YES)
	private String title;
	
	@NotEmpty
	@Lob
	@Field(index=Index.TOKENIZED,store=Store.YES)
	private String content;
	@NotEmpty
	@Field(index=Index.TOKENIZED,store=Store.YES)
	private String author;
	@NotEmpty(message="Please select a node!")
	private String treeId;
	
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy(value = "insertDate")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<Attachment> attachmentList =Lists.newArrayList();


	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void addAttachment(Attachment attachment) {
		if (!this.attachmentList.contains(attachment)) {
			this.attachmentList.add(attachment);
		}
		attachment.setArticle(this);
	}
	

}

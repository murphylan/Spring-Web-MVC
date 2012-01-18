package san.dao.article;

import org.apache.lucene.queryParser.ParseException;
import org.springframework.stereotype.Repository;

import san.entity.article.Article;

import com.orm.lucene.SimpleLuceneDao;
import com.web.page.Page;

@Repository
public class ArticleLuceneDao extends SimpleLuceneDao<Article, String> {
	public Page<Article> findPage(Page<Article> page, String text)
			throws ParseException {
		String[] fields = new String[] { "title", "content", "author" };
		page = this.findPage(page, fields, text, Article.class);
		return page;
	}

}

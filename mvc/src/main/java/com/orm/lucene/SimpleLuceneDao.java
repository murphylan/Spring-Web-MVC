package com.orm.lucene;

import java.io.Serializable;
import java.util.List;

import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.orm.hibernate.SimpleHibernateDao;
import com.web.page.Page;

public class SimpleLuceneDao<T, PK extends Serializable> extends
		SimpleHibernateDao<T, PK> {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * 手工建立索引
	 */
	public void index() {
		FullTextSession fullTextSession = Search
				.getFullTextSession(getSession());
		Transaction tx = fullTextSession.beginTransaction();
		List<T> list = getAll();
		for (T o : list) {
			fullTextSession.index(o);
		}
		tx.commit();
	}

	/**
	 * 通过关键字搜索
	 * 
	 * @param field索引字段名
	 * @param text搜索文本关键字
	 * @return
	 * @throws ParseException
	 */
	public List<T> findByFullText(String field, String text, Class<T> clazz) {
		try {
			Assert.notNull(field, "搜索字段为空");
			Assert.hasText(field, "搜索字段不是文本");
			Assert.hasText(text, "搜索内容不是文本");
			FullTextSession fullTextSession = Search
					.getFullTextSession(getSession());
			org.apache.lucene.queryParser.QueryParser parser = new QueryParser(
					field, new StopAnalyzer());
			// parser.setAllowLeadingWildcard(true);
			org.apache.lucene.search.Query luceneQuery = parser.parse(text);
			org.hibernate.Query fullTextQuery = null;
			if (clazz != null) {
				fullTextQuery = fullTextSession.createFullTextQuery(
						luceneQuery, clazz);
			} else {
				fullTextQuery = fullTextSession
						.createFullTextQuery(luceneQuery);
			}
			logger.info("find '" + text + "' on field:" + field);
			return fullTextQuery.list();
		} catch (ParseException e) {
			logger.error("not find '" + text + "' on field:" + field,
					e.fillInStackTrace());
			return null;
		} catch (Exception e) {
			logger.error("find '" + text + "' on field:" + field
					+ " having error!", e.fillInStackTrace());
			return null;
		}
	}

	/**
	 * 通过关键字搜索
	 * 
	 * @param field索引字段名
	 * @param text搜索文本关键字
	 * @return
	 * @throws ParseException
	 */
	public List<T> findByFullText(String[] field, String text, Class<T> clazz) {
		try {
			Assert.notNull(field, "搜索字段为空");
			Assert.notEmpty(field, "搜索字段数目为0");
			Assert.hasText(text, "搜索内容不是文本");
			FullTextSession fullTextSession = Search
					.getFullTextSession(getSession());
			BooleanClause.Occur[] flags = new BooleanClause.Occur[field.length];
			for (int i = 0; i < field.length; i++) {
				flags[i] = BooleanClause.Occur.SHOULD;
			}
			org.apache.lucene.search.Query luceneQuery = MultiFieldQueryParser
					.parse(text, field, flags, new StandardAnalyzer());
			org.hibernate.Query fullTextQuery = null;
			if (clazz != null) {
				fullTextQuery = fullTextSession.createFullTextQuery(
						luceneQuery, clazz);
			} else {
				fullTextQuery = fullTextSession
						.createFullTextQuery(luceneQuery);
			}
			logger.info("find '" + text + "' on field:" + field);
			return fullTextQuery.list();
		} catch (ParseException e) {
			logger.error("not find '" + text + "' on field:" + field,
					e.fillInStackTrace());
			return null;
		} catch (Exception e) {
			logger.error("find '" + text + "' on field:" + field
					+ " having error!", e.fillInStackTrace());
			return null;
		}
	}

	/**
	 * 通过关键字搜索
	 * 
	 * @param field索引字段名
	 * @param text搜索文本关键字
	 * @return
	 * @throws ParseException
	 */
	public Page<T> findPage(Page<T> page,String[] field, String text, Class<T> clazz) {
		try {
			Assert.notNull(field, "搜索字段为空");
			Assert.notEmpty(field, "搜索字段数目为0");
			Assert.hasText(text, "搜索内容不是文本");
			FullTextSession fullTextSession = Search
					.getFullTextSession(getSession());
			BooleanClause.Occur[] flags = new BooleanClause.Occur[field.length];
			for (int i = 0; i < field.length; i++) {
				flags[i] = BooleanClause.Occur.SHOULD;
			}
			org.apache.lucene.search.Query luceneQuery = MultiFieldQueryParser
					.parse(text, field, flags, new StandardAnalyzer());
			org.hibernate.Query fullTextQuery = null;
			if (clazz != null) {
				fullTextQuery = fullTextSession.createFullTextQuery(
						luceneQuery, clazz);
			} else {
				fullTextQuery = fullTextSession
						.createFullTextQuery(luceneQuery);
			}
			logger.info("find '" + text + "' on field:" + field);
			
			fullTextQuery.setFirstResult(page.getFirst() - 1);
			fullTextQuery.setMaxResults(page.getPageSize());
			
			page.setResult(fullTextQuery.list());
			
			return page;
		} catch (ParseException e) {
			logger.error("not find '" + text + "' on field:" + field,
					e.fillInStackTrace());
			return null;
		} catch (Exception e) {
			logger.error("find '" + text + "' on field:" + field
					+ " having error!", e.fillInStackTrace());
			return null;
		}
	}
}

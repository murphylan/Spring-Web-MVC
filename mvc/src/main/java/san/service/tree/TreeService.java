package san.service.tree;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import san.dao.tree.TreeDao;
import san.entity.article.Article;
import san.entity.tree.Tree;
import san.entity.tree.TreeDto;
import san.service.article.ArticleService;

import com.google.common.collect.Lists;
import com.utils.CollectionUtils;

@Service
@Transactional
public class TreeService {
	/**
	 * 自动生成TreeService类.
	 */
	private static final long serialVersionUID = 7818016452499804878L;
	@Autowired
	private TreeDao treeDao;

	@Autowired
	private ArticleService articleService;

	@Transactional(readOnly = true)
	public Tree get(String id) throws Exception {
		return treeDao.get(id);
	}

	public void save(Tree tree) {
		treeDao.save(tree);
	}

	public String save(HttpServletRequest request) throws Exception {
		Tree tree = null;
		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			tree = new Tree();
			tree.setName(request.getParameter("title"));
			tree.setType(request.getParameter("type"));
			tree.setPosition(request.getParameter("position"));
			tree.setParentId(request.getParameter("parentId"));
			treeDao.save(tree);

		} else {
			tree = treeDao.get(id);
			tree.setName(request.getParameter("title"));
			treeDao.save(tree);
		}
		return tree.getId();
	}

	public String move(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		String position = request.getParameter("position");
		String parentId = request.getParameter("ref");
		String copy = request.getParameter("copy");
		Tree tree=null;
		Article art = articleService.findUniqueBy("id", id);
		if(art!=null){
			if ("1".equals(copy)) {
				Article article=new Article();
				article.setAttachmentList(art.getAttachmentList());
				article.setAuthor(art.getAuthor());
				article.setContent(art.getContent());
				article.setCreateBy(art.getCreateBy());
				article.setInsertDate(art.getInsertDate());
				article.setTitle(art.getTitle());
				article.setTreeId(parentId);
				articleService.save(article);
				
			}else{
				art.setTreeId(parentId);
				articleService.save(art);
			}
		}else{
		tree = treeDao.get(id);
		if ("1".equals(copy)) {
         Tree newtree=new Tree();
         newtree.setName(tree.getName());
         newtree.setPosition(position);
         newtree.setParentId(parentId);
         newtree.setType(tree.getType());
         treeDao.save(newtree);
         return newtree.getId();
		} else {

			int old_position = Integer.parseInt(tree.getPosition());

			int int_position = Integer.parseInt(position);
			if (old_position < int_position) {
				int_position = int_position - 1;
			}
			Object[] object = { parentId, position, tree.getId() };

			List<Tree> list = treeDao.findByPrarentIdGE(object);
			int i = 0;
			for (Tree tree2 : list) {
				i++;
				tree2.setPosition((int_position + i) + "");
				treeDao.save(tree2);
			}
			i = 0;
			list = treeDao.findByPrarentIdLT(object);
			for (Tree tree2 : list) {
				i++;
				tree2.setPosition((int_position - i) + "");
				treeDao.save(tree2);
			}

			tree.setPosition(int_position + "");
			tree.setParentId(parentId);
			treeDao.save(tree);
		}
		}
		return tree.getId();
	}

	public void delete(String id) throws Exception {
		Article art = articleService.findUniqueBy("id", id);
		if (null == art) {
			treeDao.delete(id);
			List<Article> list = articleService.findBy("treeId", id);
			for (Article article : list) {
				articleService.delete(article);
			}
		} else {
			articleService.delete(art);
		}

	}

	@Transactional(readOnly = true)
	public List<Tree> findBy(String propertyName, Object value)
			throws Exception {
		return treeDao.findBy(propertyName, value);
	}

	@Transactional(readOnly = true)
	public String[] findBy(String value) throws Exception {
		List<Tree> list = treeDao.findBy(value);
		String[] temp = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			Tree tree = list.get(i);
			temp[i] = "#" + tree.getId();
		}
		return temp;
	}

	@Transactional(readOnly = true)
	public List<TreeDto> getJson(String id, String operation) throws Exception {
		List<TreeDto> list = Lists.newArrayList();
		List<Tree> treeList = treeDao.findTreeOrderBy(id);
		if (!CollectionUtils.isEmpty(treeList)) {
			for (Tree tree : treeList) {
				TreeDto treeDto = new TreeDto();
				treeDto.getAttr().setId(tree.getId());
				treeDto.getAttr().setRel(tree.getType());
				Long count = treeDao.findUnique(tree.getId());
				Long articleCount = 0L;
				String name = tree.getName();
				if (!"get_folder".equals(operation)) {
					articleCount = articleService.getTreeIdCount(tree.getId());
					if ("-1".equalsIgnoreCase(tree.getParentId())) {
						name = name + " (" + articleService.getCount() + ")";
					} else {
						name = name + " (" + articleCount + ")";
					}
				}
				if (count > 0 || articleCount > 0) {
					treeDto.setState("closed");
				} else {
					treeDto.setState("");
				}

				treeDto.getData().setTitle(name);
				list.add(treeDto);
			}
		}

		if (!"-1".equals(id) && !"get_folder".equals(operation)) {
			List<Article> articleList = articleService.findByOrder(id);
			for (Article article : articleList) {
				TreeDto treeDto = new TreeDto();
				treeDto.getAttr().setId(article.getId());
				treeDto.getAttr().setRel("default");
				treeDto.setState("");
				treeDto.getData().setTitle(article.getTitle());
				list.add(treeDto);
			}
		}

		return list;
	}

	@Transactional(readOnly = true)
	public List<Tree> findAllChildren(String id, List<Tree> list)
			throws Exception {
		List<Tree> treeList = this.findBy("parentId", id);
		if (CollectionUtils.isEmpty(treeList)) {
			return list;
		} else {
			list.addAll(treeList);
			for (Tree tree : treeList) {
				findAllChildren(tree.getId(), list);
			}
		}
		return list;
	}

	public Tree findUniqueByPropertyName(String propertyName, String value) {
		return treeDao.findUniqueBy(propertyName, value);
	}

	public void see(String id, String type) {
		if ("file".equals(type)) {

		} else if ("folder".equals(type)) {

		}
	}

}

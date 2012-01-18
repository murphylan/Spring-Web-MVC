package san.service.dictionary;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import san.dao.dictionary.DictionaryDao;
import san.entity.dictionary.Dictionary;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.web.page.Page;
import com.web.spring.SpringSecurityUtils;

@Service("dictionaryService")
@Transactional
public class DictionaryService {
	/**
	 * 自动生成DictionaryService类.
	 */
	@Autowired
	private DictionaryDao dictionaryDao;

	@Transactional(readOnly = true)
	public Page<Dictionary> findPageByOrder(Page<Dictionary> page) throws Exception {
		return dictionaryDao.findPage(page);//
	}

	@Transactional(readOnly = true)
	public Dictionary get(String id) {
		return dictionaryDao.get(id);
	}

	public void save(Dictionary dictionary) throws Exception {
		dictionary.setCreateBy(SpringSecurityUtils.getCurrentUserName());
		dictionaryDao.save(dictionary);
	}

	public void delete(String id) {
		dictionaryDao.delete(id);
	}

	@Transactional(readOnly = true)
	public List<Dictionary> getListByType(String type) throws Exception {
		List<Dictionary> dictionaryList = dictionaryDao.findBy("type", type);
		return dictionaryList;
	}
	
	
	
	//获得指定类型,传Map
	@Transactional(readOnly = true)
	public Map<String, String> getDictionaryItems(String type) throws Exception {
		List<Dictionary> dictionaryItems = this.getListByType(type);
		Map<String, String> map = Maps.newHashMap();
		map.put("","");
		if (!dictionaryItems.isEmpty()) {
			for (Dictionary o : dictionaryItems) {
				map.put(o.getId(), o.getName());
			}
		}
		return map;
	}

	@Transactional(readOnly = true)
	public List<Dictionary> findByIds(List<String> ids) throws Exception {
		if(CollectionUtils.isEmpty(ids)){
			return null;
		}
		return dictionaryDao.findByIds(ids);
	}

	
	public Dictionary findUniqueBy( String propertyName,  Object value) {
		return  dictionaryDao.findUniqueBy(propertyName, value);
	}
	
	//查指定类型,传符合条件的字典表对象
	@Transactional(readOnly = true)
	public List<Dictionary> findBy(String propertyName,  Object value){
		return dictionaryDao.findBy(propertyName, value);
	}

}

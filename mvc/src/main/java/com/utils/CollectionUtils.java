package com.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.web.util.WebUtils;

/**
 * 集合操作的Utils函数集合.
 * 
 * 主要针对Web应用与Hibernate的特征而开发.
 * 
 * @author san
 */
public class CollectionUtils extends org.springframework.util.CollectionUtils{
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory
			.getLogger(CollectionUtils.class);

	private CollectionUtils() {
	}

	/**
	 * 提取集合中的对象的属性,组合成列表.
	 * 
	 * @param collection
	 *            来源集合.
	 * @param propertityName
	 *            要提取的属性名.
	 */
	@SuppressWarnings("unchecked")
	public static List fetchPropertyToList(Collection collection,
			String propertyName) throws Exception {

		List list = new ArrayList();
		if(CollectionUtils.isEmpty(collection)){return null;}
		for (Object obj : collection) {
			list.add(PropertyUtils.getProperty(obj, propertyName));
		}

		return list;
	}

	/**
	 * 提取集合中的对象的属性,组合成由分割符分隔的字符串.
	 * 
	 * @param collection
	 *            来源集合.
	 * @param propertityName
	 *            要提取的属性名.
	 * @param separator
	 *            分隔符.
	 */
	@SuppressWarnings("unchecked")
	public static String fetchPropertyToString(Collection collection,
			String propertyName, String separator) throws Exception {
		List list = fetchPropertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * 根据对象ID集合,整理合并集合.
	 * 
	 * 整理算法为：在源集合中删除不在ID集合中的元素,创建在ID集合中的元素并对其ID属性赋值并添加到源集合中.
	 * 多用于根据http请求中的id列表，修改对象所拥有的子对象集合.
	 * 
	 * @param collection
	 *            源对象集合
	 * @param retainIds
	 *            目标集合
	 * @param clazz
	 *            集合中对象的类型
	 * 
	 * @see #retainById(Collection, Collection, String, Class)
	 */
	public static <T, ID> void mergeByCheckedIds(Collection<T> collection,
			Collection<ID> checkedIds, Class<T> clazz) throws Exception {
		mergeByCheckedIds(collection, checkedIds, "id", clazz);
	}

	/**
	 * 根据对象ID集合,整理合并集合.
	 * 
	 * http请求发送变更后的子对象id列表时，hibernate不适合删除原来子对象集合再创建一个全新的集合 需采用以下整合的算法：
	 * 在源集合中删除不在ID集合中的元素,创建在ID集合中的元素并对其ID属性赋值并添加到源集合中.
	 * 
	 * @param collection
	 *            源对象集合
	 * @param retainIds
	 *            目标集合
	 * @param idName
	 *            对象中ID的属性名
	 * @param clazz
	 *            集合中对象的类型
	 */
	public static <T, ID> void mergeByCheckedIds(Collection<T> collection,
			Collection<ID> checkedIds, String idName, Class<T> clazz)
			throws Exception {

		if (checkedIds == null) {
			collection.clear();
			return;
		}

		Iterator<T> it = collection.iterator();

		while (it.hasNext()) {
			T obj = it.next();
			if (checkedIds.contains(PropertyUtils.getProperty(obj, idName))) {
				checkedIds.remove(PropertyUtils.getProperty(obj, idName));
			} else {
				it.remove();
			}
		}

		for (ID id : checkedIds) {
			T obj = clazz.newInstance();
			PropertyUtils.setProperty(obj, idName, id);
			collection.add(obj);
		}
	}

	/*
	 * 提取集合中的对象的属性,组合成由分割符分隔的字符串.
	 * 主要用于freemarker页面生成的下拉菜单，数据格式为：{'-1':'','0':'是','1':'否'}
	 */
	@SuppressWarnings("unchecked")
	public static String fetchPropertyToString2(Collection collection,
			String propertyNameByKey, String propertyNameByValue)
			throws Exception {
		StringBuilder str = new StringBuilder();
		str.append("{");
		for (Object obj : collection) {
			str.append("'");
			str.append(PropertyUtils.getProperty(obj, propertyNameByKey));
			str.append("':'");
			str.append(PropertyUtils.getProperty(obj, propertyNameByValue));
			str.append("',");
		}
		return StringUtils.substringBeforeLast(str.toString(), ",")+ "}";
	}

	/*
	 * 根据页面提交的参数拼接HQL
	 */
	@SuppressWarnings("unchecked")
	public static String getHql(HttpServletRequest request, String entityName,
			Object object) throws Exception {
		StringBuilder str = new StringBuilder();
		String className = StringUtils.capitalize(entityName);
		str.append("from " + className + " o where 1=1 ");
		BeanWrapper wrapper = new BeanWrapperImpl(object);
		//从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
		Map<String, Object> filterParamMap = WebUtils.getParametersStartingWith(request, entityName + ".");
		//分析参数Map,构造PropertyFilter列表
		for (Map.Entry<String, Object> entry : filterParamMap.entrySet()) {
			String filterName = entry.getKey();
			String value = entry.getValue().toString();
			//如果value值为空,则忽略此filter.
			if (StringUtils.isNotBlank(value)) {
				Class c = wrapper.getPropertyType(filterName);
				if (c.getName().equalsIgnoreCase("java.lang.String")) {
					str.append(" and o." + filterName);
					str.append(" like '%" + value + "%'");
				}
				//这里可以接着扩展，判断其他类型
			}
		}
		return str.toString();
	}
}

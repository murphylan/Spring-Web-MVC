package san.dao.dictionary;

import org.springframework.stereotype.Repository;

import san.entity.dictionary.Dictionary;

import com.orm.HibernateDao;

@Repository
public class DictionaryDao extends HibernateDao<Dictionary, String> {

}

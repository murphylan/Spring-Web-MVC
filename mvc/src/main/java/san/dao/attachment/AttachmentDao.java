package san.dao.attachment;

import org.springframework.stereotype.Repository;

import com.orm.HibernateDao;
import san.entity.attachment.Attachment;
@Repository
public class AttachmentDao extends HibernateDao<Attachment,String>{
	/**
	 * 自动生成AttachmentDao类.
	 */
	 private static final long serialVersionUID = 4421466894064648958L;
}

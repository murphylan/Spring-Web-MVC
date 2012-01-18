package san.entity.tree;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.orm.BaseEntity;
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name="start_tree")
public class Tree extends BaseEntity {

	private static final long serialVersionUID = 2306848763572534082L;

	private String name;
	
	private String type;
	
	private String description;
	
	private String position;
	
	private String parentId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	
	
}

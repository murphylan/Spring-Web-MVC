package san.entity.dictionary;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.orm.BaseEntity;

@Entity
@Table(name = "start_dictionary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dictionary extends BaseEntity {
	/**
	 * 字典表
	 */
	private static final long serialVersionUID = -4080918385669272281L;
	@NotNull
	@Size(min = 1, max = 25,message="Length must be greater than 1")
	private String name;
	@NotNull
	@Size(min = 1, max = 25,message="Length must be greater than 1")
	private String description;
	@NotNull
	@Size(min = 1, max = 25,message="Length must be greater than 1")
	private String type;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

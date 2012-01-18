package san.web.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6881807093365605204L;
	private Long resourceId;
	@SuppressWarnings("unused")
	private String entityId;
	public ResourceNotFoundException(Long resourceId) {
		this.resourceId = resourceId;
	}
	public ResourceNotFoundException(String entityId) {
		this.entityId = entityId;
	}
	public Long getResourceId() {
		return resourceId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	
}

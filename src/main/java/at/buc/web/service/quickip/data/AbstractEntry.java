package at.buc.web.service.quickip.data;

import java.util.UUID;

import org.springframework.hateoas.ResourceSupport;

public abstract class AbstractEntry extends ResourceSupport{
	//metadata
	protected final String uuid;
	protected String name;
	protected String action;
	protected boolean isTemplate;
	
	public AbstractEntry(String name,  boolean isTemplate) {
		this.uuid=UUID.randomUUID().toString();
		this.name=name;
		this.isTemplate=isTemplate;
	}

	public abstract String getAction();
	
	public String getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public boolean isTemplate() {
		return isTemplate;
	}

	public void setTemplate(boolean isTemplate) {
		this.isTemplate = isTemplate;
	}
	
}

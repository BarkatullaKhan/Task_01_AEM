package aemApp.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TeaserModel {
	
	@ValueMapValue
	private String teaserTitle;
	
	@ValueMapValue
	private String teaserDescription;
	
	@ValueMapValue
	private String teaserImage;

	public String getTeaserTitle() {
		return teaserTitle;
	}

	public String getTeaserDescription() {
		return teaserDescription;
	}

	public String getTeaserImage() {
		return teaserImage;
	}
	
	

}

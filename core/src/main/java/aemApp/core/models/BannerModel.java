package aemApp.core.models;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Required;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = SlingHttpServletRequest.class,
defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BannerModel {

	@ValueMapValue
	private String bannerTitle;

	@ValueMapValue
	private String description;

	@ValueMapValue
	private String bannerImage;

	@ValueMapValue
	private String actionText;

	@ValueMapValue
	private String actionLink;

	public String getBannerTitle() {
		return bannerTitle;
	}

	public String getDescription() {
		return description;
	}

	public String getBannerImage() {
		return bannerImage;
	}

	public String getActionText() {
		return actionText;
	}

	public String getActionLink() {
		return actionLink;
	}

}

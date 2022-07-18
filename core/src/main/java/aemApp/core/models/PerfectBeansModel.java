package aemApp.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = SlingHttpServletRequest.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PerfectBeansModel {
    
    @ValueMapValue
    private String leftImage;

    @ValueMapValue
    private String leftTitle;

    @ValueMapValue
    private String leftDescription;

    @ValueMapValue
    private String rightTitle;

    @ValueMapValue
    private String rightDescription;

    @ValueMapValue
    private String rightImage;

	public String getLeftImage() {
		return leftImage;
	}

	public String getLeftTitle() {
		return leftTitle;
	}

	public String getLeftDescription() {
		return leftDescription;
	}

	public String getRightTitle() {
		return rightTitle;
	}

	public String getRightDescription() {
		return rightDescription;
	}

	public String getRightImage() {
		return rightImage;
	}

    
    
}

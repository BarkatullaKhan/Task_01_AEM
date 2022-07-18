package aemApp.core.models;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;

public class Teasers{

    private String teaserTitle;
    private String teaserDescription;
    private String teaserImage;

    public Teasers(Resource resource){
        try {
            if (StringUtils.isNotBlank(resource.getValueMap().get("teaserTitle",String.class))) {
               this.teaserTitle=resource.getValueMap().get("teaserTitle",String.class) ;
            }
            if (StringUtils.isNotBlank(resource.getValueMap().get("teaserDescription",String.class))) {
                this.teaserDescription=resource.getValueMap().get("teaserDescription",String.class);
            }
            if (StringUtils.isNotBlank(resource.getValueMap().get("teaserImage",String.class))) {
             this.teaserImage=resource.getValueMap().get("teaserImage",String.class);   
            }
            
        } catch (Exception e) {
            //TODO: handle exception
        }
        
        
    }

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

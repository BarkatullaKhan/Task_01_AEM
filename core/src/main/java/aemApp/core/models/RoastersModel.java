package aemApp.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;


@Model(adaptables = SlingHttpServletRequest.class,
defaultInjectionStrategy = DefaultInjectionStrategy.REQUIRED)
public class RoastersModel {
	
	@Inject

    Resource componentResource;
	
	public List<Roasters> getRoasters() {
        List<Roasters> roastersList=new ArrayList<>();
        try {
            Resource roasters=componentResource.getChild("roasterItems");
            if(roasters!=null){
                for (Resource item : roasters.getChildren()) {
                	Roasters newItem=new Roasters(item);
                   
                	roastersList.add(newItem);
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
       
        return roastersList;
	}
	
	
	 public class Roasters {
//		    private static final Logger LOG = LoggerFactory.getLogger(MultifieldHelper.class);
		    private String 	roasterImg;
		    private String 	pageLink;
		    private String 	title;
		    private String 	description;
		   

		    public Roasters(Resource resource){
		        try {
		            if(StringUtils.isNotBlank(resource.getValueMap().get("roasterImg", String.class))) {
		                this.roasterImg = resource.getValueMap().get("roasterImg", String.class);
		            }
		            if(StringUtils.isNotBlank(resource.getValueMap().get("pageLink", String.class))) {
		            	this.pageLink = resource.getValueMap().get("pageLink", String.class);
		            }
		            if(StringUtils.isNotBlank(resource.getValueMap().get("title", String.class))) {
		            	this.title = resource.getValueMap().get("title", String.class);
		            }
		            if(StringUtils.isNotBlank(resource.getValueMap().get("description", String.class))) {
		            	this.description = resource.getValueMap().get("description", String.class);
		            }
		            

		        }catch (Exception e){
//		            LOG.info("\n BEAN ERROR : {}",e.getMessage());
		        }

		    }


			public String getRoasterImg() {
				return roasterImg;
			}


			public String getPageLink() {
				return pageLink;
			}


			public String getTitle() {
				return title;
			}


			public String getDescription() {
				return description;
			}
		    
		    
		   
		}


	
}



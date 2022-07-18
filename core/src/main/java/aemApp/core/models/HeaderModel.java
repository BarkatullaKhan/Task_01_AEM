package aemApp.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;



@Model(adaptables = SlingHttpServletRequest.class,
defaultInjectionStrategy = DefaultInjectionStrategy.REQUIRED)
public class HeaderModel {
	
	
	
	@Inject

    Resource componentResource;
	
	@ValueMapValue
    @Default(values = "logoPath")
    private String logoPath;
	

	public String getLogoPath() {
		return logoPath;
	}





	public List<Items> getHeader() {
        List<Items> itemList=new ArrayList<>();
        try {
            Resource itemNested=componentResource.getChild("allItems");
            if(itemNested!=null){
                for (Resource item : itemNested.getChildren()) {
                	Items newItem=new Items(item);
                   
                	itemList.add(newItem);
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
       
        return itemList;
    }
	
	
	
	

	 public class Items {
//	    private static final Logger LOG = LoggerFactory.getLogger(MultifieldHelper.class);
	    private String 	itemName;
	    private String 	linkAddress;
	   

	    public Items(Resource resource){
	        try {
	            if(StringUtils.isNotBlank(resource.getValueMap().get("itemName", String.class))) {
	                this.itemName = resource.getValueMap().get("itemName", String.class);
	            }
	            if(StringUtils.isNotBlank(resource.getValueMap().get("linkAddress", String.class))) {
	                this.linkAddress = resource.getValueMap().get("linkAddress", String.class);
	            }
	            

	        }catch (Exception e){
//	            LOG.info("\n BEAN ERROR : {}",e.getMessage());
	        }

	    }


		public String getItemName() {
			return itemName;
		}
		public String getLinkAddress() {
			return linkAddress;
		}

	    
	
	

	   
	}

}

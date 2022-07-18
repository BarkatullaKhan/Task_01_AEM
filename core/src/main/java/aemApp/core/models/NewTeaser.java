package aemApp.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NewTeaser {

    @Inject @Via("resource")
    Resource componentResource;

    public List<Teasers> getTeasers(){
        List<Teasers> teasersList=new ArrayList<>();

        try {
            Resource allTeasers=componentResource.getChild("allTeasers");

            if (allTeasers!=null) {
                for(Resource teaser:allTeasers.getChildren()){
                    Teasers newTeaser=new Teasers(teaser);
                    teasersList.add(newTeaser);
                    
                }
            }
        } catch (Exception e) {
            //TODO: handle exception
        }

        return teasersList;
    }

    
}

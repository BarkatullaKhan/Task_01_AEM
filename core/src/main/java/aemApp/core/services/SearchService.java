package aemApp.core.services;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

import aemApp.core.utils.ResolverUtil;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.jcr.Value;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = SearchService.class)
public class SearchService{

    private static final Logger LOG= LoggerFactory.getLogger(SearchService.class);

    @Reference
    QueryBuilder queryBuilder;

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    @Activate
    public void activate(){
        LOG.info("\n ----ACTIVATE METHOD----");
    }

    public Map<String,String> createTextSearchQuery(String id){
        Map<String,String> queryMap=new HashMap<>();
        queryMap.put("path","/content/aemApp");
        queryMap.put("type","cq:PageContent");
        queryMap.put("property","id");
        queryMap.put("property.value",id);
        return queryMap;
    }

    
    public JSONObject searchResult(String id,PrintWriter out){
        LOG.info("\n ----SEARCH RESULT--------");
        JSONObject searchResult=new JSONObject();
        try {
            ResourceResolver resourceResolver = ResolverUtil.newResolver(resourceResolverFactory);
            final Session session = resourceResolver.adaptTo(Session.class);
            Query query = queryBuilder.createQuery(PredicateGroup.create(createTextSearchQuery(id)), session);


            SearchResult result = query.getResult();
            

//            int perPageResults = result.getHits().size();
//            long totalResults = result.getTotalMatches();
//            long startingResult = result.getStartIndex();
//            double totalPages = Math.ceil((double) totalResults / (double) resultPerPage);
//
//            searchResult.put("perpageresult",perPageResults);
//            searchResult.put("totalresults",totalResults);
//            searchResult.put("startingresult",startingResult);
//            searchResult.put("pages",totalPages);


            List<Hit> hits =result.getHits();
            JSONArray resultArray=new JSONArray();
           
            for(Hit hit: hits){
                Node node=hit.getResource().adaptTo(Node.class);
                JSONObject resultObject=new JSONObject();
                Value [] tags=node.getProperty("cq:tags").getValues();
                List<String> tagList=new ArrayList<String>();
                for(Value tag:tags){
                	tagList.add(tag.toString());
                }
                resultObject.put("tags",tagList);
                resultObject.put("description",node.getProperty("jcr:description").getString());
                resultArray.put(resultObject);
                LOG.info("\n Page {} ",node.getPath());
            }
            searchResult.put("searchResults",resultArray);

        }catch (Exception e){
            LOG.info("\n ----ERROR -----{} ",e.getMessage());
            out.write(e.toString());
        }
        return searchResult;
    }
}
package aemApp.core.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.Value;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Servlet to get Content Fragments based on tags authored.
 * Call the servlet using below url
 * localhost:4502/bin/querybuilder?queryparam=/content/we-retail/ca/en/experience
 */
@Component(service = Servlet.class, property = { "sling.servlet.methods=" + "GET",
		"sling.servlet.paths=" + "/aemApp/myService/api/v2", "sling.servlet.extensions=" + "json" })
public class SearchServletV2 extends SlingAllMethodsServlet {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4202969241216634342L;
	/** The Logger */
	private static final Logger LOG = LoggerFactory.getLogger(SearchServletV2.class);

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject searchResult=new JSONObject();
		JSONArray resultArray=new JSONArray();
		try {
			
			String queryparam=request.getParameter("espotId").trim();
			// Create the query
			String query = createQuery(queryparam, response);
			if (query == null) {
				return;
			}
			
			ResourceResolver resolver = request.getResourceResolver();
			Iterator<Resource> results = resolver.findResources(query, "JCR-SQL2");
			
			// Process the results and add them to the Json Array
			
			while (results.hasNext()) {
				
				Resource result = results.next();
				response.setContentType("json");
				
				response.setHeader("Cache-Control", "max-age=0");
				// Removing the ending Newline character and carriage return characters
//				out.println(result.getPath().toString());
				
				Node jcrNode=result.adaptTo(Node.class);
				JSONObject searchNode=new JSONObject();
				
				List<String> tagList=new ArrayList<String>();
				
				Value [] tags=jcrNode.getProperty("cq:tags").getValues();
				for(Value v:tags){
					tagList.add(v.toString());
				}

				
				
				searchNode.put("tags",tagList);
				searchNode.put("description", jcrNode.getProperty("jcr:description").getString());
				resultArray.put(searchNode);
				
				}
			 searchResult.put("results",resultArray);
			 out.write(searchResult.toString());
			out.flush();
			out.close();
		
		} catch (Exception e) {
			LOG.error("Exception in AppCFServlet " + e.getMessage());
			out.println(e.toString());
		}
	}

	
	private String createQuery(String param, SlingHttpServletResponse response) throws IOException {
//			
//		StringBuilder query = new StringBuilder(
//				"SELECT * FROM [dam:Asset] WHERE ISDESCENDANTNODE (["+param+"])");
		StringBuilder query = new StringBuilder(
				"SELECT * FROM [cq:PageContent] as node WHERE ISDESCENDANTNODE ([/content/aemApp]) AND contains(node.[id],\""+param+"\")");
//		
	
		LOG.info("Query: " + query.toString());
		return query.toString();
	}

	
}

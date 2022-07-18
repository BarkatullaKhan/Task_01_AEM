package aemApp.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.TidyJsonItemWriter;
import com.day.cq.replication.PathNotFoundException;
import com.day.cq.wcm.api.WCMMode;

@Component(service = Servlet.class,
property = {
"sling.servlet.methods=" + "GET",
"sling.servlet.paths=" + "/aemApp/bin/api/content/v1",
"sling.servlet.extensions=" + "json"})
public class RestServlet extends SlingSafeMethodsServlet {
	
	
	/**
	 * Default long serial version ID
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(RestServlet.class);

		
	
	
	Resource resource;
	@Reference
	private ResourceResolverFactory resolverFactory;
	     
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException{
		WCMMode wcmMode = WCMMode.fromRequest(request);
	
		String nodePath="/content/aemApp/en/"+request.getParameter("page");
		
		response.setContentType("json");
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control","max-age=0");
		JSONObject jsonObject = new JSONObject();
		
		try {
			Node node = null;

			resource = request.getResourceResolver().getResource(nodePath);
			
			if (resource != null) {

				node = resource.adaptTo(Node.class);
				
				NodeIterator nodeItr = node.getNodes();

				iterate(nodeItr,out,nodePath);
				 //out.println("JSON data : "+jsonObject);

			}
			else
			{
				out.write("{Please provide correct parameters ex: Host:/aemApp/bin/api/content/v1?page=home}");
			}
		}

		catch (Exception e) {
			out.print(e);
		}
		out.flush();
		out.close();
	}
	@SuppressWarnings("deprecation")
	private void iterate(NodeIterator nodeItr,PrintWriter out,String strpath)
			throws RepositoryException, ValueFormatException, PathNotFoundException, IOException, JSONException {
		JSONObject jsonObject = null;
		int i=1;
		
			while (nodeItr.hasNext()) {
			Node cNode = nodeItr.nextNode();
			//response.println("CNode vallue"+cNode);

			
			final StringWriter stringWriter = new StringWriter();
			
			TidyJsonItemWriter jsonWriter = new TidyJsonItemWriter(null);


			final Set<String> propertiesToIgnore = new HashSet();
			  propertiesToIgnore.add("jcr:lastModifiedBy");
			  propertiesToIgnore.add("jcr:primaryType");
			  propertiesToIgnore.add("cq:lastModifiedBy");
			  propertiesToIgnore.add("jcr:created");
			  propertiesToIgnore.add("jcr:createdBy");
			  propertiesToIgnore.add("cq:template");
			  
			  propertiesToIgnore.add("jcr:lastModified");
			  propertiesToIgnore.add("cq:lastModified");
			  propertiesToIgnore.add("jcr:mixinTypes");
			  propertiesToIgnore.add("jcr:versionHistory");
			  propertiesToIgnore.add("jcr:baseVersion");
			  propertiesToIgnore.add("sling:resourceType");
//			  propertiesToIgnore.add("root.responsivegrid.footer");
//			  propertiesToIgnore.add("jcr:title");
			  jsonWriter = new TidyJsonItemWriter(propertiesToIgnore);
			
		
			try {
				jsonWriter.dump(cNode, stringWriter, -1);
			} catch (org.apache.sling.commons.json.JSONException e) {
				e.printStackTrace();
			}
			try {
				jsonObject = new JSONObject(stringWriter.toString());
				//System.out.println("Hello tag"+jsonObject.toString());
				//System.out.println("Hello tag"+jsonObject.optString("cq:tags"));
				out.write(jsonObject.toString());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		
			}
	}
		  
	


	//Method to separate the tags

	
	
	

}

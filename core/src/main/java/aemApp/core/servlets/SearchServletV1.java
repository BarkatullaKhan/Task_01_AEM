package aemApp.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aemApp.core.services.SearchService;

@Component(service = Servlet.class, property = { "sling.servlet.methods=" + "GET",
		"sling.servlet.paths=" + "/aemApp/mySearchService/api/v1", "sling.servlet.extensions=" + "json" })
public class SearchServletV1 extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = LoggerFactory.getLogger(SearchServletV1.class);
	
	@Reference
	SearchService searchService;
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		
		final PrintWriter out = response.getWriter();
		
		
		String id=request.getParameter("espotId").trim();
		
		try {
			 
			 
			 JSONObject jsonObject= searchService.searchResult(id,out);
			 out.write(jsonObject.toString());
		} catch (Exception e) {
			
			out.write("Something went wrong");
			out.write(e.toString());
		}
		 
		
		
	}
}

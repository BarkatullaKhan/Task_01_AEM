package aemApp.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.WCMMode;

@Component(service = Servlet.class, property = { "sling.servlet.methods=" + "GET",
		"sling.servlet.paths=" + "/aemApp/bin/api/content/v6", "sling.servlet.extensions=" + "json" })
public class RestV6 extends SlingSafeMethodsServlet {

	/**
	 * Default long serial version ID
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(RestServlet.class);

	Resource resource;
	@Reference
	private ResourceResolverFactory resolverFactory;
	static List<String> componentsList = new ArrayList<String>();
	static ConvertResourceToJson convertToJSON = new ConvertResourceToJson();

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		WCMMode wcmMode = WCMMode.fromRequest(request);

		componentsList = new ArrayList<>();
		iteration = 0;

		String nodePath = "/content/aemApp/en/" + request.getParameter("page");

		response.setContentType("json");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "max-age=0");

		final PrintWriter out = response.getWriter();

		out.write("{");
		try {
			Node node = null;

			resource = request.getResourceResolver().getResource(nodePath);

			if (resource != null) {

				node = resource.adaptTo(Node.class);

				NodeIterator nodeItr = node.getNodes();
				

				while (nodeItr.hasNext()) {
					Node cNode = nodeItr.nextNode();
					if (cNode.getName().equals("root")) {
						nodeItr = cNode.getNodes();
						continue;

					}
					if (cNode.getName().equals("jcr:content")) {
						nodeItr = cNode.getNodes();
						continue;

					}
					if (cNode.getName().contains("responsivegrid")) {
						iterateNodes(cNode, out);
					}

					
				}

				out.write("}");

			} else {
				out.write("\""+"status"+"\""+":404}");
			}
		}

		catch (Exception e) {
			out.print(e);
			out.write("e");
		}
		out.flush();
		out.close();
	}

	static int iteration = 0;

	static void iterateNodes(Node node, PrintWriter out) {

		boolean respFlag = false;
		try {
			NodeIterator nIte = node.getNodes();

			while (nIte.hasNext()) {
				Node cNode = nIte.nextNode();

				if (cNode.getName().contains("responsivegrid")) {

					iterateNodes(cNode, out);
					respFlag = true;

					continue;

				} else {
					respFlag = false;
				}
				if (cNode.getName().contains("cq:responsive")) {

					continue;
				}

				if (iteration > 0) {
					if (respFlag == true) {
						if (nIte.getSize() > 1) {
							out.write(",");
						}
					} else {
						out.write(",");
					}
				}

				out.write("\"" + getComponentNameFromResourceString(cNode.getProperty("sling:resourceType").getString())
						+ "\"" + ":" + convertToJSON.resourceToJSON(cNode).toString());

				iteration++;

			}

		} catch (Exception e) {
			out.print(e);
			out.write("e");
		}

	}

	static String getComponentNameFromResourceString(String resourceName) {
		String name = "";

		name = resourceName.substring(resourceName.lastIndexOf('/') + 1, resourceName.length());
		componentsList.add(name);
		if (Collections.frequency(componentsList, name) > 1) {
			name = name + "_" + (Collections.frequency(componentsList, name) - 1);
		}

		return name;
	}

}

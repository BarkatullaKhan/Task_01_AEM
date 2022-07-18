package aemApp.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
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
		"sling.servlet.paths=" + "/aemApp/bin/api/content/v5", "sling.servlet.extensions=" + "json" })
public class RestV5 extends SlingSafeMethodsServlet {

	/**
	 * Default long serial version ID
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(RestServlet.class);

	Resource resource;
	@Reference
	private ResourceResolverFactory resolverFactory;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		WCMMode wcmMode = WCMMode.fromRequest(request);

		String nodePath = "/content/aemApp/en/" + request.getParameter("page");

		response.setContentType("json");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "max-age=0");

		final PrintWriter out = response.getWriter();

		ConvertResourceToJson convertToJSON = new ConvertResourceToJson();
		out.write("{");
		try {
			Node node = null;

			resource = request.getResourceResolver().getResource(nodePath);

			if (resource != null) {

				node = resource.adaptTo(Node.class);

				NodeIterator nodeItr = node.getNodes();
				int respLevel = 1;

				

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
					if (cNode.getName().contains("responsivegrid")) { // NOPMD by BarkatullaKhan on 1/7/22 7:32 PM
						// out.write(cNode.getProperty("sling:resourceType").getString());
						if (respLevel == 1) {
							nodeItr = cNode.getNodes();
							respLevel++;
							continue;

						} else if (respLevel > 1) {
							NodeIterator nIte = cNode.getNodes();

							while (nIte.hasNext()) {
								Node newNode = nIte.nextNode();

								if (newNode.getName().contains("cq:responsive")) {

									continue;
								}

								if (nodeItr.hasNext()) {
									out.write("\""
											+ getComponentNameFromResourceString(
													newNode.getProperty("sling:resourceType").getString())
											+ "\"" + ":" + convertToJSON.resourceToJSON(newNode).toString() + ",");
								} else {
									if (nIte.hasNext()) {
										out.write("\""
												+ getComponentNameFromResourceString(
														newNode.getProperty("sling:resourceType").getString())
												+ "\"" + ":" + convertToJSON.resourceToJSON(newNode).toString() + ",");
									} else {
										out.write("\""
												+ getComponentNameFromResourceString(
														newNode.getProperty("sling:resourceType").getString())
												+ "\"" + ":" + convertToJSON.resourceToJSON(newNode).toString());
									}

								}

							}

						}

						continue;
					}

					if (cNode.getName().contains("cq:responsive")) {

						continue;
					}
					if (nodeItr.hasNext()) {
						out.write("\""
								+ getComponentNameFromResourceString(
										cNode.getProperty("sling:resourceType").getString())
								+ "\"" + ":" + convertToJSON.resourceToJSON(cNode).toString() + ",");
					} else {
						out.write("\""
								+ getComponentNameFromResourceString(
										cNode.getProperty("sling:resourceType").getString())
								+ "\"" + ":" + convertToJSON.resourceToJSON(cNode).toString());
					}

				}

				out.write("}");

			} else {
				out.write("{Please provide correct parameters ex: Host:/aemApp/bin/api/content/v1?page=home}");
			}
		}

		catch (Exception e) {
			out.print(e);
			out.write("e");
		}
		out.flush();
		out.close();
	}

	static String getComponentNameFromResourceString(String resourceName) {
		String name = "";
		for (int i = resourceName.length() - 1; i >= 0; i--) {
			if (resourceName.charAt(i) == '/') {
				name = resourceName.substring(i + 1, resourceName.length());
				return name;

			}
		}

		return name;
	}

}

package hr.fer.zemris.java.hw17.rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import hr.fer.zemris.java.hw17.servlets.ExtractorUtil;

/**
 * The TagJSON class provides picture tags in JSON format.
 * @author Alen Carin
 *
 */
@Path("/tags")
public class TagJSON {

	/**
	 * Returns all tags.
	 * @return tags
	 * @throws IOException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTagsList() throws IOException {

		JSONObject result = new JSONObject();
		JSONArray tags = new JSONArray();
		for(String s : ExtractorUtil.getAllTags()) {
			tags.put(s);
		}
		result.put("tags", tags);

		return Response.status(Status.OK).entity(tags.toString()).build();
	}
}

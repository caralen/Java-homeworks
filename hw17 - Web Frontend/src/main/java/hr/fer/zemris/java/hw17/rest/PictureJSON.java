package hr.fer.zemris.java.hw17.rest;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw17.model.Picture;
import hr.fer.zemris.java.hw17.servlets.ExtractorUtil;

/**
 * The Class PictureJSON.
 */
@Path("/pictures")
public class PictureJSON {

	/**
	 * Gets the pictures list.
	 *
	 * @return the pictures list
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNumberOfPictures() throws IOException {
		int n = ExtractorUtil.getNumberOfPictures();

		JSONObject result = new JSONObject();
		result.put("numberOfPictures", n);

		return Response.status(Status.OK).entity(result.toString()).build();
	}

	/**
	 * Gets the picture.
	 *
	 * @param index the index
	 * @return the picture
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Path("{index}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Picture getPicture(@PathParam("index") int index) throws IOException {
		
		int n = ExtractorUtil.getNumberOfPictures();
		if (index < 0 || index >= n) {
			return null;
		}

		Picture p = ExtractorUtil.getPicture(index);
		if (p == null) {
			return null;
		}

		return p;
	}

	/**
	 * Returns response which contains json representation of all pictures that contain.
	 * the tag which is given in url.
	 * @param tag that returned pictures contain
	 * @return
	 * @throws IOException
	 */
	@Path("/tag/{tag}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPicturesForTag(@PathParam("tag") String tag) throws IOException {
		
		List<Picture> pictures = ExtractorUtil.getAllPicturesForTag(tag);

		if (pictures == null) {
			return null;
		}
		
		Picture[] picturesArray = new Picture[pictures.size()];
		pictures.toArray(picturesArray);
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(picturesArray);

		return Response.status(Status.OK).entity(jsonText).build();
	}
	
	/**
	 * Returns the picture which has the given name.
	 * @param name of the picture which will be returned
	 * @return
	 * @throws IOException
	 */
	@Path("/picture/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Picture getPictureForName(@PathParam("name") String name) throws IOException {

		Picture pic = ExtractorUtil.getPictureByName(name);
		if (pic == null) {
			return null;
		}

		return pic;
	}
}

package api;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.util.List;

import Beans.ImageBean;
import Sessions.ImageRemote;
import Sessions.ImageServiceBean;

@Path("/image")
public class ImageWS {

	@Context
	private UriInfo contexte;

	@EJB
	private ImageRemote imageRemote = new ImageServiceBean();

	public ImageWS() {}

	@PUT
	@Consumes("application/json")
	public void putJSON(String json) {

	}

	@GET
	@Path("/getJson")
	@Produces("application/json")
	public String getJson() {
		return "{msg: \"ImageWS\"}";
	}

	@GET
	@Path("/getImages")
	@Produces("application/json")
	public String getImages() throws Exception {
		List<ImageBean> images = this.imageRemote.findAll();
		String str = "[";
		for (int i = 0 ; i < images.size(); i++) {
			str += images.get(i).toString();
			if (i < images.size() - 1) str += ",";
		}
		str += "]";
		return str;
	}

	@POST
	@Path("/addImage")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public Response addImage(@FormParam("ID_ANNONCE") String id_annonce,
							 @FormParam("URL") String url) throws Exception {
		int id = this.imageRemote.getLastId();
		ImageBean image = new ImageBean(id, Integer.valueOf(id_annonce), url);
		this.imageRemote.create(image);
		return Response.status(200).entity(image.toString()).build();
	}

	@POST
	@Path("/deleteImage")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
	public Response deleteImage(@FormParam("ID") String id) throws Exception {
		ImageBean image = this.imageRemote.find((int)Integer.valueOf(id));
		this.imageRemote.remove(image);
		return Response.status(200).entity(image.toString()).build();
	}

}


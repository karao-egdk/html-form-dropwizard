package html.resources;

import org.dalesbred.Database;

import html.entity.Form;
import html.service.imp.FormServiceImplementation;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/form")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FormResource {

	private FormServiceImplementation formServiceImplementation;
	
	public FormResource(Database database) {
		this.formServiceImplementation = new FormServiceImplementation(database);
	}
	
	@POST
	@Path("/add")
	public Response addForm(Form form) {
		return formServiceImplementation.addFormDetails(form);
	}
}

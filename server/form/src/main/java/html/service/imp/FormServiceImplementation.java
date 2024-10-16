package html.service.imp;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.dalesbred.Database;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import html.entity.Form;
import html.repo.FormRepository;
import html.service.FormService;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class FormServiceImplementation implements FormService {
	private FormRepository repo;

	private static final String DATA_SOURCE = "db/table.sql";

	private String generateTableIfExists() throws IOException {
		URL url = Resources.getResource(DATA_SOURCE);
		String tables = Resources.toString(url, Charsets.UTF_8);
		return tables;
	}

	public FormServiceImplementation(Database database) {
		this.repo = new FormRepository(database);
		
		try {
			String tables = generateTableIfExists();
			database.update(tables);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Response addFormDetails(Form form) {
		Map<String, String> res = new HashMap<>();
		
		if (form == null) {
			res.put("message", "Empty data");
			return Response.status(Status.NO_CONTENT).entity(res).build();
		}
		
		try {
			repo.addFormDetails(form);
			
			res.put("message", "form Successfully submitted!");
			return Response.status(Status.ACCEPTED).entity(res).build();
			
		} catch (Exception e) {
			res.put("message", e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(res).build();
		}
	}
}

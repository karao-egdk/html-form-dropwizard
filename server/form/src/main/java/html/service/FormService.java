package html.service;

import html.entity.Form;
import jakarta.ws.rs.core.Response;

public interface FormService {
	Response addFormDetails(Form form);
}

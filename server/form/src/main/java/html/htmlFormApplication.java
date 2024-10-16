package html;

import org.dalesbred.Database;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import html.resources.FormResource;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;

import java.util.EnumSet;

public class htmlFormApplication extends Application<htmlFormConfiguration> {

	public static void main(final String[] args) throws Exception {
		new htmlFormApplication().run(args);
	}

	@Override
	public String getName() {
		return "htmlForm";
	}

	@Override
	public void initialize(final Bootstrap<htmlFormConfiguration> bootstrap) {
		// TODO: application initialization
	}

	@Override
	public void run(final htmlFormConfiguration configuration, final Environment environment) {
		DataSourceFactory config = configuration.getDataSourceFactory();
		final Database db = Database.forUrlAndCredentials(config.getUrl(), config.getUser(), config.getPassword());

		// --- Start of the CORS Configuration --//
		final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

//		cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "http://127.0.0.1:5500/");
		cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
		cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM,
				"X-Requested-With,Content-Type,Accept,Origin,Authorization");
		cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
		cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");
		cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER,
				"X-Requested-With,Content-Type,Accept,Origin,Authorization");
		cors.setInitParameter(CrossOriginFilter.CHAIN_PREFLIGHT_PARAM, Boolean.FALSE.toString());

		// Add URL mapping
		cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
		// --- End of the CORS Configuration --//
		environment.jersey().register(new FormResource(db));

	}

}

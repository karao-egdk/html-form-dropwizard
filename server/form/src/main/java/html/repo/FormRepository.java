package html.repo;

import org.dalesbred.Database;
import org.dalesbred.query.SqlQuery;

import html.dao.FormDao;
import html.entity.Form;

public class FormRepository implements FormDao {
	private Database database;
	
	public FormRepository(Database database) {
		this.database = database;
	}
	
	@Override
	public void addFormDetails(Form form) {
		final String ADD_FORM = "INSERT INTO form (name, age, mobile, email, has_secondary_phone, secondary_mobile, gender, fav_technology) VALUES (:name, :age, :mobile, :email, :hasSecondaryPhone, :secondaryMobile, :gender, :favTechnology)";
		
		database.update(SqlQuery.namedQuery(ADD_FORM, form));
	}
}

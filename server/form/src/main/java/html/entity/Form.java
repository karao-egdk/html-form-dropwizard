package html.entity;

import html.enums.FavTechnology;
import html.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Form {
	private String name;
	private Integer age;
	private String mobile;
	private String email;
	private Boolean hasSecondaryPhone;
	private String secondaryMobile;
	private Gender gender;
	private FavTechnology favTechnology;
}

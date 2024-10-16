CREATE TABLE IF NOT EXISTS form(
	id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	age INT,
	email VARCHAR(50),
	fav_technology VARCHAR(50),
	gender VARCHAR(7),
	has_secondary_phone BOOLEAN,
	mobile VARCHAR(10),
	name VARCHAR(50),
	secondary_phone VARCHAR(10)
);
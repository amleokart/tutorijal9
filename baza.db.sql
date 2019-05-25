CREATE TABLE Bus (
	"bus_id" INTEGER PRIMARY KEY,
	"maker"	TEXT,
	"series"	TEXT,
	"seat_number"	INTEGER,
	"driver_number"	INTEGER,
);

CREATE TABLE Driver (
	"driver_id"	INTEGER PRIMARY KEY,
	"name"	TEXT,
	"surname"	TEXT,
	"JMBG"	TEXT UNIQUE ,
	"bithday"	DATE,
	"employment_date"	DATE,
);
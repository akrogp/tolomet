BEGIN TRANSACTION;
CREATE TABLE "Meteo" (
	`station`	TEXT NOT NULL,
	`stamp`	INTEGER NOT NULL,
	`dir`	INTEGER,
	`med`	REAL,
	`max`	REAL,
	`hum`	REAL,
	`temp`	REAL,
	`pres`	REAL
);
COMMIT;

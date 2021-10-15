DROP DATABASE IF EXISTS heroDB;
CREATE DATABASE heroDB;

USE heroDB;

CREATE TABLE location(
	locId int PRIMARY KEY AUTO_INCREMENT,
    locName VARCHAR(50) NOT NULL,
    locDescription VARCHAR(250),
    street VARCHAR(30),
    street2 VARCHAR(30),
    city VARCHAR(30),
    state CHAR(2),
    zip CHAR(5),
    latitude DOUBLE,
    longitude DOUBLE
);

CREATE TABLE organization(
    orgId INT PRIMARY KEY AUTO_INCREMENT,
    orgName VARCHAR(50) NOT NULL,
    orgDescription VARCHAR(250),
    locId int,
    FOREIGN KEY fk_organization_locId (locId)
		REFERENCES location(locId)
);

CREATE TABLE sighting(
	sightingId int PRIMARY KEY AUTO_INCREMENT,
    locId int NOT NULL,
    sightingDate date NOT NULL,
    sightingDescription VARCHAR(250)
);

CREATE TABLE power(
	powerId INT PRIMARY KEY AUTO_INCREMENT,
    powerName VARCHAR(30) NOT NULL,
    powerDescription VARCHAR(250)
);

CREATE TABLE super(
    superId INT PRIMARY KEY AUTO_INCREMENT,
    superName VARCHAR(50) NOT NULL,
    superDescription VARCHAR(250),
    hero BIT(1) NOT NULL,
    imageFileName VARCHAR(50)
);

CREATE TABLE super_organization(
	superId int NOT NULL,
    orgId int NOT NULL,
    PRIMARY KEY pk_Super_Organization (superId, orgId),
    FOREIGN KEY fk_Super_Organization_SuperId (superId)
		REFERENCES super(superId),
	FOREIGN KEY fk_Super_Organization_OrgId (orgId)
		REFERENCES organization(orgId)
);

CREATE TABLE super_power(
	superId int NOT NULL,
    powerId int NOT NULL,
    PRIMARY KEY pk_Super_Power (superId, powerId),
    FOREIGN KEY fk_Super_Power_SuperId (superId)
		REFERENCES super(superId),
	FOREIGN KEY fk_Super_Power_PowerId (powerId)
		REFERENCES power(powerId)
);

CREATE TABLE super_sighting(
	superId int NOT NULL,
    sightingId int NOT NULL,
    PRIMARY KEY pk_Super_Sighting (superId, sightingId),
    FOREIGN KEY fk_Super_Sighting_SuperId (superId)
		REFERENCES super(superId),
	FOREIGN KEY fk_Super_Sighting_SightingId (sightingId)
		REFERENCES sighting(sightingId)
);

/*




*/

INSERT INTO location
	(locId , locName , locDescription , street , street2 , city , state , zip , latitude , longitude )
    VALUES
    ( 1 , "Avenger's Tower" , "Formerly Stark Tower" , "4 Times Square" , "Suite 100" , "New York" , "NY" , "10030" , "10.3256" , "-36.5489"),
    ( 2 , "Rundown building in Queens" , '"Non-descript"' , "100 Park Lane" , NULL , "New York" , "NY" , "10038" , "10.3285" , "-36.5632"),
    ( 3 , "Baxter Building" , "35 story office building" , "1 Very Real Pl" , NULL , "New York" , "NY" , "10038" , "10.3296" , "-36.5752");

INSERT INTO organization
	(orgId, orgName, orgDescription, locId)
    VALUES
    ( 1 , "The Avengers" , "Saved the world a bunch..." , 1),
    ( 2 , "The Sinister Six" , "A dangerous sextouple" , 2),
	( 3 , "Future Foundation" , "Original Naming" , 3);

/*
INSERT INTO sighting 
	(sightingId, locId , sightingDate, sightingDescription)
    VALUES
    ( 1 , 1 , "2021-09-01" , "Just chillin'" ),
    ( 2 , 2 , "2009-01-01" , "Just palin' around" );
*/

INSERT INTO power
	(powerId , powerName , powerDescription)
    VALUES
    (1 , "Superhuman Strength" , "Real stronk"),
    (2 , "Regeneration" , "Hard to kill"),
    (3 , "Master of Technology" , "Can craft technology to aid themself"),
    (4 , "Silk Spinner" , "Can spin and sling webs");

INSERT INTO super 
	(superId , superName , superDescription, hero)
    VALUES
    (1 , "The Hulk" , "Green and often Mean" , 1),
    (2 , "Spider-man" , "Tingly senses" , 1),
    (3 , "Doc Ock" , "Slimy dude" , 0);

INSERT INTO super_organization(superId, orgId)
	VALUES
    (1,1),
    (2,1),
    (3,2),
    (2,3);

INSERT INTO super_power
	(superId , powerId)
    VALUES
    (1,1),
    (1,2),
    (2,4),
    (3,3);

/*
INSERT INTO super_sighting
	(superId, sightingId)
    VALUES
    (1,1),
    (1,2),
    (2,1),
    (2,2),
    (3,2);
    ;
*/

    
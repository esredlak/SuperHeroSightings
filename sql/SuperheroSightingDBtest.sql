DROP DATABASE IF EXISTS heroDBtest;
CREATE DATABASE heroDBtest;

USE heroDBtest;

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
    hero BIT(1) NOT NULL
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
create database if not exists LPDB_Database;

use LPDB_Database;

alter table IMAGE drop foreign key fk_ID_ANNONCE;
alter table ANNONCE drop foreign key fk_ID_UTILISATEUR;

drop table if exists IMAGE, ANNONCE, UTILISATEUR;
 
create table UTILISATEUR (
	ID int not null auto_increment,
	NOM varchar(255),
	PRENOM varchar(255),
	PSEUDONYME varchar(255),
	ADRESSE varchar(255),
	MDP varchar(255),
	ADMINISTRATEUR int default 0,
	primary key (ID),
	unique(PSEUDONYME)
);

create table ANNONCE (
	ID int not null auto_increment,
	ID_UTILISATEUR int not null,
	TITRE varchar(255),
	PRIX float,
	DESCRIPTION varchar(1024),
	CREATION date,
	TYPE varchar(255),
	primary key (ID),
	constraint fk_ID_UTILISATEUR foreign key (ID_UTILISATEUR) references UTILISATEUR(ID)
);

create table IMAGE (
	ID int not null auto_increment,
	ID_ANNONCE int not null,
	URL varchar(1024),
	primary key (ID),
	constraint fk_ID_ANNONCE foreign key (ID_ANNONCE) references ANNONCE(ID)
);

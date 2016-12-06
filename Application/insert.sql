insert into UTILISATEUR (NOM, PRENOM, PSEUDONYME, ADRESSE, MDP, ADMINISTRATEUR) values ('Nouguier', 'Thibaut', 'Babou', 'Praxus City', 'toto', 1);
insert into UTILISATEUR (NOM, PRENOM, PSEUDONYME, ADRESSE, MDP, ADMINISTRATEUR) values ('Ramel', 'Régis', 'Linkdirt', 'Praxus City', 'titi', 0);

insert into ANNONCE (ID_UTILISATEUR, TITRE, PRIX, DESCRIPTION, CREATION, TYPE) values (1, 'Ordinateur à prix cassé', 500, 'Ordinateur à prix cassé pour la mondique somme de 500€', curdate(), 'Informatique');
insert into ANNONCE (ID_UTILISATEUR, TITRE, PRIX, DESCRIPTION, CREATION, TYPE) values (1, 'Vélo', 50, 'Vélo pour enfant de 2 à 5 ans', curdate(), 'Jouet');

insert into IMAGE(ID_ANNONCE, URL) values (1, 'http://www.cekane.fr/wp-content/uploads/2015/10/googlelogosept12015.png');
insert into IMAGE(ID_ANNONCE, URL) values (2, 'http://www.cekane.fr/wp-content/uploads/2015/10/googlelogosept12015.png');

DROP DATABASE IF EXISTS gestionprojets;

CREATE DATABASE gestionprojets;
USE gestionprojets;

CREATE TABLE utilisateur(
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255)
);

CREATE TABLE projet (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nom_projet VARCHAR(255) UNIQUE NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    description TEXT,
    createur INTEGER NOT NULL,
    actif BOOLEAN DEFAULT TRUE NOT NULL,
    FOREIGN KEY (createur) REFERENCES Utilisateur (id),
    CONSTRAINT date_coherente CHECK(date_fin > date_debut)
);

CREATE TABLE etattache (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nom_etat VARCHAR(255) NOT NULL,
    ordre INTEGER NOT NULL
);

CREATE TABLE tache (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	nom_tache VARCHAR(255) NOT NULL,
    date_realisation_prevue DATE,
    description TEXT,
    projet INTEGER NOT NULL,
    createur INTEGER NOT NULL,
    etat INTEGER NOT NULL,
    importance BOOLEAN NOT NULL,
    FOREIGN KEY (projet) REFERENCES projet (id),
    FOREIGN KEY (createur) REFERENCES utilisateur (id),
    FOREIGN KEY (etat) REFERENCES etattache (id)
);

CREATE TABLE affectationtache (
	utilisateur INTEGER,
    tache INTEGER,
    PRIMARY KEY (utilisateur, tache),
    FOREIGN KEY (utilisateur) REFERENCES utilisateur (id),
    FOREIGN KEY (tache) REFERENCES tache (id)
);

/**
 * Fixtures
 */
INSERT INTO utilisateur (nom) VALUES 
	('Bob'), 
    ('Clara'), 
    ('Damien'), 
    ('Léa');

INSERT INTO projet (nom_projet, date_debut, date_fin, description, createur, actif) VALUES
	('Test 1', '2023-05-04', '2023-06-15', '', 1, TRUE),
    ('Projet de Bob', '2023-05-30', '2028-05-18', 'DESC', 1, TRUE),
    ('Projet de Bob 2', '2022-07-12', '2022-08-18', 'DESC', 1, FALSE);

INSERT INTO etattache (nom_etat, ordre) VALUES
	('ETAT TEST', 1);
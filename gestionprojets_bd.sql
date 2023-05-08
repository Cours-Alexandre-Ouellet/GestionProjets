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

/**
 * Fixtures
 */
INSERT INTO Utilisateur (nom) VALUES ('Bob');

INSERT INTO Projet (nom_projet, date_debut, date_fin, description, createur, actif) VALUES
	('Test 1', '2023-05-04', '2023-06-15', '', 1, TRUE),
    ('Projet de Bob', '2023-05-30', '2028-05-18', 'DESC', 1, TRUE),
    ('Projet de Bob', '2022-07-12', '2022-05-18', 'DESC', 1, FALSE);


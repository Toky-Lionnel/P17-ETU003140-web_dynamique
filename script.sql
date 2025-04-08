CREATE DATABASE exam_web_dyn;

USE exam_web_dyn;

CREATE TABLE Prevision
(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(40),
    montant INT
);

CREATE TABLE Depense
(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    id_prevision INT,
    montant INT,
    FOREIGN KEY (id_prevision) REFERENCES Prevision(id)
);

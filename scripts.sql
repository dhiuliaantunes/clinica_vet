--Dhiulia e Mariana
USE clinica;

select tipo from Pet;

select * from clinica.Pet;

CREATE TABLE IF NOT EXISTS clinica.Pet (
 id INT(11) NOT NULL AUTO_INCREMENT,
 nome VARCHAR(50),
 tipo VARCHAR(50),
 sexo VARCHAR(20),
 idade int,
 raca VARCHAR(25),
 peso int,
 id_dono int not null,
 PRIMARY KEY (id));
 
 CREATE TABLE IF NOT EXISTS clinica.Dono(
	id int(11) not null auto_increment,
    nome varchar(120),
	contato int,
    PRIMARY KEY(id)
 );
 
  CREATE TABLE IF NOT EXISTS clinica.Veterinario(
	id int(11) not null auto_increment,
    nome varchar(120),
	contato int(15),
    PRIMARY KEY(id)
 );
 
  CREATE TABLE IF NOT EXISTS clinica.Consulta(
	id int(11) not null auto_increment,
    id_pet int(11) not null,
    id_veterinario int(11) not null,
    data_consulta datetime,
    receita varchar(500),
	preco float,
    foi_realizada boolean,
    foi_cancelada boolean,
    PRIMARY KEY(id)
 );
 
ALTER TABLE clinica.Pet ADD CONSTRAINT fk_pet_dono FOREIGN KEY ( id_dono) REFERENCES clinica.Dono (id);
ALTER TABLE clinica.Consulta ADD CONSTRAINT fk_consulta_pet FOREIGN KEY ( id_pet) REFERENCES clinica.Pet (id);
ALTER TABLE clinica.Consulta ADD CONSTRAINT fk_consulta_veterinario FOREIGN KEY (id_veterinario) REFERENCES clinica.Veterinario (id);

select * from clinica.dono;

insert into clinica.Dono(nome, contato) values('Dhiulia', 996773860);
insert into clinica.Pet(nome, tipo, sexo, idade, raca, peso, id_dono) values('Lola', 'Cachorro', 'Fêmea', 8, 'Vira-Lata', 5, 1);
 

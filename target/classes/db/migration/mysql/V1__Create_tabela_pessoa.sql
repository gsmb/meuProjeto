create table tabela_pessoa (
	ID int NOT NULL PRIMARY KEY,
	NOME varchar(100) NOT NULL,
	SEXO varchar(1) NOT NULL,
	ENDERECO varchar(300) NOT NULL,
	DATA_NASCIMENTO date NOT NULL,
	CPF varchar(14) NOT NULL
	
);

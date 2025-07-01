-- Remover tabelas existentes 
DROP TABLE IF EXISTS aluno CASCADE;
DROP TABLE IF EXISTS professores CASCADE;
DROP TABLE IF EXISTS adm CASCADE;
DROP TABLE IF EXISTS perguntas CASCADE;
DROP TABLE IF EXISTS ranking CASCADE;

-- Tabela Aluno
CREATE TABLE aluno (
    id_aluno INT AUTO_INCREMENT PRIMARY KEY,
    nome_aluno VARCHAR(70) NOT NULL,
    email_aluno VARCHAR(50) NOT NULL UNIQUE,
    senha_aluno VARCHAR(50) NOT NULL
) ENGINE = InnoDB;

-- Tabela Professores
CREATE TABLE professores(
    id_professores INT AUTO_INCREMENT PRIMARY KEY,
    nome_professor VARCHAR(70) NOT NULL,
    email_professor VARCHAR(50) UNIQUE NOT NULL,
    senha_professor VARCHAR(50) NOT NULL
) ENGINE = InnoDB;

-- Tabela Adm
CREATE TABLE adm (
    id_adm INT AUTO_INCREMENT PRIMARY KEY,
    nome_adm VARCHAR(70) NOT NULL,
    email_adm VARCHAR(50) UNIQUE NOT NULL,
    senha_adm VARCHAR(50) NOT NULL
) ENGINE = InnoDB;

-- Tabela Perguntas
CREATE TABLE perguntas (
    id_perguntas INT AUTO_INCREMENT PRIMARY KEY,
    pergunta VARCHAR(200) NOT NULL,
    resposta_certa VARCHAR(200) NOT NULL,
    resposta_errada1 VARCHAR(200) NOT NULL,
    resposta_errada2 VARCHAR(200) NOT NULL,
    resposta_errada3 VARCHAR(200) NOT NULL,
    justificativa VARCHAR(200) NOT NULL
) ENGINE = InnoDB;

-- Tabela Ranking
CREATE TABLE ranking (
    id_ranking INT AUTO_INCREMENT PRIMARY KEY,
    pontuacao INT,
    id_aluno_popula INT,
    FOREIGN KEY (id_aluno_popula) REFERENCES aluno (id_aluno) 
        ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB;
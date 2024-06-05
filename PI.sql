SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE=TRADITIONAL;

use sid;

DROP TABLE IF EXISTS perguntas CASCADE;
CREATE TABLE perguntas 
( id_perguntas INT AUTO_INCREMENT PRIMARY KEY,
pergunta VARCHAR(200) NOT NULL,
resposta_certa VARCHAR(200) NOT NULL,
resposta_errada1 VARCHAR(200) NOT NULL,
resposta_errada2 VARCHAR(200) NOT NULL,
resposta_errada3 VARCHAR(200) NOT NULL,
justificativa VARCHAR(200) NOT NULL) 
ENGINE = InnoDB;

DROP TABLE IF EXISTS professores CASCADE;
CREATE TABLE professores(
id_professores INT AUTO_INCREMENT PRIMARY KEY,
nome_professor VARCHAR(70) NOT NULL,
email_professor VARCHAR(50) UNIQUE NOT NULL,
senha_professor VARCHAR(50) NOT NULL
)
ENGINE = InnoDB;

DROP TABLE IF EXISTS aluno CASCADE;
CREATE TABLE aluno (
id_aluno INT AUTO_INCREMENT PRIMARY KEY,
nome_aluno VARCHAR(70) NOT NULL,
email_aluno VARCHAR(50) NOT NULL UNIQUE,
senha_aluno VARCHAR(50) NOT NULL
)
ENGINE = InnoDB;

select * from aluno;
select * from ranking;
-- Drop da trigger anterior
-- Drop da trigger anterior
DROP TRIGGER IF EXISTS cadastrar_aluno_ranking;

-- Criação de uma nova trigger para atualizar o ranking ao cadastrar um novo aluno
DELIMITER //

CREATE TRIGGER cadastrar_aluno_ranking AFTER INSERT ON aluno
FOR EACH ROW
BEGIN
    DECLARE num_alunos INT;
    -- Verificar quantos alunos existem no ranking atualmente
    SELECT COUNT(*) INTO num_alunos FROM ranking;
    
    -- Se não houver alunos no ranking, insira o novo aluno
    IF num_alunos = 0 THEN
        INSERT INTO ranking (pontuacao, id_aluno_popula) VALUES (0, NEW.id_aluno);
    ELSE
        -- Se já houver alunos no ranking, insira o novo aluno com o próximo lugar disponível
        INSERT INTO ranking (id_ranking, pontuacao, id_aluno_popula)
        SELECT MAX(id_ranking) + 1, 0, NEW.id_aluno FROM ranking;
    END IF;
END;
//

DELIMITER ;

DROP TABLE IF EXISTS ranking CASCADE;
CREATE TABLE ranking (
id_ranking INT AUTO_INCREMENT PRIMARY KEY,
pontuacao INT,
id_aluno_popula INT,
FOREIGN KEY (id_aluno_popula) REFERENCES
aluno (id_aluno) ON DELETE RESTRICT ON UPDATE CASCADE)
ENGINE = InnoDB;

DROP TABLE IF EXISTS adm CASCADE;
CREATE TABLE adm (
id_adm INT AUTO_INCREMENT PRIMARY KEY,
nome_adm VARCHAR(70) NOT NULL,
email_adm VARCHAR(50) UNIQUE NOT NULL,
senha_adm VARCHAR(50) NOT NULL
)
ENGINE = InnoDB;
SELECT * FROM professores; 
INSERT INTO perguntas VALUES (null,
'Qual órgão é responsável pela produção da bile?',
'Vesícula bilia',
'Estômago',
'Pâncreas',
'Baço',
'A bile é produzida no fígado e armazenada na vesícula biliar');

INSERT INTO perguntas VALUES (null,
'Onde ocorre a absorção da maioria dos nutrientes?',
'Intestino delgado',
'Estômago',
'Intestino grosso',
'Esôfago',
'É no intestino
delgado onde a maioria dos nutrientes é absorvida para a corrente sanguínea');

INSERT INTO perguntas VALUES (null,
'Qual é a função do esôfago no sistema digestório?',
'Transporte de alimentos para o estômago',
'Produção de enzimas digestivas',
'Absorção de nutrientes',
'Armazenamento de bile',
'O esôfago conduz o alimento da boca para o estômago através de contrações musculares.');

INSERT INTO perguntas VALUES (null,
'Qual é a função do intestino grosso no sistema digestório?',
'Absorção de água e minerais',
'Digestão de gorduras',
'Produção de bile',
'Absorção de vitaminas',
'O intestino grosso é responsável principalmente pela absorção de água e minerais.');
-- ppppp
INSERT INTO perguntas VALUES (null,
'Quais são as glândulas anexas ao sistema digestório?',
'Fígado, pâncreas e vesícula biliar',
'Fígado, estômago e pâncreas',
'Pâncreas, vesícula biliar e baço',
'Vesícula biliar, baço e intestino grosso',
'Essas glândulas produzem substâncias essenciais para a digestão.');

INSERT INTO perguntas VALUES (null,
'Qual é a principal enzima produzida pelo estômago?',
'Pepsina',
'Amilase',
'Lipase',
'Tripsina',
'A pepsina é uma enzima que quebra as proteínas em pedaços menores.');

INSERT INTO perguntas VALUES (null,
'O que é o peristaltismo?',
'Movimento de contração dos músculos no trato digestório',
'Movimento de mistura no estômago',
'Quebra mecânica dos alimentos',
'Absorção de nutrientes',
'O peristaltismo é o movimento de
contração rítmica dos músculos que empurra o alimento ao longo do sistema digestório.');

INSERT INTO perguntas VALUES (null,
'Qual é a função das vilosidades intestinais?',
'Aumento da superfície de absorção no intestino delgado',
'Produção de suco pancreático',
'Produção de bile',
'Armazenamento de nutrientes',
'As vilosidades intestinais aumentam a área de superfície disponível para a absorção de nutrientes.');

INSERT INTO perguntas VALUES (null,
'Qual é a função do suco pancreático?',
'Neutralizar o ácido estomacal',
'Digestão de proteínas',
'Emulsificação de gorduras',
'Digestão de carboidratos',
'O suco pancreático contém bicarbonato de sódio, que ajuda a neutralizar o ácido do estômago');

INSERT INTO perguntas VALUES (null,
'Onde ocorre a maior parte da digestão química dos alimentos?',
'Intestino delgado',
'Boca',
'Estômago',
'Intestino grosso',
'É no
intestino delgado onde ocorre a maior parte da digestão química dos alimentos, 
devido à ação de enzimas pancreáticas e intestinais');

INSERT INTO perguntas VALUES (null,
'O que é a amilase salivar?',
'Enzima que quebra amido',
'Enzima que quebra gorduras',
'Enzima que quebra proteínas',
'Enzima que quebra açúcares',
'A amilase salivar é uma enzima que inicia a digestão do amido, um
carboidrato presente nos alimentos.');

INSERT INTO perguntas VALUES (null,
'Qual é a função do fígado no sistema digestório?',
'Produção de bile',
'Digestão de gorduras',
'Produção de suco pancreático',
'Absorção de nutrientes',
'O fígado é responsável pela produção da bile, que é armazenada na vesícula biliar e
liberada no intestino delgado para ajudar na digestão das gorduras.');

INSERT INTO perguntas VALUES (null,
'O que são os movimentos segmentares no sistema digestório?',
'Movimentos de contração e relaxamento que ajudam na absorção de nutrientes',
'Movimentos peristálticos que empurram o alimento ao longo do trato digestório',
'Movimentos de mistura que ocorrem no estômago',
'Movimentos de contração no intestino grosso',
'Os movimentos segmentares ajudam a misturar o alimento com sucos digestivos e a
promover a absorção de nutrientes.');

INSERT INTO perguntas VALUES (null,
'O que é a bile e qual é sua função na digestão?',
'Um fluido que emulsifica gorduras no intestino delgado',
'Uma enzima que quebra gorduras no intestino delgado',
'Um ácido que neutraliza o pH estomacal',
'Um pigmento que dá cor às fezes',
'A bile emulsifica as gorduras, quebrando-as em gotículas menores para
facilitar a digestão pelas enzimas.');

INSERT INTO perguntas VALUES (null,
'O que é o suco gástrico e qual é sua composição principal?',
'Um fluido ácido que contém ácido clorídrico e pepsina',
'Um fluido alcalino que neutraliza o ácido estomacal',
'Um fluido rico em enzimas digestivas',
'Um fluido que reveste o estômago para protegê-lo do ácido',
'O suco gástrico é produzido pelas glândulas do estômago e contém ácido
clorídrico, que ajuda na digestão das proteínas, e pepsina, uma enzima digestiva.');

INSERT INTO perguntas VALUES (null,
'O que são as microvilosidades intestinais e qual é sua função?',
'Pequenas protuberâncias na mucosa do intestino delgado para aumentar a área
de absorção',
'Dobras na parede do intestino grosso para aumentar a absorção de água',
'Estruturas responsáveis pela produção de bile',
'Células especializadas na produção de enzimas digestivas',
'As microvilosidades intestinais aumentam a
superfície de absorção no intestino delgado, facilitando a absorção de nutrientes.');

INSERT INTO perguntas VALUES (null,
'O que é a peritonite e como ela pode ocorrer?',
'Inflamação da membrana que reveste a cavidade abdominal, geralmente devido a
uma infecção ou perfuração',
'Inflamação do revestimento do estômago devido ao consumo excessivo de
alimentos ácidos',
'Inflamação do intestino delgado causada por uma infecção viral',
'Inflamação da vesícula biliar devido ao acúmulo de bile',
'A peritonite pode ocorrer devido a uma
infecção bacteriana, lesão abdominal ou perfuração do trato gastrointestinal');

INSERT INTO perguntas VALUES (null,
'O que é a diverticulite?',
'Inflamação dos divertículos no intestino grosso',
'Inflamação das glândulas salivares',
'Inflamação do pâncreas',
'Inflamação do fígado',
'Diverticulite é a inflamação ou infecção dos
divertículos, pequenas bolsas que podem se formar ao longo da parede do intestino
grosso.');

INSERT INTO perguntas VALUES (null,
'O que são enzimas digestivas e qual é o papel delas na digestão?',
'Proteínas que ajudam na quebra de alimentos em moléculas menores',
'Hormônios que controlam o apetite',
'Substâncias que neutralizam o ácido estomacal',
'Carboidratos que fornecem energia ao sistema digestório',
'As enzimas digestivas são proteínas que aceleram as reações químicas envolvidas
na digestão, quebrando os alimentos em moléculas menores para absorção.');

INSERT INTO perguntas VALUES (null,
'O que é a síndrome do intestino irritável e quais são seus sintomas principais?',
'Distúrbio funcional do intestino com sintomas como dor abdominal, constipação e
diarreia',
'Doença inflamatória crônica do intestino com sintomas como dor abdominal,
diarreia e perda de peso',
'Condição autoimune que afeta o revestimento do intestino delgado',
'Infecção bacteriana do intestino com sintomas como febre e vômitos',
' A síndrome do intestino irritável é um distúrbio
gastrointestinal crônico que causa dor abdominal, constipação e/ou diarreia, sem
danos físicos no intestino.');

INSERT INTO perguntas VALUES (null,
'O que é a doença celíaca e como ela afeta o sistema digestório?',
'Doença autoimune que danifica o revestimento do intestino delgado em resposta
ao consumo de glúten',
'Alergia ao glúten que afeta o estômago, causando inflamação e úlceras',
'Intolerância à lactose que causa desconforto abdominal e diarreia',
'Infecção viral que causa inflamação no esôfago',
'A doença celíaca é uma doença autoimune em que o sistema
imunológico ataca erroneamente o revestimento do intestino delgado em resposta à
ingestão de glúten.');

INSERT INTO perguntas VALUES (null,
'O que é a cirrose hepática e como ela afeta o funcionamento do fígado?',
'Cicatrização do fígado devido a danos crônicos, resultando em perda de função
hepática',
'Inflamação crônica do pâncreas que leva à degeneração dos tecidos',
'Acúmulo de gordura no fígado que pode levar à insuficiência hepática',
'Inflamação aguda do fígado causada por uma infecção vira',
'A cirrose hepática é uma condição na qual o tecido hepático
normal é substituído por tecido cicatricial, levando à perda progressiva da função
hepática.');

INSERT INTO perguntas VALUES (null,
'O que é a pancreatite aguda e quais são suas causas comuns?',
'Inflamação aguda do pâncreas devido a cálculos biliares ou consumo excessivo
de álcool',
'Inflamação crônica do pâncreas devido ao consumo excessivo de álcool',
'Infecção bacteriana do pâncreas devido à ingestão de alimentos contaminados',
'Inflamação do pâncreas causada por uma dieta rica em gorduras',
'A pancreatite aguda é uma inflamação
súbita do pâncreas, muitas vezes causada por cálculos biliares ou consumo
excessivo de álcool.');

INSERT INTO perguntas VALUES (null,
'O que é a esteatose hepática e como ela afeta o fígado?',
'Acúmulo de gordura no fígado que pode levar à inflamação e cicatrização',
'Formação de cálculos biliares no fígado',
'Inflamação crônica do fígado devido à exposição a toxinas',
'Degeneração do tecido hepático devido à falta de oxigênio',
'A esteatose hepática é o acúmulo excessivo de gordura no fígado, o que pode levar a inflamação e cicatrização,
resultando em danos ao fígado.');

INSERT INTO perguntas VALUES (null,
'Qual cirurgia comprometeria mais a função do sistema digestório e por quê: a
remoção dos centímetros iniciais do intestino delgado ou a
remoção de igual porção do início do intestino grosso?',
'A remoção do duodeno seria mais drástica, pois nele ocorre a maior parte da
digestão intestinal.',
'A remoção do duodeno seria mais drástica, pois nele ocorre a absorção de toda a
água de que o organismo necessita para sobreviver.',
'A remoção do intestino grosso seria mais drástica, pois nele ocorre a maior parte
da absorção dos produtos do processo digestório.',
'A remoção do intestino grosso seria mais drástica, pois nele ocorre a absorção de
toda a água de que o organismo necessita para sobreviver.',
'Sendo assim, a remoção do duodeno seria mais drástica.');
INSERT INTO perguntas VALUES (null,
'a',
'b',
'c',
'A remoção do intestino grosso seria mais drástica, pois nele ocorre a maior parte
da absorção dos produtos do processo digestório.',
'A remoção do intestino grosso seria mais drástica, pois nele ocorre a absorção de
toda a água de que o organismo necessita para sobreviver.',
'Sendo assim, a remoção do duodeno seria mais drástica.');


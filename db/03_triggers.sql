-- Remover trigger existente
DROP TRIGGER IF EXISTS cadastrar_aluno_ranking;

-- Criar trigger para inserir aluno no ranking automaticamente
DELIMITER //

CREATE TRIGGER cadastrar_aluno_ranking AFTER INSERT ON aluno
FOR EACH ROW
BEGIN
    DECLARE num_alunos INT;
    
    -- Verificar quantos alunos existem no ranking
    SELECT COUNT(*) INTO num_alunos FROM ranking;
    
    -- Inserir novo aluno no ranking
    IF num_alunos = 0 THEN
        INSERT INTO ranking (pontuacao, id_aluno_popula) 
        VALUES (0, NEW.id_aluno);
    ELSE
        INSERT INTO ranking (id_ranking, pontuacao, id_aluno_popula)
        SELECT MAX(id_ranking) + 1, 0, NEW.id_aluno FROM ranking;
    END IF;
END;
//

DELIMITER ;
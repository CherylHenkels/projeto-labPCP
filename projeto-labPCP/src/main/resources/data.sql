INSERT INTO papeis (id, nome)
VALUES (1, 'ADM'),
       (2, 'PEDAGOGICO'),
       (3, 'RECRUITER'),
       (4, 'PROFESSOR'),
       (5, 'ALUNO');

INSERT INTO usuarios (nome_usuario, senha, id_papel)
 VALUES ('user1', '$2a$14$OnqkbK/v3CMOkxoVNKuTdOPIWLGH90Zom1frMx4K61ndADkzAsN7O', 1),
        ('user2', '$2a$14$OnqkbK/v3CMOkxoVNKuTdOPIWLGH90Zom1frMx4K61ndADkzAsN7O', 2),
        ('user3', '$2a$14$OnqkbK/v3CMOkxoVNKuTdOPIWLGH90Zom1frMx4K61ndADkzAsN7O', 3),
        ('user4', '$2a$14$OnqkbK/v3CMOkxoVNKuTdOPIWLGH90Zom1frMx4K61ndADkzAsN7O', 4),
        ('user5', '$2a$14$OnqkbK/v3CMOkxoVNKuTdOPIWLGH90Zom1frMx4K61ndADkzAsN7O', 5),
        ('user6', '$2a$14$OnqkbK/v3CMOkxoVNKuTdOPIWLGH90Zom1frMx4K61ndADkzAsN7O', 5),
        ('user7', '$2a$14$OnqkbK/v3CMOkxoVNKuTdOPIWLGH90Zom1frMx4K61ndADkzAsN7O', 5),
        ('user8', '$2a$14$OnqkbK/v3CMOkxoVNKuTdOPIWLGH90Zom1frMx4K61ndADkzAsN7O', 5);
--
INSERT INTO docentes (nome, data_entrada, id_usuario)
VALUES  ('Albert Einstein', '2023-08-02', 1),
        ('Marie Curie', '2023-08-02', 2),
        ('Isaac Newton','2023-08-02', 3),
        ('Galileu Galilei','2023-08-02', 4);

INSERT INTO cursos (nome)
VALUES  ('Física'),
        ('Química'),
        ('Matemática');

INSERT INTO turmas (nome, id_docente, id_curso)
VALUES  ('A', 4, 1),
        ('B', 4, 2);


INSERT INTO alunos (nome, data_nascimento, id_usuario, id_turma)
VALUES  ('Tony Stark', '1970-05-29', 5, 1),
        ('Steve Rogers', '1918-07-04', 6, 1),
        ('Natasha Romanoff', '1984-12-03', 7, 2),
        ('Bruce Banner', '1969-12-18', 8, 2);

INSERT INTO materias (nome, id_curso)
VALUES  ('Mecânica Quântica', 1),
        ('Físico-Química', 2),
        ('Geometria Analítica', 3);

INSERT INTO notas (id_aluno, id_docente, id_materia, valor, data)
VALUES  (1, 4, 1, 7.0 , '2024-02-24'),
        (1, 4, 1, 8.0 , '2024-02-24'),
        (2, 4, 1, 7.0 , '2024-02-24'),
        (2, 4, 1, 6.0 , '2024-02-24'),
        (3, 4, 2, 9.0 , '2024-02-24'),
        (3, 4, 2, 10.0, '2024-02-24'),
        (4, 4, 2, 8.0 , '2024-02-24'),
        (4, 4, 2, 9.0 , '2024-02-24');

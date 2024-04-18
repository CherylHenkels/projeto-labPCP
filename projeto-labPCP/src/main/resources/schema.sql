CREATE TABLE IF NOT EXISTS papeis
(
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) UNIQUE
);

CREATE TABLE IF NOT EXISTS usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome_usuario VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(500) NOT NULL,
    id_papel BIGINT NOT NULL,
    FOREIGN KEY (id_papel) REFERENCES papeis(id)
);

CREATE TABLE IF NOT EXISTS docentes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    data_entrada DATE NOT NULL DEFAULT CURRENT_DATE,
    id_usuario BIGINT NOT NULL,
    UNIQUE (id_usuario),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

CREATE TABLE IF NOT EXISTS turmas (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    id_docente BIGINT NOT NULL,
    id_curso BIGINT NOT NULL,
    FOREIGN KEY (id_docente) REFERENCES docentes(id),
    FOREIGN KEY (id_curso) REFERENCES cursos(id)
);

CREATE TABLE IF NOT EXISTS cursos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS alunos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    data_nascimento DATE NOT NULL,
    id_usuario BIGINT NOT NULL,
    id_turma BIGINT NOT NULL,
    UNIQUE (id_usuario),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_turma) REFERENCES turmas(id)
);

CREATE TABLE IF NOT EXISTS materias (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    id_curso BIGINT NOT NULL,
    FOREIGN KEY (id_curso) REFERENCES cursos(id)
);

CREATE TABLE IF NOT EXISTS notas (
    id BIGSERIAL PRIMARY KEY,
    id_aluno BIGINT NOT NULL,
    id_docente BIGINT NOT NULL,
    id_materia BIGINT NOT NULL,
    data DATE NOT NULL DEFAULT CURRENT_DATE,
    valor DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (id_aluno) REFERENCES alunos(id),
    FOREIGN KEY (id_docente) REFERENCES docentes(id),
    FOREIGN KEY (id_materia) REFERENCES materias(id)
);
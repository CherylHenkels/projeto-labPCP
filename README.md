# Projeto módulo 1 - labPCP

## Aluna
Cheryl Henkels

## Descrição
Sistema de controle de uma instituição de ensino que permite a gestão de cursos, turmas, matérias, notas, alunos e docentes. Ela pode ser implementada em creches, escolas, universidades, ou qualquer entidade de ensino para gerir seu sistema organizacional.
O sistema é uma API Rest back-end desenvolvida em Java com o framework Spring Boot.
O PostgreSQL é utilizado para guardar as informações cadastradas.


## Execução 
O sistema baseia-se na utilização de diferentes papéis de usuário para restringir o acesso às diferentes funcionalidades do sistema. Assim, é preciso fornecer um Token JWT em cada endpoint para acessar os dados do mesmo.

O único papel que pode criar usuários é o ADM. Logo, para iniciar sistema e criar o primeiro token é necessário ir no endpoint de login de Usuário (POST /login) e inserir os dados de um ADM previamente cadastrado no sistema:


    {
    "usuario":"user1",
    "senha":"pass"
    }
    


Com este primeiro token, o usuário terá acesso ao endpoint de Cadastro de Usuário (POST /cadastro) para criar outros usuários que podem ter os seguintes papéis:

ADM: que pode acessar tudo.

PEDAGOGICO: que pode acessar tudo sobre turma, curso e professor, aluno, menos deletar dados.

RECRUITER: que pode acessar tudo sobre professor, menos deletar dados.

PROFESSOR: que pode acessar tudo sobre notas, menos deletar dados.

ALUNO: que apenas acessa as suas próprias notas e a pontuação total pessoal, menos deletar dados.


## Uso

Com o papel do usuário definido (a partir do Token), o mesmo pode acessar as funcionalidades do sistema a partir dos seguintes endpoints (**Observação:** todos os endpoins podem ser encontrados em Projeto1.postman_collection.json):

**Endpoints para entidade Docente**

* Criar Docente (POST /docentes)
    * Body: JSON representando os dados do docente a ser criado.

* Obter Docente por ID (GET /docentes/{id})
    * Parâmetros de URL: ID do docente.

* Atualizar Docente (PUT /docentes/{id})
    * Parâmetros de URL: ID do docente a ser atualizado.
    * Body: JSON representando os novos dados do docente.

* Excluir Docente (DELETE /docentes/{id})
    * Parâmetros de URL: ID do docente a ser excluído.

* Listar Docentes (GET /docentes)


**Endpoints para entidade Turma**
* Criar Turma (POST /turmas)
    * Body: JSON representando os dados da turma a ser criada.

* Obter Turma por ID (GET /turmas/{id})
    * Parâmetros de URL: ID da turma.

* Atualizar Turma (PUT /turmas/{id})
    * Parâmetros de URL: ID da turma a ser atualizada.
    * Body: JSON representando os novos dados da turma.

* Excluir Turma (DELETE /turmas/{id})
    * Parâmetros de URL: ID da turma a ser excluída.

* Listar Turmas (GET /turmas)


**Endpoints para entidade Aluno**
* Criar Aluno (POST /alunos)
    * Body: JSON representando os dados do aluno a ser criado.

* Obter Aluno por ID (GET /alunos/{id})
    * Parâmetros de URL: ID do aluno.

* Atualizar Aluno (PUT /alunos/{id})
    * Parâmetros de URL: ID do aluno a ser atualizado.
    * Body: JSON representando os novos dados do aluno.

* Excluir Aluno (DELETE /alunos/{id})
    * Parâmetros de URL: ID do aluno a ser excluído.

* Listar Alunos (GET /alunos)



**Endpoints para entidade Curso**
* Criar Curso (POST /cursos)
    * Body: JSON representando os dados do curso a ser criado.

* Obter Curso por ID (GET /cursos/{id})
    * Parâmetros de URL: ID do curso.

* Atualizar Curso (PUT /cursos/{id})
    * Parâmetros de URL: ID do curso a ser atualizado.
    * Body: JSON representando os novos dados do curso.

* Excluir Curso (DELETE /cursos/{id})
    * Parâmetros de URL: ID do curso a ser excluído.

* Listar Cursos (GET /cursos)


**Endpoints para entidade Matérias**
* Listar Matérias por Curso (GET /cursos/{id_curso}/materias)
    * Parâmetros de URL: ID do curso.

* Criar Matéria (POST /materias)
    * Body: JSON representando os dados da matéria a ser criada.

* Obter Matéria por ID (GET /materias/{id})
    * Parâmetros de URL: ID da matéria.

* Atualizar Matéria (PUT /materias/{id})
    * Parâmetros de URL: ID da matéria a ser atualizada.
    * Body: JSON representando os novos dados da matéria.

* Excluir Matéria (DELETE /materias/{id})
    * Parâmetros de URL: ID da matéria a ser excluída.


**Endpoints para entidade Notas**
* Listar Notas por Aluno (GET /alunos/{id_aluno}/notas)
    * Parâmetros de URL: ID do aluno.

* Criar Nota (POST /notas)
    * Body: JSON representando os dados da nota a ser criada.

* Obter Nota por ID (GET /notas/{id})
    * Parâmetros de URL: ID da nota.

* Atualizar Nota (PUT /notas/{id})
    * Parâmetros de URL: ID da nota a ser atualizada.
    * Body: JSON representando os novos dados da nota.

* Excluir Nota (DELETE /notas/{id})
    * Parâmetros de URL: ID da nota a ser excluída.


**Endpoint para obter Pontuação Total do Aluno**
* Criar Turma (GET /alunos/{id}/pontuacao)


  


## Melhorias
- O aluno poderia ter acesso a sua média por matéria para saber se foi aprovado na mesma
- Poderia ser acrescentado um status final se o aluno foi APROVADO ou REPROVADO
- O aluno poderia ter acesso às informações das matérias na qual ele está matriculado


## Informações finais
Este projeto foi desenvolvido para o curso FullStack [Education] para compor a nota do módulo.

O projeto está todo contido no repositório do gitHub: <https://github.com/CherylHenkels/projeto-labPCP.git>

A organização das tarefas foi feita no Trello e pode ser conferida no quadro (board):
<https://trello.com/invite/b/Pfw5jWR6/ATTIb9a4632475e94fc541e9e1b3c1bf7ebaF157177D/projeto-modulo-1>

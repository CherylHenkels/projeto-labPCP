package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.datasource.entity.CursoEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.TurmaEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.DocenteEntity;
import br.fullstack.education.projetolabpcp.datasource.repository.CursoRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.DocenteRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.TurmaRepository;
import br.fullstack.education.projetolabpcp.infra.exception.TurmaByIdNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurmaServiceImpl implements TurmaService {

    private final TurmaRepository turmaRepository;
    private final DocenteRepository docenteRepository;
    private final CursoRepository cursoRepository;
    private final TokenService tokenService;

    public TurmaServiceImpl(TurmaRepository turmaRepository, DocenteRepository docenteRepository, CursoRepository cursoRepository, TokenService tokenService) {
        this.turmaRepository = turmaRepository;
        this.docenteRepository = docenteRepository;
        this.cursoRepository = cursoRepository;
        this.tokenService = tokenService;
    }

    @Override
    public List<TurmaEntity> buscarTodos() {

        return turmaRepository.findAll();
    }

    @Override
    public TurmaEntity buscarPorId(Long id) {

        return turmaRepository.findById(id)
                .orElseThrow(() -> new TurmaByIdNotFoundException(id));
    }

    @Override
    public TurmaEntity criar(TurmaEntity entity, String token) {

        // Pega id do token para mais tarde validar o usuário
        Long tokenId = Long.valueOf( tokenService.buscaCampo(token,"sub"));

        // cria docente que será vinculado a turma
        DocenteEntity turmaDocente = docenteRepository.findById(entity.getDocente().getId())
                .orElseThrow(() -> new RuntimeException("Docente não encontrado"));

        // cria curso que será vinculado a turma
        CursoEntity turmaCurso = cursoRepository.findById(entity.getCurso().getId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        entity.setId(null); // garante que novo Id vai ser criado
        entity.setDocente(turmaDocente); // Faz o link do docente com o turma
        entity.setCurso(turmaCurso); // Faz o link do curso com o turma
        return turmaRepository.save(entity);
    }

    @Override
    public TurmaEntity alterar(Long id, TurmaEntity entity) {
        buscarPorId(id); // Verifica a existência do Turma.
        entity.setId(id); // Assegura que a alteração será no Turma correto.
        return turmaRepository.save(entity);
    }

    @Override
    public void excluir(Long id) {
        TurmaEntity entity = buscarPorId(id); // Verifica se o Turma existe antes de excluir.
        turmaRepository.delete(entity);
    }
}

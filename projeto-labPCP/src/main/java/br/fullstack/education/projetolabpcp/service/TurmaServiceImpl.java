package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.controller.dto.request.TurmaRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.DocenteResponse;
import br.fullstack.education.projetolabpcp.controller.dto.response.TurmaResponse;
import br.fullstack.education.projetolabpcp.datasource.entity.CursoEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.TurmaEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.DocenteEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.UsuarioEntity;
import br.fullstack.education.projetolabpcp.datasource.repository.CursoRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.DocenteRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.TurmaRepository;
import br.fullstack.education.projetolabpcp.infra.exception.NotFoundException;
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
    public List<TurmaResponse> buscarTodos() {
        List<TurmaEntity> turmas = turmaRepository.findAll();

        if (turmas.isEmpty()) {
            throw new NotFoundException("Não há turmas cadastradas.");
        }

        return turmas.stream().map( //mapear a lista de TurmaEntity para uma lista de TurmaResponse
                t-> new TurmaResponse(t.getId(), t.getNome(), t.getDocente().getId(), t.getCurso().getId())
        ).toList();
    }

    @Override
    public TurmaResponse buscarPorId(Long id) {

        TurmaEntity turma = turmaRepository.findById(id)
                .orElseThrow( () -> new NotFoundException("Turma não encontrada com id:" + id ));

        return new TurmaResponse(turma.getId(), turma.getNome(), turma.getDocente().getId(), turma.getCurso().getId());
    }

    @Override
    public TurmaResponse criar(TurmaRequest turmaRequest, String token) {

        // Pega id do token para mais tarde validar o usuário
        Long tokenId = Long.valueOf( tokenService.buscaCampo(token,"sub"));

        // cria docente que será vinculado a turma
        DocenteEntity turmaDocente = docenteRepository.findById(turmaRequest.getId_docente())
                .orElseThrow(() -> new NotFoundException("Docente não encontrado com id:" + turmaRequest.getId_docente()));

        // cria curso que será vinculado a turma
        CursoEntity turmaCurso = cursoRepository.findById(turmaRequest.getId_curso())
                .orElseThrow(() -> new NotFoundException("Curso não encontrado com id:" + turmaRequest.getId_curso()));

        TurmaEntity turma = new TurmaEntity();
        turma.setId(null); // garante que novo Id vai ser criado
        turma.setNome(turmaRequest.getNome());
        turma.setDocente(turmaDocente); // Faz o link do docente com o turma
        turma.setCurso(turmaCurso); // Faz o link do curso com o turma
        turmaRepository.save(turma);

        return new TurmaResponse(turma.getId(), turma.getNome(), turma.getDocente().getId(), turma.getCurso().getId());
    }

    @Override
    public TurmaResponse alterar(Long id, TurmaRequest turmaRequest) {
        buscarPorId(id); // Verifica a existência do Turma.

        // cria docente que será vinculado a turma
        DocenteEntity turmaDocente = docenteRepository.findById(turmaRequest.getId_docente())
                .orElseThrow(() -> new NotFoundException("Docente não encontrado com id:" + turmaRequest.getId_docente()));

        // cria curso que será vinculado a turma
        CursoEntity turmaCurso = cursoRepository.findById(turmaRequest.getId_curso())
                .orElseThrow(() -> new NotFoundException("Curso não encontrado com id:" + turmaRequest.getId_curso()));

        TurmaEntity turma = new TurmaEntity();
        turma.setId(id);
        turma.setNome(turmaRequest.getNome());
        turma.setDocente(turmaDocente);
        turma.setCurso(turmaCurso);
       turmaRepository.save(turma);

        return new TurmaResponse(turma.getId(), turma.getNome(), turma.getDocente().getId(), turma.getCurso().getId());
    }

    @Override
    public void excluir(Long id) {
        TurmaEntity turma = turmaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Turma não encontrada com id:" + id )); // Verifica se o Turma existe antes de excluir.
        turmaRepository.delete(turma);
    }
}

package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.controller.dto.request.TurmaRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.TurmaResponse;
import br.fullstack.education.projetolabpcp.datasource.entity.CursoEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.TurmaEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.DocenteEntity;
import br.fullstack.education.projetolabpcp.datasource.repository.CursoRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.DocenteRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.TurmaRepository;
import br.fullstack.education.projetolabpcp.infra.exception.InvalidRequestException;
import br.fullstack.education.projetolabpcp.infra.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TurmaServiceImpl implements TurmaService {

    private final TurmaRepository turmaRepository;
    private final DocenteRepository docenteRepository;
    private final CursoRepository cursoRepository;

    public TurmaServiceImpl(TurmaRepository turmaRepository, DocenteRepository docenteRepository, CursoRepository cursoRepository) {
        this.turmaRepository = turmaRepository;
        this.docenteRepository = docenteRepository;
        this.cursoRepository = cursoRepository;
    }

    @Override
    public List<TurmaResponse> buscarTodos() {
        List<TurmaEntity> turmas = turmaRepository.findAll();

        if (turmas.isEmpty()) {
            log.error(" 404 NOT FOUND -> Não há turmas cadastradas.");
            throw new NotFoundException("Não há turmas cadastradas.");
        }

        return turmas.stream().map( //mapear a lista de TurmaEntity para uma lista de TurmaResponse
                t-> new TurmaResponse(t.getId(), t.getNome(), t.getDocente().getId(), t.getCurso().getId())
        ).toList();
    }

    @Override
    public TurmaResponse buscarPorId(Long id) {

        TurmaEntity turma = turmaRepository.findById(id)
                .orElseThrow( () -> {
                    log.error("404 NOT FOUND -> Turma não encontrada com id: {}" , id);
                    return new NotFoundException("Turma não encontrada com id:" + id );});

        return new TurmaResponse(turma.getId(), turma.getNome(), turma.getDocente().getId(), turma.getCurso().getId());
    }

    @Override
    public TurmaResponse criar(TurmaRequest turmaRequest) {

        if (turmaRequest.getNome() == null || turmaRequest.getNome().trim().isEmpty()) {
            log.error("400 BAD REQUEST -> Nome da turma é obrigatória");
            throw new InvalidRequestException("Nome da turma é obrigatória");
        }
        if (turmaRequest.getId_docente() == null ) {
            log.error("400 BAD REQUEST -> Id do docente é obrigatório");
            throw new InvalidRequestException("Id do docente é obrigatório");
        }
        if (turmaRequest.getId_curso() == null ) {
            log.error("400 BAD REQUEST -> Id do curso é obrigatório");
            throw new InvalidRequestException("Id do curso é obrigatório");
        }

        // cria docente que será vinculado a turma
        DocenteEntity turmaDocente = docenteRepository.findById(turmaRequest.getId_docente())
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND -> Docente não encontrado com id: {}" , turmaRequest.getId_docente());
                    return new NotFoundException("Docente não encontrado com id:" + turmaRequest.getId_docente());});

        // cria curso que será vinculado a turma
        CursoEntity turmaCurso = cursoRepository.findById(turmaRequest.getId_curso())
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND -> Curso não encontrado com id: {}" , turmaRequest.getId_curso());
                    return new NotFoundException("Curso não encontrado com id:" + turmaRequest.getId_curso());});

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

        if (turmaRequest.getNome() == null || turmaRequest.getNome().trim().isEmpty()) {
            log.error("400 BAD REQUEST -> Nome da turma é obrigatória");
            throw new InvalidRequestException("Nome da turma é obrigatória");
        }
        if (turmaRequest.getId_docente() == null ) {
            log.error("400 BAD REQUEST -> Id do docente é obrigatório");
            throw new InvalidRequestException("Id do docente é obrigatório");
        }
        if (turmaRequest.getId_curso() == null ) {
            log.error("400 BAD REQUEST -> Id do curso é obrigatório");
            throw new InvalidRequestException("Id do curso é obrigatório");
        }

        // cria docente que será vinculado a turma
        DocenteEntity turmaDocente = docenteRepository.findById(turmaRequest.getId_docente())
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND -> Docente não encontrado com id: {}" , turmaRequest.getId_docente());
                    return new NotFoundException("Docente não encontrado com id:" + turmaRequest.getId_docente());});

        // cria curso que será vinculado a turma
        CursoEntity turmaCurso = cursoRepository.findById(turmaRequest.getId_curso())
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND -> Curso não encontrado com id: {}" , turmaRequest.getId_curso());
                    return new NotFoundException("Curso não encontrado com id:" + turmaRequest.getId_curso());});

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
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND -> Turma não encontrada com id: {}" , id );
                    return new NotFoundException("Turma não encontrada com id:" + id );}); // Verifica se o Turma existe antes de excluir.
        turmaRepository.delete(turma);
    }
}

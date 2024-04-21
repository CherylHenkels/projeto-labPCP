package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.controller.dto.request.MateriaRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.MateriaResponse;
import br.fullstack.education.projetolabpcp.datasource.entity.CursoEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.MateriaEntity;
import br.fullstack.education.projetolabpcp.datasource.repository.MateriaRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.CursoRepository;
import br.fullstack.education.projetolabpcp.infra.exception.CursoByIdNotFoundException;
import br.fullstack.education.projetolabpcp.infra.exception.InvalidRequestException;
import br.fullstack.education.projetolabpcp.infra.exception.MateriaByIdNotFoundException;
import br.fullstack.education.projetolabpcp.infra.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MateriaServiceImpl implements MateriaService {

    private final MateriaRepository materiaRepository;
    private final CursoRepository cursoRepository;
    private final TokenService tokenService;

    public MateriaServiceImpl(MateriaRepository materiaRepository, CursoRepository cursoRepository, TokenService tokenService) {
        this.materiaRepository = materiaRepository;
        this.cursoRepository = cursoRepository;
        this.tokenService = tokenService;
    }

    @Override
    public List<MateriaResponse> buscarTodos() {
        List<MateriaEntity> materias = materiaRepository.findAll();
        if (materias.isEmpty()) {
            log.error("404 NOT FOUND -> Não há matérias cadastradas");
            throw new NotFoundException("Não há matérias cadastradas");
        }

        return materias.stream().map(
                t-> new MateriaResponse(t.getId(), t.getNome(), t.getCurso().getId())
        ).toList();
    }

    @Override
    public MateriaResponse buscarPorId(Long id) {

        MateriaEntity materia = materiaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND -> Matéria não encontrada com id: {}" , id);
                    return new NotFoundException("Matéria não encontrada com id:" + id);} );

        return new MateriaResponse(id, materia.getNome(), materia.getCurso().getId());
    }

    @Override
    public MateriaResponse criar(MateriaRequest materiaRequest, String token) {

        // Pega id do token para mais tarde validar o usuário
        Long tokenId = Long.valueOf( tokenService.buscaCampo(token,"sub"));

        if (materiaRequest.getNome() == null || materiaRequest.getNome().trim().isEmpty()) {
            log.error("400 BAD REQUEST -> Nome da matéria é obrigatório");
            throw new InvalidRequestException("Nome da matéria é obrigatório");
        }
        if (materiaRequest.getId_curso() == null ) {
            log.error("400 BAD REQUEST -> Id do curso é obrigatório");
            throw new InvalidRequestException("Id do curso é obrigatório");
        }


        // cria curso que será vinculado ao materia
        CursoEntity materiaCurso = cursoRepository.findById(materiaRequest.getId_curso())
                .orElseThrow(() -> {
                    log.error(" 404 NOT FOUND -> Curso não encontrado com id: {}" , materiaRequest.getId_curso());
                    return new NotFoundException("Curso não encontrado com id:" + materiaRequest.getId_curso());});

        MateriaEntity materia = new MateriaEntity();
        materia.setId(null); // garante que novo Id vai ser criado
        materia.setNome(materiaRequest.getNome());
        materia.setCurso(materiaCurso); // Faz o link do curso com o materia
        materiaRepository.save(materia);

        return new MateriaResponse(materia.getId(), materia.getNome(), materia.getCurso().getId());
    }

    @Override
    public MateriaResponse alterar(Long id, MateriaRequest materiaRequest) {
        buscarPorId(id); // Verifica a existência do Materia.

        if (materiaRequest.getNome() == null || materiaRequest.getNome().trim().isEmpty()) {
            log.error("400 BAD REQUEST -> Nome da matéria é obrigatório");
            throw new InvalidRequestException("Nome da matéria é obrigatório");
        }
        if (materiaRequest.getId_curso() == null ) {
            log.error("400 BAD REQUEST -> Id do curso é obrigatório");
            throw new InvalidRequestException("Id do curso é obrigatório");
        }

        // cria curso que será vinculado ao materia
        CursoEntity materiaCurso = cursoRepository.findById(materiaRequest.getId_curso())
                .orElseThrow(() -> {
                    log.error(" 404 NOT FOUND -> Curso não encontrado com id: {}" , materiaRequest.getId_curso());
                    return new NotFoundException("Curso não encontrado com id:" + materiaRequest.getId_curso());});

        MateriaEntity materia = new MateriaEntity();
        materia.setId(id); // Assegura que a alteração será no Materia correto.
        materia.setNome(materiaRequest.getNome());
        materia.setCurso(materiaCurso);
        materiaRepository.save(materia);

        return new MateriaResponse(id, materia.getNome(), materia.getCurso().getId());
    }

    @Override
    public void excluir(Long id) {
        MateriaEntity materia = materiaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND -> Matéria não encontrada com id: {}" , id);
                    return new NotFoundException("Matéria não encontrada com id:" + id);}); // Verifica se o Materia existe antes de excluir.
        materiaRepository.delete(materia);
    }

    @Override
    public List<MateriaResponse> buscarPorCursoId(Long idCurso) {

        if (!cursoRepository.existsById(idCurso)) {
            log.error("404 NOT FOUND -> Não há matérias cadastradas para o curso com id: {}" , idCurso);
            throw new NotFoundException("Não há matérias cadastradas para o curso com id:" + idCurso);
        }

        List<MateriaEntity> materias =  materiaRepository.findByCursoId(idCurso);

        return materias.stream().map(
                t-> new MateriaResponse(t.getId(), t.getNome(), t.getCurso().getId())
        ).toList();
    }
}

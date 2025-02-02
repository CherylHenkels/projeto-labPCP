package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.controller.dto.request.NotaRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.NotaResponse;
import br.fullstack.education.projetolabpcp.datasource.entity.DocenteEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.MateriaEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.NotaEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.AlunoEntity;
import br.fullstack.education.projetolabpcp.datasource.repository.DocenteRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.MateriaRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.NotaRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.AlunoRepository;
import br.fullstack.education.projetolabpcp.infra.exception.InvalidRequestException;
import br.fullstack.education.projetolabpcp.infra.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NotaServiceImpl implements NotaService {

    private final NotaRepository notaRepository;
    private final AlunoRepository alunoRepository;
    private final DocenteRepository docenteRepository;
    private final MateriaRepository materiaRepository;

    public NotaServiceImpl(NotaRepository notaRepository, AlunoRepository alunoRepository, DocenteRepository docenteRepository,  MateriaRepository materiaRepository) {
        this.notaRepository = notaRepository;
        this.alunoRepository = alunoRepository;
        this.docenteRepository = docenteRepository;
        this.materiaRepository = materiaRepository;
    }

    @Override
    public List<NotaResponse> buscarTodos() {
        List<NotaEntity> notas = notaRepository.findAll();

        if (notas.isEmpty()) {
            log.error("404 NOT FOUND -> Não há notas cadastradas");
            throw new NotFoundException("Não há notas cadastradas");
        }

        return notas.stream().map(
                t -> new NotaResponse(t.getId(), t.getValor(), t.getData(),
                        t.getAluno().getId(), t.getDocente().getId(), t.getMateria().getId())
        ).toList();
    }

    @Override
    public NotaResponse buscarPorId(Long id) {

        NotaEntity nota = notaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND -> Nota não encontrada com id: {}" , id);
                    return new NotFoundException("Nota não encontrada com id:" + id); });

        return new NotaResponse(nota.getId(), nota.getValor(), nota.getData(),
                nota.getAluno().getId(), nota.getDocente().getId(), nota.getMateria().getId());
    }

    @Override
    public NotaResponse criar(NotaRequest notaRequest) {


        if (notaRequest.getValor() == null ) {
            log.error("400 BAD REQUEST -> Valor da nota é obrigatório");
            throw new InvalidRequestException("Valor da nota é obrigatório");
        }
        if (notaRequest.getId_docente() == null ) {
            log.error("400 BAD REQUEST -> Id do docente é obrigatório");
            throw new InvalidRequestException("Id do docente é obrigatório");
        }
        if (notaRequest.getId_materia() == null ) {
            log.error("400 BAD REQUEST -> Id da materia é obrigatório");
            throw new InvalidRequestException("Id da materia é obrigatório");
        }
        if (notaRequest.getId_aluno() == null ) {
            log.error("400 BAD REQUEST -> Id do aluno é obrigatório");
            throw new InvalidRequestException("Id do aluno é obrigatório");
        }

        // cria aluno que será vinculado a nota
        AlunoEntity notaAluno = alunoRepository.findById(notaRequest.getId_aluno())
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND -> Aluno não encontrado com id: {}" , notaRequest.getId_aluno());
                    return new NotFoundException("Aluno não encontrado com id:" + notaRequest.getId_aluno());});

        // cria docente que será vinculado a nota
        DocenteEntity notaDocente = docenteRepository.findById(notaRequest.getId_docente())
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND -> Docente não encontrado com id: {}" , notaRequest.getId_docente());
                    return new NotFoundException("Docente não encontrado com id:" + notaRequest.getId_docente());});

        // cria materia que será vinculada a nota
        MateriaEntity notaMateria = materiaRepository.findById(notaRequest.getId_materia())
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND -> Materia não encontrada com id: {}" , notaRequest.getId_materia());
                    return new NotFoundException("Materia não encontrada com id:" + notaRequest.getId_materia());});

        NotaEntity nota = new NotaEntity();
        nota.setId(null); // garante que novo Id vai ser criado
        nota.setValor(notaRequest.getValor());
        nota.setAluno(notaAluno); // Faz o link do aluno com a nota
        nota.setDocente(notaDocente); // Faz o link do docente com a nota
        nota.setMateria(notaMateria); // Faz o link da materia com a nota
        notaRepository.save(nota);

        return new NotaResponse(nota.getId(), nota.getValor(), nota.getData(),
                nota.getAluno().getId(), nota.getDocente().getId(), nota.getMateria().getId());
    }

    @Override
    public NotaResponse alterar(Long id, NotaRequest notaRequest) {
        buscarPorId(id); // Verifica a existência do Nota.

        if (notaRequest.getValor() == null ) {
            log.error("400 BAD REQUEST -> Valor da nota é obrigatório");
            throw new InvalidRequestException("Valor da nota é obrigatório");
        }
        if (notaRequest.getId_docente() == null ) {
            log.error("400 BAD REQUEST -> Id do docente é obrigatório");
            throw new InvalidRequestException("Id do docente é obrigatório");
        }
        if (notaRequest.getId_materia() == null ) {
            log.error("400 BAD REQUEST -> Id da materia é obrigatório");
            throw new InvalidRequestException("Id da materia é obrigatório");
        }
        if (notaRequest.getId_aluno() == null ) {
            log.error("400 BAD REQUEST -> Id do aluno é obrigatório");
            throw new InvalidRequestException("Id do aluno é obrigatório");
        }

        // cria aluno que será vinculado a nota
        AlunoEntity notaAluno = alunoRepository.findById(notaRequest.getId_aluno())
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND -> Aluno não encontrado com id: {}" , notaRequest.getId_aluno());
                    return new NotFoundException("Aluno não encontrado com id:" + notaRequest.getId_aluno());});

        // cria docente que será vinculado a nota
        DocenteEntity notaDocente = docenteRepository.findById(notaRequest.getId_docente())
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND -> Docente não encontrado com id: {}" , notaRequest.getId_docente());
                    return new NotFoundException("Docente não encontrado com id:" + notaRequest.getId_docente());});

        // cria materia que será vinculada a nota
        MateriaEntity notaMateria = materiaRepository.findById(notaRequest.getId_materia())
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND -> Materia não encontrada com id: {}" , notaRequest.getId_materia());
                    return new NotFoundException("Materia não encontrada com id:" + notaRequest.getId_materia());});

        NotaEntity nota = new NotaEntity();
        nota.setId(id);
        nota.setValor(notaRequest.getValor());
        nota.setAluno(notaAluno);
        nota.setDocente(notaDocente);
        nota.setMateria(notaMateria);
        notaRepository.save(nota);

        return new NotaResponse(nota.getId(), nota.getValor(), nota.getData(),
                nota.getAluno().getId(), nota.getDocente().getId(), nota.getMateria().getId());
    }

    @Override
    public void excluir(Long id) {
        NotaEntity nota = notaRepository.findById(id)
                .orElseThrow(()-> {
                    log.error("404 NOT FOUND -> Nota não encontrada com id: {}" , id);
                    return new NotFoundException("Nota não encontrada com id:" + id);}); // Verifica se o Nota existe antes de excluir.
        notaRepository.delete(nota);
    }

    @Override
    public List<NotaResponse> buscarPorAlunoId(Long idAluno) {
        if (!alunoRepository.existsById(idAluno)) {
            log.error("404 NOT FOUND -> Aluno não encontrado com id: {}" , idAluno);
            throw new NotFoundException("Aluno não encontrado com id:" + idAluno);
        }

        List<NotaEntity> notas = notaRepository.findByAlunoId(idAluno);
        if(notas.isEmpty()){
            log.error("404 NOT FOUND -> Não há notas cadastradas para o aluno com id: {}" , idAluno);
            throw new NotFoundException("Não há notas cadastradas para o aluno com id: " + idAluno);
        }

        return notas.stream().map(
                t -> new NotaResponse(t.getId(), t.getValor(), t.getData(),
                        t.getAluno().getId(), t.getDocente().getId(), t.getMateria().getId())
        ).toList() ;
    }

    @Override
    public Double calcularPontuacaoAluno(Long idAluno) {
        if (!alunoRepository.existsById(idAluno)) {
            log.error("404 NOT FOUND -> Aluno não encontrado com id: {}" , idAluno);
            throw new NotFoundException("Aluno não encontrado com id:" + idAluno);
        }

        List<NotaEntity> notas = notaRepository.findByAlunoId(idAluno);
        if(notas.isEmpty()){
            log.error("404 NOT FOUND -> Não há notas cadastradas para o aluno com id: {}" , idAluno);
            throw new NotFoundException("Não há notas cadastradas para o aluno com id: " + idAluno);
        }

        //Cálculo pontuação
        Double pontuacao = 0.0;
        for (NotaEntity notaAluno : notas) {
            pontuacao += notaAluno.getValor();
        }
        pontuacao = pontuacao / notas.size() * 10;

        return pontuacao;
    }
}

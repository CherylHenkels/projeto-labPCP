package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.controller.dto.request.AlunoRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.AlunoResponse;
import br.fullstack.education.projetolabpcp.datasource.entity.*;
import br.fullstack.education.projetolabpcp.datasource.repository.NotaRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.TurmaRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.UsuarioRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.AlunoRepository;
import br.fullstack.education.projetolabpcp.infra.exception.InvalidRequestException;
import br.fullstack.education.projetolabpcp.infra.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AlunoServiceImpl implements AlunoService {

    private final AlunoRepository alunoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TurmaRepository turmaRepository;
    private final NotaRepository notaRepository;

    public AlunoServiceImpl(AlunoRepository alunoRepository, UsuarioRepository usuarioRepository,  TurmaRepository turmaRepository, NotaRepository notaRepository) {
        this.alunoRepository = alunoRepository;
        this.usuarioRepository = usuarioRepository;
        this.turmaRepository = turmaRepository;
        this.notaRepository = notaRepository;
    }

    @Override
    public List<AlunoResponse> buscarTodos() {
        List<AlunoEntity> alunos = alunoRepository.findAll();

        if (alunos.isEmpty()) {
            log.error("404 NOT FOUND -> Não há alunos cadastrados.");
            throw new NotFoundException("Não há alunos cadastrados.");
        }

        return alunos.stream().map( //mapear a lista de AlunoEntity para uma lista de AlunoResponse
                t -> new AlunoResponse(t.getId(), t.getNome(), t.getDataNascimento(), t.getUsuario().getId(), t.getTurma().getId() )
        ).toList();
    }

    @Override
    public AlunoResponse buscarPorId(Long id) {

        AlunoEntity aluno = alunoRepository.findById(id)
                .orElseThrow(()-> {
                    log.error("404 NOT FOUND -> Aluno não encontrado com id: {}" , id);
                    return new NotFoundException("Aluno não encontrado com id:" + id);});

        return new AlunoResponse(aluno.getId(), aluno.getNome(), aluno.getDataNascimento(),aluno.getUsuario().getId(), aluno.getTurma().getId());
    }

    @Override
    public AlunoResponse criar(AlunoRequest alunoRequest) {

        if (alunoRequest.getNome() == null || alunoRequest.getNome().trim().isEmpty()) {
            log.error("400 BAD REQUEST -> Nome do aluno é obrigatório");
            throw new InvalidRequestException("Nome do aluno é obrigatório");
        }
        if (alunoRequest.getDataNascimento() == null ) {
            log.error("400 BAD REQUEST -> Data de nascimento do aluno é obrigatória");
            throw new InvalidRequestException("Data de nascimento do aluno é obrigatória");
        }
        if (alunoRequest.getId_usuario() == null ) {
            log.error("400 BAD REQUEST -> Id do usuario é obrigatório");
            throw new InvalidRequestException("Id do usuario é obrigatório");
        }
        if (alunoRequest.getId_turma() == null ) {
            log.error("400 BAD REQUEST -> Id da turma é obrigatório");
            throw new InvalidRequestException("Id da turma é obrigatório");
        }

        // cria usuario que será vinculado ao aluno
        UsuarioEntity alunoUsuario = usuarioRepository.findById(alunoRequest.getId_usuario())
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND ->Usuário não encontrado com id: {}" , alunoRequest.getId_usuario());
                    return new NotFoundException("Usuário não encontrado com id:" + alunoRequest.getId_usuario());});

        // cria turma que será vinculado ao aluno
        TurmaEntity alunoTurma = turmaRepository.findById(alunoRequest.getId_turma())
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND -> Turma não encontrada com id: {}" , alunoRequest.getId_turma());
                    return new NotFoundException("Turma não encontrada com id:" + alunoRequest.getId_turma());});

        AlunoEntity aluno = new AlunoEntity();
        aluno.setId(null); // Garante que um novo ID será gerado.
        aluno.setNome(alunoRequest.getNome());
        aluno.setDataNascimento(alunoRequest.getDataNascimento());
        aluno.setUsuario(alunoUsuario); // Faz o link do usuario com o aluno
        aluno.setTurma(alunoTurma); // Faz o link da turma com o aluno
        alunoRepository.save(aluno);

        return new AlunoResponse(aluno.getId(), aluno.getNome(), aluno.getDataNascimento(),
                aluno.getUsuario().getId(), aluno.getTurma().getId());
    }

    @Override
    public AlunoResponse alterar(Long id, AlunoRequest alunoRequest) {
        buscarPorId(id); // Verifica a existência do Aluno.

        if (alunoRequest.getNome() == null || alunoRequest.getNome().trim().isEmpty()) {
            log.error("400 BAD REQUEST -> Nome do aluno é obrigatório");
            throw new InvalidRequestException("Nome do aluno é obrigatório");
        }
        if (alunoRequest.getDataNascimento() == null ) {
            log.error("400 BAD REQUEST -> Data de nascimento do aluno é obrigatória");
            throw new InvalidRequestException("Data de nascimento do aluno é obrigatória");
        }
        if (alunoRequest.getId_usuario() == null ) {
            log.error("400 BAD REQUEST -> Id do usuario é obrigatório");
            throw new InvalidRequestException("Id do usuario é obrigatório");
        }
        if (alunoRequest.getId_turma() == null ) {
            log.error("400 BAD REQUEST -> Id da turma é obrigatório");
            throw new InvalidRequestException("Id da turma é obrigatório");
        }

        // cria usuario que será vinculado ao aluno
        UsuarioEntity alunoUsuario = usuarioRepository.findById(alunoRequest.getId_usuario())
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND ->Usuário não encontrado com id: {}" , alunoRequest.getId_usuario());
                    return new NotFoundException("Usuário não encontrado com id:" + alunoRequest.getId_usuario());});

        // cria turma que será vinculado ao aluno
        TurmaEntity alunoTurma = turmaRepository.findById(alunoRequest.getId_turma())
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND -> Turma não encontrada com id: {}" , alunoRequest.getId_turma());
                    return new NotFoundException("Turma não encontrada com id:" + alunoRequest.getId_turma());});

        AlunoEntity aluno = new AlunoEntity();
        aluno.setId(id);
        aluno.setNome(alunoRequest.getNome());
        aluno.setDataNascimento(alunoRequest.getDataNascimento());
        aluno.setUsuario(alunoUsuario);
        aluno.setTurma(alunoTurma);
        alunoRepository.save(aluno);

        return new AlunoResponse(aluno.getId(), aluno.getNome(), aluno.getDataNascimento(),
                aluno.getUsuario().getId(), aluno.getTurma().getId());
    }

    @Override
    public void excluir(Long id) {
        AlunoEntity aluno = alunoRepository.findById(id)
                .orElseThrow(()-> {
                    log.error("404 NOT FOUND -> Aluno não encontrado com id: {}" , id);
                    return new NotFoundException("Aluno não encontrado com id:" + id);}); // Verifica se o Aluno existe antes de excluir.
        alunoRepository.delete(aluno);
    }


}

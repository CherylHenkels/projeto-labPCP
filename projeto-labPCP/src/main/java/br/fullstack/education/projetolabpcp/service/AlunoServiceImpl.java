package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.datasource.entity.AlunoEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.TurmaEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.UsuarioEntity;
import br.fullstack.education.projetolabpcp.datasource.repository.TurmaRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.UsuarioRepository;
import br.fullstack.education.projetolabpcp.infra.exception.AlunoByIdNotFoundException;
import br.fullstack.education.projetolabpcp.datasource.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoServiceImpl implements AlunoService {

    private final AlunoRepository alunoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;
    private final TurmaRepository turmaRepository;

    public AlunoServiceImpl(AlunoRepository alunoRepository, UsuarioRepository usuarioRepository, TokenService tokenService, TurmaRepository turmaRepository) {
        this.alunoRepository = alunoRepository;
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
        this.turmaRepository = turmaRepository;
    }

    @Override
    public List<AlunoEntity> buscarTodos() {

        return alunoRepository.findAll();
    }

    @Override
    public AlunoEntity buscarPorId(Long id) {

        return alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoByIdNotFoundException(id));
    }

    @Override
    public AlunoEntity criar(AlunoEntity entity, String token) {

        // Pega id do token para mais tarde validar o usuário
        Long tokenId = Long.valueOf( tokenService.buscaCampo(token,"sub"));

        // cria usuario que será vinculado ao aluno
        UsuarioEntity alunoUsuario = usuarioRepository.findById(entity.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // cria turma que será vinculado ao aluno
        TurmaEntity alunoTurma = turmaRepository.findById(entity.getTurma().getId())
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        entity.setId(null); // Garante que um novo ID será gerado.
        entity.setUsuario(alunoUsuario); // Faz o link do usuario com o aluno
        entity.setTurma(alunoTurma); // Faz o link da turma com o aluno
        return alunoRepository.save(entity);
    }

    @Override
    public AlunoEntity alterar(Long id, AlunoEntity entity) {
        buscarPorId(id); // Verifica a existência do Aluno.
        entity.setId(id); // Assegura que a alteração será no Aluno correto.
        return alunoRepository.save(entity);
    }

    @Override
    public void excluir(Long id) {
        AlunoEntity entity = buscarPorId(id); // Verifica se o Aluno existe antes de excluir.
        alunoRepository.delete(entity);
    }
}

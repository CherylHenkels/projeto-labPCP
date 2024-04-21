package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.controller.dto.request.CursoRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.CursoResponse;
import br.fullstack.education.projetolabpcp.datasource.entity.CursoEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.UsuarioEntity;
import br.fullstack.education.projetolabpcp.datasource.repository.CursoRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.UsuarioRepository;
import br.fullstack.education.projetolabpcp.infra.exception.CursoByIdNotFoundException;
import br.fullstack.education.projetolabpcp.infra.exception.InvalidRequestException;
import br.fullstack.education.projetolabpcp.infra.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;

    public CursoServiceImpl(CursoRepository cursoRepository, UsuarioRepository usuarioRepository, TokenService tokenService) {
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
    }

    @Override
    public List<CursoResponse> buscarTodos() {
        List<CursoEntity> cursos = cursoRepository.findAll();

        if (cursos.isEmpty()) {
            log.error("404 NOT FOUND -> Não há cursos cadastrados.");
            throw new NotFoundException("Não há cursos cadastrados.");
        }

        return cursos.stream().map(
                t-> new CursoResponse(t.getId(), t.getNome())
        ).toList();
    }

    @Override
    public CursoResponse buscarPorId(Long id) {

        CursoEntity curso = cursoRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND -> Curso não encontrado com id: {}" , id);
                    return new NotFoundException("Curso não encontrado com id:" + id );});

        return new CursoResponse(curso.getId(), curso.getNome());
    }

    @Override
    public CursoResponse criar(CursoRequest cursoRequest, String token) {

        // Pega id do token para mais tarde validar o usuário
        Long tokenId = Long.valueOf( tokenService.buscaCampo(token,"sub"));

        if (cursoRequest.getNome() == null || cursoRequest.getNome().trim().isEmpty()) {
            log.error("400 BAD REQUEST -> Nome do curso é obrigatório");
            throw new InvalidRequestException("Nome do curso é obrigatório");
        }


        CursoEntity curso = new CursoEntity();
        curso.setId(null); // garante que novo Id vai ser criado
        curso.setNome(cursoRequest.getNome());
        cursoRepository.save(curso);

        return new CursoResponse(curso.getId(), curso.getNome());
    }

    @Override
    public CursoResponse alterar(Long id, CursoRequest cursoRequest) {
        buscarPorId(id); // Verifica a existência do Curso.

        if (cursoRequest.getNome() == null || cursoRequest.getNome().trim().isEmpty()) {
            log.error("400 BAD REQUEST -> Nome do curso é obrigatório");
            throw new InvalidRequestException("Nome do curso é obrigatório");
        }

        //cria CursoEntity para salvar novos dados
        CursoEntity curso = new CursoEntity();
        curso.setId(id); // Assegura que a alteração será no Curso correto.
        curso.setNome(cursoRequest.getNome());
        cursoRepository.save(curso);

        return new CursoResponse(curso.getId(), curso.getNome());
    }

    @Override
    public void excluir(Long id) {
        CursoEntity entity = cursoRepository.findById(id)
                .orElseThrow(()-> {
                    log.error("404 NOT FOUND -> Curso não encontrado com id: {}" , id);
                    return new NotFoundException("Curso não encontrado com id:" + id );}); // Verifica se o Curso existe antes de excluir.
        cursoRepository.delete(entity);
    }
}

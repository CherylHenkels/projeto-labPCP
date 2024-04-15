package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.datasource.entity.CursoEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.UsuarioEntity;
import br.fullstack.education.projetolabpcp.datasource.repository.CursoRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.UsuarioRepository;
import br.fullstack.education.projetolabpcp.infra.exception.CursoByIdNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<CursoEntity> buscarTodos() {

        return cursoRepository.findAll();
    }

    @Override
    public CursoEntity buscarPorId(Long id) {

        return cursoRepository.findById(id)
                .orElseThrow(() -> new CursoByIdNotFoundException(id));
    }

    @Override
    public CursoEntity criar(CursoEntity entity, String token) {

        // Pega id do token para mais tarde validar o usuário
        Long tokenId = Long.valueOf( tokenService.buscaCampo(token,"sub"));

        entity.setId(null); // garante que novo Id vai ser criado
        return cursoRepository.save(entity);
    }

    @Override
    public CursoEntity alterar(Long id, CursoEntity entity) {
        buscarPorId(id); // Verifica a existência do Curso.
        entity.setId(id); // Assegura que a alteração será no Curso correto.
        return cursoRepository.save(entity);
    }

    @Override
    public void excluir(Long id) {
        CursoEntity entity = buscarPorId(id); // Verifica se o Curso existe antes de excluir.
        cursoRepository.delete(entity);
    }
}

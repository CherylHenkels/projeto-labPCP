package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.datasource.entity.DocenteEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.UsuarioEntity;
import br.fullstack.education.projetolabpcp.datasource.repository.DocenteRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.UsuarioRepository;
import br.fullstack.education.projetolabpcp.infra.exception.DocenteByIdNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocenteServiceImpl implements DocenteService {

    private final DocenteRepository docenteRepository;
    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;

    public DocenteServiceImpl(DocenteRepository docenteRepository, UsuarioRepository usuarioRepository, TokenService tokenService) {
        this.docenteRepository = docenteRepository;
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
    }

    @Override
    public List<DocenteEntity> buscarTodos() {

        return docenteRepository.findAll();
    }

    @Override
    public DocenteEntity buscarPorId(Long id) {

        return docenteRepository.findById(id)
                .orElseThrow(() -> new DocenteByIdNotFoundException(id));
    }

    @Override
    public DocenteEntity criar(DocenteEntity entity, String token) {

        // Pega id do token para mais tarde validar o usuário
        Long tokenId = Long.valueOf( tokenService.buscaCampo(token,"sub"));

        // cria usuario que será vinculado ao docente
        UsuarioEntity docenteUsuario = usuarioRepository.findById(entity.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        entity.setId(null); // garante que novo Id vai ser criado
        entity.setUsuario(docenteUsuario); // Faz o link do usuario com o docente
        return docenteRepository.save(entity);
    }

    @Override
    public DocenteEntity alterar(Long id, DocenteEntity entity) {
        buscarPorId(id); // Verifica a existência do Docente.
        entity.setId(id); // Assegura que a alteração será no Docente correto.
        return docenteRepository.save(entity);
    }

    @Override
    public void excluir(Long id) {
        DocenteEntity entity = buscarPorId(id); // Verifica se o Docente existe antes de excluir.
        docenteRepository.delete(entity);
    }
}

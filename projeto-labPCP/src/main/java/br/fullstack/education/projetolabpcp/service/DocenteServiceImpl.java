package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.controller.dto.request.DocenteRequest;
import br.fullstack.education.projetolabpcp.controller.dto.response.DocenteResponse;
import br.fullstack.education.projetolabpcp.datasource.entity.DocenteEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.UsuarioEntity;
import br.fullstack.education.projetolabpcp.datasource.repository.DocenteRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.UsuarioRepository;
import br.fullstack.education.projetolabpcp.infra.exception.DocenteByIdNotFoundException;
import br.fullstack.education.projetolabpcp.infra.exception.InvalidRequestException;
import br.fullstack.education.projetolabpcp.infra.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
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
    public List<DocenteResponse> buscarTodos() {
        List<DocenteEntity> docentes = docenteRepository.findAll();

        if (docentes.isEmpty()) {
            log.error("404 NOT FOUND -> Não há docentes cadastrados.");
            throw new NotFoundException("Não há docentes cadastrados.");
        }

        return docentes.stream().map( //mapear a lista de DocenteEntity para uma lista de DocenteResponse
                t -> new DocenteResponse(t.getId(), t.getNome(), t.getDataEntrada() )
        ).toList();

    }

    @Override
    public DocenteResponse buscarPorId(Long id) {

        DocenteEntity docente = docenteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND -> Docente não encontrado com id: {}" , id);
                    return new NotFoundException("Docente não encontrado com id:" + id);}) ;

        return new DocenteResponse(docente.getId(), docente.getNome(), docente.getDataEntrada());
    }

    @Override
    public DocenteResponse criar(DocenteRequest docenteRequest, String token) {

        // Pega id do token para mais tarde validar o usuário
        Long tokenId = Long.valueOf( tokenService.buscaCampo(token,"sub"));

        if (docenteRequest.getNome() == null || docenteRequest.getNome().trim().isEmpty()) {
            log.error("400 BAD REQUEST -> Nome do docente é obrigatório");
            throw new InvalidRequestException("Nome do docente é obrigatório");
        }
        if (docenteRequest.getId_usuario() == null ) {
            log.error("400 BAD REQUEST -> Id do usuário é obrigatório");
            throw new InvalidRequestException("Id do usuário é obrigatório");
        }


        // cria usuario que será vinculado ao docente
        UsuarioEntity docenteUsuario = usuarioRepository.findById(docenteRequest.getId_usuario())
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND -> Usuário não encontrado com id: {}" , docenteRequest.getId_usuario());
                    return new NotFoundException("Usuário não encontrado com id:" + docenteRequest.getId_usuario());});

        //cria DocenteEntity
        DocenteEntity docente =  new DocenteEntity();
        docente.setId(null); // garante que novo Id vai ser criado
        docente.setNome(docenteRequest.getNome());
        docente.setUsuario(docenteUsuario); // Faz o link do usuario com o docente
        docenteRepository.save(docente);

        return new DocenteResponse(docente.getId(), docente.getNome(), docente.getDataEntrada());
    }

    @Override
    public DocenteResponse alterar(Long id, DocenteRequest docenteRequest) {
        buscarPorId(id); // Verifica a existência do Docente.

        if (docenteRequest.getNome() == null || docenteRequest.getNome().trim().isEmpty()) {
            log.error("400 BAD REQUEST -> Nome do docente é obrigatório");
            throw new InvalidRequestException("Nome do docente é obrigatório");
        }
        if (docenteRequest.getId_usuario() == null ) {
            log.error("400 BAD REQUEST -> Id do usuário é obrigatório");
            throw new InvalidRequestException("Id do usuário é obrigatório");
        }

        // cria usuario que será vinculado ao docente
        UsuarioEntity docenteUsuario = usuarioRepository.findById(docenteRequest.getId_usuario())
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND -> Usuário não encontrado com id: {}" , docenteRequest.getId_usuario());
                    return new NotFoundException("Usuário não encontrado com id:" + docenteRequest.getId_usuario());});

        //cria DocenteEntity para salvar novos dados
        DocenteEntity docente = new DocenteEntity();
        docente.setId(id); // Assegura que a alteração será no Docente correto.
        docente.setNome(docenteRequest.getNome());
        docente.setUsuario(docenteUsuario);
        docenteRepository.save(docente);
        return new DocenteResponse(docente.getId(), docente.getNome(), docente.getDataEntrada());
    }

    @Override
    public void excluir(Long id) {
        DocenteEntity docente = docenteRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("404 NOT FOUND -> Docente não encontrado com id: {}" , id);
                    return new NotFoundException("Docente não encontrado com id:" + id);}) ; // Verifica se o Docente existe antes de excluir.
        docenteRepository.delete(docente);
    }
}

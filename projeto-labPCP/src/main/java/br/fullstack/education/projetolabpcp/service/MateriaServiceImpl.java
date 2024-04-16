package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.datasource.entity.CursoEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.MateriaEntity;
import br.fullstack.education.projetolabpcp.datasource.repository.MateriaRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.CursoRepository;
import br.fullstack.education.projetolabpcp.infra.exception.CursoByIdNotFoundException;
import br.fullstack.education.projetolabpcp.infra.exception.MateriaByIdNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<MateriaEntity> buscarTodos() {

        return materiaRepository.findAll();
    }

    @Override
    public MateriaEntity buscarPorId(Long id) {

        return materiaRepository.findById(id)
                .orElseThrow(() -> new MateriaByIdNotFoundException(id));
    }

    @Override
    public MateriaEntity criar(MateriaEntity entity, String token) {

        // Pega id do token para mais tarde validar o usuário
        Long tokenId = Long.valueOf( tokenService.buscaCampo(token,"sub"));

        // cria curso que será vinculado ao materia
        CursoEntity materiaCurso = cursoRepository.findById(entity.getCurso().getId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        entity.setId(null); // garante que novo Id vai ser criado
        entity.setCurso(materiaCurso); // Faz o link do curso com o materia
        return materiaRepository.save(entity);
    }

    @Override
    public MateriaEntity alterar(Long id, MateriaEntity entity) {
        buscarPorId(id); // Verifica a existência do Materia.
        entity.setId(id); // Assegura que a alteração será no Materia correto.
        return materiaRepository.save(entity);
    }

    @Override
    public void excluir(Long id) {
        MateriaEntity entity = buscarPorId(id); // Verifica se o Materia existe antes de excluir.
        materiaRepository.delete(entity);
    }

    @Override
    public List<MateriaEntity> buscarPorCursoId(Long idCurso) {
        if (!cursoRepository.existsById(idCurso)) {
            throw new CursoByIdNotFoundException(idCurso);
        }
        return materiaRepository.findByCursoId(idCurso);
    }
}

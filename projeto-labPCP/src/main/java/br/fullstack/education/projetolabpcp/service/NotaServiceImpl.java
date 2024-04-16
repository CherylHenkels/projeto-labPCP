package br.fullstack.education.projetolabpcp.service;

import br.fullstack.education.projetolabpcp.datasource.entity.DocenteEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.MateriaEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.NotaEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.AlunoEntity;
import br.fullstack.education.projetolabpcp.datasource.repository.DocenteRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.MateriaRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.NotaRepository;
import br.fullstack.education.projetolabpcp.datasource.repository.AlunoRepository;
import br.fullstack.education.projetolabpcp.infra.exception.CursoByIdNotFoundException;
import br.fullstack.education.projetolabpcp.infra.exception.NotaByIdNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaServiceImpl implements NotaService {

    private final NotaRepository notaRepository;
    private final AlunoRepository alunoRepository;
    private final DocenteRepository docenteRepository;
    private final TokenService tokenService;
    private final MateriaRepository materiaRepository;

    public NotaServiceImpl(NotaRepository notaRepository, AlunoRepository alunoRepository, DocenteRepository docenteRepository, TokenService tokenService, MateriaRepository materiaRepository) {
        this.notaRepository = notaRepository;
        this.alunoRepository = alunoRepository;
        this.docenteRepository = docenteRepository;
        this.tokenService = tokenService;
        this.materiaRepository = materiaRepository;
    }

    @Override
    public List<NotaEntity> buscarTodos() {

        return notaRepository.findAll();
    }

    @Override
    public NotaEntity buscarPorId(Long id) {

        return notaRepository.findById(id)
                .orElseThrow(() -> new NotaByIdNotFoundException(id));
    }

    @Override
    public NotaEntity criar(NotaEntity entity, String token) {

        // Pega id do token para mais tarde validar o usuário
        Long tokenId = Long.valueOf( tokenService.buscaCampo(token,"sub"));

        // cria aluno que será vinculado a nota
        AlunoEntity notaAluno = alunoRepository.findById(entity.getAluno().getId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // cria docente que será vinculado a nota
        DocenteEntity notaDocente = docenteRepository.findById(entity.getDocente().getId())
                .orElseThrow(() -> new RuntimeException("Docente não encontrado"));

        // cria materia que será vinculada a nota
        MateriaEntity notaMateria = materiaRepository.findById(entity.getMateria().getId())
                .orElseThrow(() -> new RuntimeException("Materia não encontrada"));

        entity.setId(null); // garante que novo Id vai ser criado
        entity.setAluno(notaAluno); // Faz o link do aluno com a nota
        entity.setDocente(notaDocente); // Faz o link do docente com a nota
        entity.setMateria(notaMateria); // Faz o link da materia com a nota
        return notaRepository.save(entity);
    }

    @Override
    public NotaEntity alterar(Long id, NotaEntity entity) {
        buscarPorId(id); // Verifica a existência do Nota.
        entity.setId(id); // Assegura que a alteração será no Nota correto.
        return notaRepository.save(entity);
    }

    @Override
    public void excluir(Long id) {
        NotaEntity entity = buscarPorId(id); // Verifica se o Nota existe antes de excluir.
        notaRepository.delete(entity);
    }

    @Override
    public List<NotaEntity> buscarPorAlunoId(Long idAluno) {
        if (!alunoRepository.existsById(idAluno)) {
            throw new NotaByIdNotFoundException(idAluno);
        }
        return notaRepository.findByAlunoId(idAluno);
    }
}

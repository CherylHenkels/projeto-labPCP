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
import br.fullstack.education.projetolabpcp.infra.exception.CursoByIdNotFoundException;
import br.fullstack.education.projetolabpcp.infra.exception.NotFoundException;
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
    public List<NotaResponse> buscarTodos() {
        List<NotaEntity> notas = notaRepository.findAll();

        if (notas.isEmpty()) {
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
                .orElseThrow(() -> new NotFoundException("Nota não encontrada com id:" + id) );

        return new NotaResponse(nota.getId(), nota.getValor(), nota.getData(),
                nota.getAluno().getId(), nota.getDocente().getId(), nota.getMateria().getId());
    }

    @Override
    public NotaResponse criar(NotaRequest notaRequest, String token) {

        // Pega id do token para mais tarde validar o usuário
        Long tokenId = Long.valueOf( tokenService.buscaCampo(token,"sub"));

        // cria aluno que será vinculado a nota
        AlunoEntity notaAluno = alunoRepository.findById(notaRequest.getId_aluno())
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado com id:" + notaRequest.getId_aluno()));

        // cria docente que será vinculado a nota
        DocenteEntity notaDocente = docenteRepository.findById(notaRequest.getId_docente())
                .orElseThrow(() -> new NotFoundException("Docente não encontrado com id:" + notaRequest.getId_docente()));

        // cria materia que será vinculada a nota
        MateriaEntity notaMateria = materiaRepository.findById(notaRequest.getId_materia())
                .orElseThrow(() -> new NotFoundException("Materia não encontrada com id:" + notaRequest.getId_materia()));

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

        // cria aluno que será vinculado a nota
        AlunoEntity notaAluno = alunoRepository.findById(notaRequest.getId_aluno())
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado com id:" + notaRequest.getId_aluno()));

        // cria docente que será vinculado a nota
        DocenteEntity notaDocente = docenteRepository.findById(notaRequest.getId_docente())
                .orElseThrow(() -> new NotFoundException("Docente não encontrado com id:" + notaRequest.getId_docente()));

        // cria materia que será vinculada a nota
        MateriaEntity notaMateria = materiaRepository.findById(notaRequest.getId_materia())
                .orElseThrow(() -> new NotFoundException("Materia não encontrada com id:" + notaRequest.getId_materia()));

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
                .orElseThrow(()-> new NotFoundException("Nota não encontrada com id:" + id)); // Verifica se o Nota existe antes de excluir.
        notaRepository.delete(nota);
    }

    @Override
    public List<NotaResponse> buscarPorAlunoId(Long idAluno) {
        if (!alunoRepository.existsById(idAluno)) {
            throw new NotFoundException("Não há notas cadastradas para o aluno com id:" + idAluno);
        }

        List<NotaEntity> notas = notaRepository.findByAlunoId(idAluno);
        return notas.stream().map(
                t -> new NotaResponse(t.getId(), t.getValor(), t.getData(),
                        t.getAluno().getId(), t.getDocente().getId(), t.getMateria().getId())
        ).toList() ;
    }
}

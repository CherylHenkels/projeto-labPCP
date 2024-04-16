package br.fullstack.education.projetolabpcp.datasource.repository;

import br.fullstack.education.projetolabpcp.datasource.entity.MateriaEntity;
import br.fullstack.education.projetolabpcp.datasource.entity.NotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface NotaRepository extends JpaRepository<NotaEntity, Long> {
   List<NotaEntity> findByAlunoId(Long idAluno);

}


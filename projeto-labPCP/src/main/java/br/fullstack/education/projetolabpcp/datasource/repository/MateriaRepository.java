package br.fullstack.education.projetolabpcp.datasource.repository;

import br.fullstack.education.projetolabpcp.datasource.entity.MateriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface MateriaRepository extends JpaRepository<MateriaEntity, Long> {
    List<MateriaEntity> findByCursoId(Long idCurso);
}


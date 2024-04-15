package br.fullstack.education.projetolabpcp.datasource.repository;

import br.fullstack.education.projetolabpcp.datasource.entity.CursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CursoRepository extends JpaRepository<CursoEntity, Long> {
}


package br.fullstack.education.projetolabpcp.datasource.repository;

import br.fullstack.education.projetolabpcp.datasource.entity.TurmaEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TurmaRepository extends JpaRepository<TurmaEntity, Long> {
}


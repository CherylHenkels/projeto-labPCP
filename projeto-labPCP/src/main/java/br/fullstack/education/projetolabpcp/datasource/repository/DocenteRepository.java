package br.fullstack.education.projetolabpcp.datasource.repository;

import br.fullstack.education.projetolabpcp.datasource.entity.DocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DocenteRepository extends JpaRepository<DocenteEntity, Long> {
}


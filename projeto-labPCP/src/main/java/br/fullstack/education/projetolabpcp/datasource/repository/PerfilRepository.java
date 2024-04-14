package br.fullstack.education.projetolabpcp.datasource.repository;

import br.fullstack.education.projetolabpcp.datasource.entity.PerfilEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface PerfilRepository extends JpaRepository<PerfilEntity,Long> {
    Optional<PerfilEntity> findByNome(String nome);
}

package br.fullstack.education.projetolabpcp.datasource.repository;

import br.fullstack.education.projetolabpcp.datasource.entity.TesteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TesteRepository extends JpaRepository<TesteEntity, Long> {

    @Query(
            " select t from TesteEntity t " +
                    " where t.usuario.id = :id"
    )
    List<TesteEntity> findAllByUsuarioId(@Param("id") Long id);
    // As List não precisam de Optional,
    // pois se não houver itens compatíveis com o where a lista virá vazia
}

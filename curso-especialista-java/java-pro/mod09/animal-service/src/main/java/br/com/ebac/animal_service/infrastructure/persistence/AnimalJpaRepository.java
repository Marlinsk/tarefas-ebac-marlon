package br.com.ebac.animal_service.infrastructure.persistence;

import br.com.ebac.animal_service.domain.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AnimalJpaRepository extends JpaRepository<Animal, Long> {

    @Query("SELECT a FROM Animal a WHERE a.dataAdocao IS NULL AND a.dataObito IS NULL ORDER BY a.dataEntrada ASC")
    List<Animal> findDisponiveis();

    @Query("SELECT a FROM Animal a WHERE a.dataAdocao IS NOT NULL AND a.dataObito IS NULL ORDER BY a.dataAdocao DESC")
    List<Animal> findAdotados();

    @Query("SELECT a FROM Animal a LEFT JOIN FETCH a.funcionarioRecebedor WHERE a.dataEntrada BETWEEN :dataInicio AND :dataFim ORDER BY a.dataEntrada ASC")
    List<Animal> findByDataEntradaBetween(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
}

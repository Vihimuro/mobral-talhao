package mobral.talhaoManagerAPI.repository;

import mobral.talhaoManagerAPI.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    List<Field> findAllByCdIdFarm(long cdIdFarm);
}

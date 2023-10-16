package mobral.talhaoManagerAPI.repository;

import com.bedatadriven.jackson.datatype.jts.GeoJson;
import mobral.talhaoManagerAPI.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    @Query(value = "INSERT INTO ctd_field (cd_id_fazenda, geom) VALUES (:cdIdFarm, ST_GeomFromGeoJSON(:geom))", nativeQuery = true)
    Field saveField(@Param("cdIdFarm") Long cdIdFarm, @Param("geom") GeoJson geom);
}

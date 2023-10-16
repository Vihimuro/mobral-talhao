package mobral.talhaoManagerAPI.domain;

import com.vividsolutions.jts.geom.Geometry;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "cdt_field")
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_id")
    private long id;

    @Column(name="cd_id_fazenda")
    private long cdIdFarm;

    @Column(columnDefinition = "geometry(Geometry,4326)")
    private Geometry geom;

}

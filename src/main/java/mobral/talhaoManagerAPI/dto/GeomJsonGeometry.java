package mobral.talhaoManagerAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeomJsonGeometry {
    private final Double[][][] coordinates;
    private final String type;
}

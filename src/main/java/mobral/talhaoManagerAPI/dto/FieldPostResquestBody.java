package mobral.talhaoManagerAPI.dto;

import com.bedatadriven.jackson.datatype.jts.GeoJson;
import com.vividsolutions.jts.geom.Geometry;
import lombok.Data;

@Data
public class FieldPostResquestBody {
    private long cdIdFazenda;
    private GeoJson geom;
}

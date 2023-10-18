package mobral.talhaoManagerAPI.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class FieldSaveBody{
        private final long cdIdFazenda;
        private GeometryJson geom;
}

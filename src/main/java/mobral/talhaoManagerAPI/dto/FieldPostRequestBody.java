package mobral.talhaoManagerAPI.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class FieldPostRequestBody {
    private long cdIdFazenda;
    private FeatureCollectionJson featureCollection;
}

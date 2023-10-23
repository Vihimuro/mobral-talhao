package mobral.talhaoManagerAPI.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PropertiesJson {
    @JsonProperty("cd_id_fazenda")
    private long cdIdFarm;
    @JsonProperty("cd_id_talhao")
    private long cdIdField;
}

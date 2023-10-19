package mobral.talhaoManagerAPI.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mobral.talhaoManagerAPI.domain.Field;
import mobral.talhaoManagerAPI.dto.FeatureJson;
import mobral.talhaoManagerAPI.dto.FieldPostRequestBody;
import mobral.talhaoManagerAPI.repository.FieldRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.n52.jackson.datatype.jts.JtsModule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldService {

    private final FieldRepository fieldRepository;

    @Transactional
    public List<Field> saveAll(FieldPostRequestBody fieldPostRequestBody) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JtsModule());
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        ArrayList<FeatureJson> features = fieldPostRequestBody.getFeaturesCollection().getFeatures();
        List<Field> fields = new ArrayList<>();
        for (FeatureJson feature: features){
            Double[][][] listOfCoordinates = feature.getGeometry().getCoordinates();
            ArrayList<Coordinate> coordinatesPolygon = new ArrayList<>();
            for (Double[][] coordinates : listOfCoordinates) {
                for (Double[] coordinate : coordinates) {
                    coordinatesPolygon.add(new Coordinate(coordinate[0], coordinate[1]));
                }
            }
            Coordinate[] coor = new Coordinate[coordinatesPolygon.size()];
            for(int i = 0; i < coordinatesPolygon.size(); i++){
                coor[i] = coordinatesPolygon.get(i);
            }
            GeometryFactory gf = new GeometryFactory();
            Polygon polygon = gf.createPolygon(coor);
            Field field = Field.builder()
                    .cdIdFarm(fieldPostRequestBody.getCdIdFazenda())
                    .geom(polygon)
                    .build();
            fields.add(field);
        }
        return fieldRepository.saveAll(fields);
    }
}

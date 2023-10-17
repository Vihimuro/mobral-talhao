package mobral.talhaoManagerAPI.service;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mobral.talhaoManagerAPI.domain.Field;
import mobral.talhaoManagerAPI.dto.FieldPostRequestBody;
import mobral.talhaoManagerAPI.repository.FieldRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class FieldService {

    private final FieldRepository fieldRepository;

    @Transactional
    public Field save(FieldPostRequestBody fieldPostRequestBody) {
        System.out.println(fieldPostRequestBody);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JtsModule());
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        Double[][][] listOfCoordenates = fieldPostRequestBody.getGeom().getCoordinates();
        ArrayList<Coordinate> coordinatesPolygon = new ArrayList<Coordinate>();
        for (Double[][] coordenates : listOfCoordenates) {
            for (Double[] coordenate : coordenates) {
                System.out.println(Arrays.toString(coordenate));
                coordinatesPolygon.add(new Coordinate(coordenate[0], coordenate[1]));
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
        return fieldRepository.save(field);
    }
}

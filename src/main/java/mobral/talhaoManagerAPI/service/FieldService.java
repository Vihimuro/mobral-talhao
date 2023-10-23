package mobral.talhaoManagerAPI.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mobral.talhaoManagerAPI.domain.Field;
import mobral.talhaoManagerAPI.dto.*;
import mobral.talhaoManagerAPI.repository.FieldRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
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
    private final ObjectMapper mapper = mapper();
    private final GeometryFactory geometryFactory = new GeometryFactory();

    @Transactional
    public FeatureCollectionJson saveAll(FieldPostRequestBody fieldPostRequestBody) {
        ArrayList<FeatureJson> features = fieldPostRequestBody.getFeatureCollection().getFeatures();
        List<Field> fields = buildFieldsList(fieldPostRequestBody, features);
        List<Field> fieldsSaved =  fieldRepository.saveAll(fields);
        ArrayList<FeatureJson> featuresJson = buildFeaturesJson(fieldsSaved);
        return buildFeatureCollectionJson(featuresJson);
    }

    public FeatureCollectionJson findAllByCdIdFarm(long cdIdFarm) {
        List<Field> fieldList = fieldRepository.findAllByCdIdFarm(cdIdFarm);
        ArrayList<FeatureJson> featuresJson = buildFeaturesJson(fieldList);
        return buildFeatureCollectionJson(featuresJson);
    }

    private ObjectMapper mapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JtsModule());
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return mapper;
    }


    private Double[][][] buildCoordinatesJson (Coordinate[] coordinates) {
        Double[][][] coordinatesJson = new Double[1][coordinates.length][2];
        int indexCoordinate = 0;
        for (Coordinate coordinate : coordinates ){
            coordinatesJson[0][indexCoordinate][0] = coordinate.x;
            coordinatesJson[0][indexCoordinate][1] = coordinate.y;
            indexCoordinate++;
        }
        return coordinatesJson;
    }
    private PropertiesJson buildPropertiesJson (Field field) {
        return PropertiesJson.builder()
                .cdIdFarm(field.getCdIdFarm())
                .cdIdField(field.getId())
                .build();
    }

    private GeometryJson buildGeometryJson (Geometry geom, Double[][][] coordinatesJson) {
        return GeometryJson.builder()
                .type(geom.getGeometryType())
                .coordinates(coordinatesJson)
                .build();
    }

    private FeatureJson buildFeatureJson (PropertiesJson propertiesJson, GeometryJson geometryJson) {
        return FeatureJson.builder()
                .type("Feature")
                .properties(propertiesJson)
                .geometry(geometryJson)
                .build();
    }

    private ArrayList<FeatureJson> buildFeaturesJson (List<Field> fieldList) {
        ArrayList<FeatureJson> featuresJson = new ArrayList<>();
        for (Field field : fieldList){
            Geometry geom = field.getGeom();
            Coordinate[] coordinates = geom.getCoordinates();
            Double[][][] coordinatesJson = buildCoordinatesJson(coordinates);
            GeometryJson geometryJson = buildGeometryJson(geom, coordinatesJson);
            PropertiesJson propertiesJson = buildPropertiesJson(field);
            FeatureJson featureJson = buildFeatureJson(propertiesJson, geometryJson);
            featuresJson.add(featureJson);
        }
        return featuresJson;
    }

    private FeatureCollectionJson buildFeatureCollectionJson (ArrayList<FeatureJson> featuresJson) {
        return  FeatureCollectionJson.builder()
                .type("FeatureCollection")
                .features(featuresJson)
                .build();
    }

    private ArrayList<Coordinate> buildCoordinateArrayList (Double[][][] listOfCoordinates){
        ArrayList<Coordinate> coordinateArrayList = new ArrayList<>();
        for (Double[][] coordinates : listOfCoordinates) {
            for (Double[] coordinate : coordinates) {
                coordinateArrayList.add(new Coordinate(coordinate[0], coordinate[1]));
            }
        }
        return coordinateArrayList;
    }

    private Coordinate[] buildCoordinateArray (ArrayList<Coordinate> coordinateArrayList) {
        Coordinate[] coordinateArray = new Coordinate[coordinateArrayList.size()];
        for (int i = 0; i < coordinateArray.length; i++) {
            coordinateArray[i] = coordinateArrayList.get(i);
        }
        return coordinateArray;
    }
    private Field buildField (long cdIdFarm, Geometry geom) {
        return  Field.builder()
                .cdIdFarm(cdIdFarm)
                .geom(geom)
                .build();
    }

    private List<Field> buildFieldsList (FieldPostRequestBody fieldPostRequestBody, ArrayList<FeatureJson> featuresJson) {
        List<Field> fields = new ArrayList<>();
        for (FeatureJson feature : featuresJson) {
            Double[][][] listOfCoordinates = feature.getGeometry().getCoordinates();
            ArrayList<Coordinate> coordinateArrayList= buildCoordinateArrayList(listOfCoordinates);
            Coordinate[] coordinateArray = buildCoordinateArray(coordinateArrayList);

            Polygon polygon = geometryFactory.createPolygon(coordinateArray);
            Field field = buildField(fieldPostRequestBody.getCdIdFazenda(), polygon);
            fields.add(field);
        }
        return fields;
    }
}
